package com.alitvinova.countriesapp.presentation.info

import android.annotation.SuppressLint
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alitvinova.countriesapp.R
import com.alitvinova.countriesapp.domain.entity.CountryInfo
import com.alitvinova.countriesapp.presentation.ErrorInfo
import com.alitvinova.countriesapp.presentation.Loader
import com.alitvinova.countriesapp.ui.theme.BackgroundPrimary
import com.alitvinova.countriesapp.ui.theme.Purple40
import com.alitvinova.countriesapp.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun CountryInfoScreen(viewModel: CountryInfoViewModel) {
    val state = viewModel.state.collectAsState().value

    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        if (state.error != null) {
            ErrorInfo(viewModel::onReloadClick)
        } else if (state.info != null) {
            Content(info = state.info, countryCode = state.code)
        }
        if (state.loading) Loader()
    }
}

@SuppressLint("DiscouragedApi")
@Composable
private fun getImageIdByCountryCode(code: String): Int? {
    val context = LocalContext.current
    return try {
        val id = context.resources.getIdentifier(
            "_" + code.lowercase(),
            "drawable",
            context.packageName
        )

        if (id == 0) R.drawable.default_map_animation else id
    } catch (e: Exception) {
        R.drawable.default_map_animation
    }
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
private fun Content(info: CountryInfo, countryCode: String) {
    var atEnd by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        Box {
            val drawableResourceId = getImageIdByCountryCode(code = countryCode)
            if (drawableResourceId != null && drawableResourceId != 0) {
                Image(
                    painter = rememberAnimatedVectorPainter(
                        animatedImageVector = AnimatedImageVector.animatedVectorResource(
                            drawableResourceId
                        ),
                        atEnd = atEnd
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(Modifier.height(16.dp))
                Text(text = info.name, style = Typography.titleLarge, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
                if (info.name != info.officialName) {
                    Text(
                        text = info.officialName,
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
        }
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Spacer(Modifier.width(16.dp))
            AsyncImage(
                model = info.flag,
                contentDescription = null,
                modifier = Modifier.weight(1f)
            )
            if (info.coatOfArms != null) {
                Spacer(Modifier.width(24.dp))
                AsyncImage(
                    model = info.coatOfArms,
                    contentDescription = null,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.width(16.dp))
        }
        ListFields(info)
    }

    LaunchedEffect(Unit) {
        delay(100)
        atEnd = !atEnd
    }
}

@Composable
private fun ListFields(info: CountryInfo) {
    if (info.population != null) {
        InfoItem(
            title = stringResource(R.string.country_info_population),
            value = info.population.toString(),
            image = {
                Icon(
                    painter = painterResource(R.drawable.people),
                    contentDescription = null,
                    tint = Purple40
                )
            }
        )
    }
    if (info.googleMapLink != null) {
        InfoItem(
            title = stringResource(R.string.country_info_map),
            value = info.googleMapLink,
            image = {
                Icon(
                    painter = painterResource(R.drawable.place),
                    contentDescription = null,
                    tint = Purple40
                )
            }
        )
    }
    if (!info.languages.isNullOrEmpty()) {
        InfoItem(
            title = stringResource(R.string.country_info_languages),
            value = info.languages.values.joinToString(", "),
            image = {
                Icon(
                    painter = painterResource(R.drawable.language),
                    contentDescription = null,
                    tint = Purple40
                )
            }
        )
    }
    if (info.capital != null) {
        InfoItem(
            title = stringResource(R.string.country_info_capital),
            value = info.capital,
            image = {
                Icon(
                    painter = painterResource(R.drawable.castle),
                    contentDescription = null,
                    tint = Purple40
                )
            }
        )
    }
    if (!info.continents.isNullOrEmpty()) {
        InfoItem(
            title = if (info.continents.size == 1) {
                stringResource(R.string.country_info_continent)
            } else {
                stringResource(R.string.country_info_continents)
            },
            value = info.continents.joinToString(", "),
            image = {
                Icon(
                    painter = painterResource(R.drawable.map),
                    contentDescription = null,
                    tint = Purple40
                )
            }
        )
    }
}

@Composable
private fun InfoItem(
    title: String?,
    value: String,
    image: (@Composable () -> Unit)? = null
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (image != null) {
                image()
                Spacer(Modifier.width(12.dp))
            }
            Column {
                if (title != null) {
                    Text(text = title, style = Typography.labelMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(text = value, style = Typography.bodyLarge)
            }
        }
        Divider(thickness = 1.dp, modifier = Modifier.padding(start = 16.dp))
    }

}
