package com.alitvinova.countriesapp.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.alitvinova.countriesapp.domain.entity.CountryListItem
import com.alitvinova.countriesapp.navigation.CountryInfoDestination
import com.alitvinova.countriesapp.presentation.ErrorInfo
import com.alitvinova.countriesapp.presentation.Loader
import com.alitvinova.countriesapp.ui.theme.BackgroundPrimary
import com.alitvinova.countriesapp.ui.theme.BackgroundSecondary
import com.alitvinova.countriesapp.ui.theme.CountriesAppTheme
import com.alitvinova.countriesapp.ui.theme.Typography

@Composable
fun CountriesListScreen(
    viewModel: CountriesListViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value
    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundSecondary)
    ) {
        if (state.error != null) {
            ErrorInfo(viewModel::onRetryClick)
        } else {
            CountriesList(
                countries = state.countries,
                onCountryClick = {
                    navController.navigate(
                        route = CountryInfoDestination.createRoute(it.code),
                    )
                },
            )
        }
        if (state.loading) Loader()
    }
}

@Composable
private fun CountriesList(
    countries: List<CountryListItem>,
    onCountryClick: (CountryListItem) -> Unit,
) = LazyColumn {
    item { Spacer(Modifier.height(16.dp)) }
    items(countries) { country ->
        CountryItem(country = country, onClick = onCountryClick)
    }
}

@Composable
private fun CountryItem(
    country: CountryListItem,
    onClick: (CountryListItem) -> Unit,
) {
    Card(
        Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onClick(country) },
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundPrimary,
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = country.flag,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
            )
            Spacer(Modifier.width(16.dp))
            Text(text = country.name, style = Typography.titleMedium)
        }
    }
}


@Preview
@Composable
private fun ItemPreview() {
    CountriesAppTheme() {
        CountryItem(
            country = CountryListItem(
                name = "Russia",
                flag = "https://flagcdn.com/ru.svg",
                code = "ru"
            ),
            onClick = {},
        )
    }
}