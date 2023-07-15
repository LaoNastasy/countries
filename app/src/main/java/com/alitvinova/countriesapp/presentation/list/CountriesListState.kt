package com.alitvinova.countriesapp.presentation.list

import com.alitvinova.countriesapp.domain.entity.CountryListItem
import com.alitvinova.countriesapp.domain.entity.RegionalBloc
import com.alitvinova.countriesapp.network.DomainException

data class CountriesListState(
    val loading: Boolean = true,
    val countries: List<CountryListItem> = emptyList(),
    val filteredCountries: List<CountryListItem> = emptyList(),
    val error: DomainException? = null,
    val searchString: String = "",
    val filter: RegionalBloc? = null,
    val appliedFilter: RegionalBloc? = null,
    val openRegionFilers: Boolean = false,
)
