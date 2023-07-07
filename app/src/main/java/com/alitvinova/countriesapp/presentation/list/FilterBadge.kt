package com.alitvinova.countriesapp.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alitvinova.countriesapp.ui.theme.PurpleBackground
import com.alitvinova.countriesapp.ui.theme.Purple40
import com.alitvinova.countriesapp.ui.theme.TextPrimary
import com.alitvinova.countriesapp.ui.theme.Typography
import com.alitvinova.countriesapp.ui.theme.White

@Composable
fun FilterBadge(
    checked: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (checked) Purple40 else PurpleBackground
        )
    ) {
        Text(
            text = text,
            style = Typography.labelLarge,
            color = if(checked) White else TextPrimary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        )
    }
}
