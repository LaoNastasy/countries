package com.alitvinova.countriesapp.presentation.info

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun CountryInfoScreen(viewModel: CountryInfoViewModel){
    val state = viewModel.state.collectAsState().value
    Column() {

    }
}
