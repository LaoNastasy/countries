package com.alitvinova.countriesapp.presentation.info

import com.alitvinova.countriesapp.domain.entity.CountryInfo

data class CountryInfoState(
    val info: CountryInfo? = null,
    val loading: Boolean = true,
    val error: Exception? = null,
    val code: String,
)
