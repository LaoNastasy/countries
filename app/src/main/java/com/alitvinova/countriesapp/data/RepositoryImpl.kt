package com.alitvinova.countriesapp.data

import com.alitvinova.countriesapp.domain.entity.CountryInfo
import com.alitvinova.countriesapp.domain.entity.CountryListItem
import com.alitvinova.countriesapp.network.Api
import com.alitvinova.countriesapp.network.RetrofitErrorHandler
import com.alitvinova.countriesapp.network.entity.CountryInfoModel
import com.alitvinova.countriesapp.network.entity.CountryListItemModel

class RepositoryImpl(
    private val retrofitErrorHandler: RetrofitErrorHandler,
    private val api: Api,
) : Repository {

    override suspend fun getAllCountries(): List<CountryListItem> =
        retrofitErrorHandler.apiCall {
            api.getAllCountries()
        }.map(CountryListItemModel::asDomainModel)

    override suspend fun getCountryInfoByCode(code: String): CountryInfo =
        retrofitErrorHandler.apiCall {
            api.getCountryInfo(code)
        }.first().asDomainModel()
}

private fun CountryListItemModel.asDomainModel() = CountryListItem(
    name = name.common,
    flag = flags?.png,
    code = cca2,
)

private fun CountryInfoModel.asDomainModel() = CountryInfo(
    name = name.common,
    officialName = name.official,
    flag = flags?.png,
    coatOfArms = coatOfArms?.png,
    population = population,
    googleMapLink = maps?.googleMaps,
    languages = languages,
    capital = capital?.first(),
    continents = continents,
)
