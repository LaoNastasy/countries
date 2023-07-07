package com.alitvinova.countriesapp.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.alitvinova.countriesapp.R
import com.alitvinova.countriesapp.domain.entity.CountryListItem
import com.alitvinova.countriesapp.navigation.CountryInfoDestination
import com.alitvinova.countriesapp.presentation.ErrorInfo
import com.alitvinova.countriesapp.presentation.Loader
import com.alitvinova.countriesapp.ui.theme.BackgroundPrimary
import com.alitvinova.countriesapp.ui.theme.BackgroundSecondary
import com.alitvinova.countriesapp.ui.theme.BackgroundThirdly
import com.alitvinova.countriesapp.ui.theme.CountriesAppTheme
import com.alitvinova.countriesapp.ui.theme.Purple40
import com.alitvinova.countriesapp.ui.theme.TextPrimary
import com.alitvinova.countriesapp.ui.theme.TextSecondary
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
            Content(
                state = state,
                viewModel = viewModel,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content(
    state: CountriesListState,
    viewModel: CountriesListViewModel,
    onCountryClick: (CountryListItem) -> Unit
) {
    val chooseFilterSheetState = rememberModalBottomSheetState(
        initialValue = if (state.openRegionFilers) ModalBottomSheetValue.Expanded else ModalBottomSheetValue.Hidden,
        confirmValueChange = { value ->
            if (value == ModalBottomSheetValue.Hidden) viewModel.onFillerHide()
            true
        }
    )
    ModalBottomSheetLayout(
        sheetContent = {
            RegionalBlocsBottomSheet(viewModel = viewModel)
        },
        sheetShape = RoundedCornerShape(12.dp).copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        ),
        sheetState = chooseFilterSheetState,
    ) {
        Column {
            Spacer(Modifier.height(16.dp))
            SearchTextField(
                text = state.searchString,
                onTextChanged = viewModel::onSearchStringChanged,
            )
            Spacer(Modifier.height(8.dp))
            FilterBadge(
                checked = state.filter != null,
                placeholder = stringResource(R.string.country_list_regional_bloc),
                onClick = viewModel::onFilterClick,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(8.dp))
            Divider(thickness = 2.dp, modifier = Modifier.fillMaxWidth())
            CountriesList(
                countries = state.filteredCountries,
                onCountryClick = onCountryClick,
            )
        }
    }
}

@Composable
private fun CountriesList(
    countries: List<CountryListItem>,
    onCountryClick: (CountryListItem) -> Unit,
) = LazyColumn {
    item { Spacer(Modifier.height(8.dp)) }
    items(countries.sortedBy { it.name }) { country ->
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
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable { onClick(country) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundThirdly,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
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

@Composable
private fun SearchTextField(
    text: String,
    onTextChanged: (String) -> Unit,
) {
    TextField(
        value = text,
        onValueChange = onTextChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = {
            Text(
                text = stringResource(R.string.country_list_search_hint),
                style = Typography.titleMedium,
                color = TextSecondary,
            )
        },
        textStyle = Typography.titleMedium,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedContainerColor = BackgroundPrimary,
            unfocusedContainerColor = BackgroundPrimary,
            disabledContainerColor = BackgroundPrimary,
            cursorColor = Purple40,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(8.dp),
        visualTransformation = VisualTransformation.None,
    )
}

@Composable
fun RegionalBloc.toStringRes() = stringResource(
    when (this) {
        RegionalBloc.EU -> R.string.country_list_eu
        RegionalBloc.EFTA -> R.string.country_list_efta
        RegionalBloc.CARICOM -> R.string.country_list_caricom
        RegionalBloc.PA -> R.string.country_list_pa
        RegionalBloc.AU -> R.string.country_list_au
        RegionalBloc.USAN -> R.string.country_list_usan
        RegionalBloc.EEU -> R.string.country_list_eeu
        RegionalBloc.AL -> R.string.country_list_al
        RegionalBloc.ASEAN -> R.string.country_list_asean
        RegionalBloc.CAIS -> R.string.country_list_cais
        RegionalBloc.CEFTA -> R.string.country_list_cefta
        RegionalBloc.NAFTA -> R.string.country_list_nafta
        RegionalBloc.SAARC -> R.string.country_list_saarc
    }
)

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