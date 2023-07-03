package com.alitvinova.countriesapp.coutrieslist.data

import com.alitvinova.countriesapp.domain.entity.CountryListItem
import com.alitvinova.countriesapp.network.Api
import com.alitvinova.countriesapp.network.RetrofitErrorHandler
import com.alitvinova.countriesapp.network.entity.CountryListItemModel

class RepositoryImpl(
    private val retrofitErrorHandler: RetrofitErrorHandler,
    private val api: Api,
) : Repository {

    override suspend fun getAllCountries(): List<CountryListItem> =
        retrofitErrorHandler.apiCall {
            api.getAllCountries()
        }.map(CountryListItemModel::asDomainModel)
}

private fun CountryListItemModel.asDomainModel() = CountryListItem(
    name = name.common,
    flag = flags.png,
)
