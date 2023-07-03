package com.alitvinova.countriesapp.coutrieslist.data

import com.alitvinova.countriesapp.domain.entity.CountryListItem

interface Repository {
    suspend fun getAllCountries(): List<CountryListItem>
}
