package com.alitvinova.countriesapp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alitvinova.countriesapp.ui.theme.Purple40

@Composable
fun Loader(modifier: Modifier = Modifier) = Box(modifier.fillMaxSize()) {
    CircularProgressIndicator(
        color = Purple40,
        modifier = Modifier
            .size(40.dp)
            .align(Alignment.Center),
        strokeWidth = 3.dp
    )
}
