package com.alitvinova.countriesapp.data

import com.alitvinova.countriesapp.domain.entity.CountryInfo
import com.alitvinova.countriesapp.domain.entity.CountryListItem

interface Repository {
    suspend fun getAllCountries(): List<CountryListItem>

    suspend fun getCountryInfoByCode(code: String): CountryInfo
}
