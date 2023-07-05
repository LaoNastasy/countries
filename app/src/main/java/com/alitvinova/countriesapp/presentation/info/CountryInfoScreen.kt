package com.alitvinova.countriesapp.presentation.info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alitvinova.countriesapp.domain.entity.CountryInfo
import com.alitvinova.countriesapp.presentation.ErrorInfo

@Composable
fun CountryInfoScreen(viewModel: CountryInfoViewModel) {
    val state = viewModel.state.collectAsState().value
    Box(Modifier.fillMaxSize()) {
        if (state.error != null) {
            ErrorInfo(viewModel::onReloadClick)
        } else if (state.info != null) {
            Content(info = state.info)
        }
        if (state.loading)
            CircularProgressIndicator(
                color = Color(0xFF018786),
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center),
                strokeWidth = 1.dp
            )
    }
}

@Composable
private fun Content(info: CountryInfo) {
    Column() {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = info.name)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = info.officialName)
        Row(Modifier.fillMaxWidth()) {
            AsyncImage(model = info.flag, contentDescription = null, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(24.dp))
            AsyncImage(model = info.coatOfArms, contentDescription = null, modifier = Modifier.weight(1f))
        }
    }
}
