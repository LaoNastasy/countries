package com.alitvinova.countriesapp.presentation.animation

import org.w3c.dom.Node
import java.io.InputStream
import java.io.StringWriter
import java.util.regex.Pattern
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

const val paddingPercentage = 0.05

data class AnimationParameters(
    val duration: Int = 2000,
    val color: String = "#FF0000"
)

/***
 * Generate animated vector
 * @param inputStream base vector that would be change according to country code
 * @param countryCode code of country that would be colored and zoomed
 * @param animParams animation parameters to customize animation (color and duration)
 * @return InputStream generated animated-vector drawable
 *
 */
fun generateAnimationXml(
    inputStream: InputStream,
    countryCode: String,
    animParams: AnimationParameters = AnimationParameters()
): InputStream {

    val builderFactory = DocumentBuilderFactory.newInstance()
    val docBuilder = builderFactory.newDocumentBuilder()
    val doc = docBuilder.parse(inputStream)

    fun Node.addAttrib(name: String, value: String) {
        val attr = doc.createAttribute(name)
        attr.value = value
        this.attributes.setNamedItem(attr)
    }

    val vector = doc
        .childNodes.item(0)
        .childNodes.item(1)
        .childNodes.item(1)
    val viewportWidth = vector.attributes.getNamedItem("android:viewportWidth").nodeValue.toDouble()
    val viewportHeight = vector.attributes.getNamedItem("android:viewportHeight").nodeValue.toDouble()

    val group = vector.childNodes.item(1)
    val paths = group.childNodes

    var leftBorder = Double.MAX_VALUE
    var rightBorder = Double.MIN_VALUE
    var topBorder = Double.MAX_VALUE
    var bottomBorder = Double.MIN_VALUE

    for (i in 0 until paths.length) {

        val path = paths.item(i)
        if (path.hasAttributes()
            && path.attributes.getNamedItem("android:name") != null
            && path.attributes.getNamedItem("android:name").nodeValue.startsWith(countryCode)
        ) {
            val d = path.attributes.getNamedItem("android:pathData").nodeValue

            val p = Pattern.compile("[ML] (\\d+(\\.\\d+)?) (\\d+(\\.\\d+)?)")
            val m = p.matcher(d)
            while (m.find()) {
                val x = m.group(1)!!.toDouble()
                val y = m.group(3)!!.toDouble()

                leftBorder = leftBorder.coerceAtMost(x)
                rightBorder = rightBorder.coerceAtLeast(x)
                topBorder = topBorder.coerceAtMost(y)
                bottomBorder = bottomBorder.coerceAtLeast(y)
            }
            path.addAttrib("android:fillColor", animParams.color)
        }
    }

    if (leftBorder == Double.MAX_VALUE) {
        throw IllegalArgumentException("There is no country with code '$countryCode' accepted by SVG")
    }

    val countryWidth = rightBorder - leftBorder
    val countryHeight = bottomBorder - topBorder

    val countryAverageScale = Math.min(viewportWidth/countryWidth, viewportHeight/countryHeight)
    val paddingPercentage = 1.5 - (3/countryAverageScale)

    var viewWidth = countryWidth + countryWidth * paddingPercentage * 2
    var viewHeight = countryHeight + countryHeight * paddingPercentage * 2


    val scaleX = viewportWidth / (countryWidth * (1 + paddingPercentage * 2))
    val scaleY = viewportHeight / (countryHeight * (1 + paddingPercentage * 2))
    val scale = scaleX.coerceAtMost(scaleY)

    if (scaleX > scaleY) {
        viewWidth = viewHeight * viewportWidth / viewportHeight
    } else {
        viewHeight = viewWidth * viewportHeight / viewportWidth
    }

    val leftViewBorder = leftBorder - (viewWidth / 2 - countryWidth / 2)
    val topViewBorder = topBorder - (viewHeight / 2 - countryHeight / 2)

    val translateX = -leftViewBorder
    val translateY = -topViewBorder

    val pivotX = leftViewBorder
    val pivotY = topViewBorder

    println(
        """
        ---------------------------------
        translateX: $translateX
        translateY: $translateY
        scale(X and Y): $scale
        pivotX: $pivotX
        pivotY: $pivotY
        ---------------------------------
    """.trimIndent()
    )

    val objectAnimatorSet = doc
        .childNodes.item(0)
        .childNodes.item(3)
        .childNodes.item(1)
        .childNodes.item(1)
        .childNodes

    for (i in 0 until objectAnimatorSet.length) {
        val item = objectAnimatorSet.item(i)
        if (item.hasAttributes()) {
            val propertyName = objectAnimatorSet.item(i).attributes.getNamedItem("android:propertyName").nodeValue
            fun addAttrib(name: String, value: String) {
                val attr = doc.createAttribute(name)
                attr.value = value
                item.attributes.setNamedItem(attr)
            }
            when (propertyName) {
                "translateX" -> {
                    addAttrib("android:valueTo", translateX.toString())
                    addAttrib("android:duration", animParams.duration.toString())
                }

                "translateY" -> {
                    addAttrib("android:valueTo", translateY.toString())
                    addAttrib("android:duration", animParams.duration.toString())

                }

                "scaleX" -> {
                    addAttrib("android:valueTo", scale.toString())
                    addAttrib("android:duration", animParams.duration.toString())
                }

                "scaleY" -> {
                    addAttrib("android:valueTo", scale.toString())
                    addAttrib("android:duration", animParams.duration.toString())
                }

                "pivotX" -> addAttrib("android:valueTo", pivotX.toString())
                "pivotY" -> addAttrib("android:valueTo", pivotY.toString())
            }
        }
    }

    val domSource = DOMSource(doc)
    val writer = StringWriter()
    val result = StreamResult(writer)
    val tf: TransformerFactory = TransformerFactory.newInstance()
    val transformer: Transformer = tf.newTransformer()
    transformer.transform(domSource, result)
    return writer.toString().byteInputStream()
}
