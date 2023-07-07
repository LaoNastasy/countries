package com.alitvinova.countriesapp.data

import com.alitvinova.countriesapp.domain.entity.CountryInfo
import com.alitvinova.countriesapp.domain.entity.CountryListItem
import com.alitvinova.countriesapp.presentation.list.RegionalBloc

interface Repository {
    suspend fun getAllCountries(): List<CountryListItem>

    suspend fun getCountryInfoByCode(code: String): CountryInfo

    suspend fun getBlocCountries(bloc: RegionalBloc): List<CountryListItem>
}
