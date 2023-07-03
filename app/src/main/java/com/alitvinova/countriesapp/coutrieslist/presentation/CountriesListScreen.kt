package com.alitvinova.countriesapp.coutrieslist.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alitvinova.countriesapp.R
import com.alitvinova.countriesapp.ui.theme.CountriesAppTheme

@Composable
fun CountriesListScreen(viewModel: CountriesListViewModel) {
    val state = viewModel.state.collectAsState().value
    Box {
        if (state.error != null) {
            Error(viewModel::onRetryClick)
        } else {
            LazyColumn(
                Modifier.background(Color(0xFFFFFFFF))
            ) {
                items(state.countries) { country ->
                    CountryItem(name = country.name, imagePath = country.flag)
                }
            }
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
private fun CountryItem(
    name: String,
    imagePath: String,
) {
    Card(
        Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xC6EFEFEF),
        )
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imagePath,
                contentDescription = null,
                modifier = Modifier.size(36.dp),
            )
            Spacer(Modifier.width(16.dp))
            Text(text = name)
        }
    }
}

@Composable
private fun Error(onRetryClick: () -> Unit) = Box(contentAlignment = Alignment.Center) {
    Text(text = stringResource(R.string.countries_list_error))
    Button(
        onClick = onRetryClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(R.string.countries_list_error_action)
        )
    }
}


@Preview
@Composable
private fun ItemPreview() {
    CountriesAppTheme() {
        CountryItem(name = "Russia", imagePath = "https://flagcdn.com/ru.svg")
    }
}