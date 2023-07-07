package com.alitvinova.countriesapp.presentation.list

import com.alitvinova.countriesapp.domain.entity.CountryListItem
import com.alitvinova.countriesapp.domain.entity.RegionalBloc

data class CountriesListState(
    val loading: Boolean = true,
    val countries: List<CountryListItem> = emptyList(),
    val error: Exception? = null,
    val searchString: String = "",
    val filter: RegionalBloc? = null,
    val appliedFilter: RegionalBloc? = null,
    val openRegionFilers: Boolean = false,
) {
    val filteredCountries: List<CountryListItem>
        get() = countries.filter { it.name.contains(searchString, ignoreCase = true) }
}
