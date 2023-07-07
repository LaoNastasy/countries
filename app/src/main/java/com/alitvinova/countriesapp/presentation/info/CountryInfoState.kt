package com.alitvinova.countriesapp.presentation.info

import com.alitvinova.countriesapp.domain.entity.CountryInfo
import com.alitvinova.countriesapp.network.DomainException

data class CountryInfoState(
    val info: CountryInfo? = null,
    val loading: Boolean = true,
    val error: DomainException? = null,
    val code: String,
)
