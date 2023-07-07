package com.alitvinova.countriesapp.presentation.list

import com.alitvinova.countriesapp.domain.entity.CountryListItem

data class CountriesListState(
    val loading: Boolean = true,
    val countries: List<CountryListItem> = emptyList(),
    val error: Exception? = null,
    val searchString: String = "",
    val filter: RegionalBloc? = null,
    val openRegionFilers: Boolean = false,
) {
    val filteredCountries: List<CountryListItem>
        get() = countries.filter { it.name.contains(searchString, ignoreCase = true) }
}

enum class RegionalBloc {
    EU,
    EFTA,
    CARICOM,
    PA,
    AU,
    USAN,
    EEU,
    AL,
    ASEAN,
    CAIS,
    CEFTA,
    NAFTA,
    SAARC;
}
