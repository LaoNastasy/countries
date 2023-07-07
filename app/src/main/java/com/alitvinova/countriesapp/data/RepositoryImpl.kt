package com.alitvinova.countriesapp.data

import com.alitvinova.countriesapp.domain.entity.CountryInfo
import com.alitvinova.countriesapp.domain.entity.CountryListItem
import com.alitvinova.countriesapp.network.Api
import com.alitvinova.countriesapp.network.RetrofitErrorHandler
import com.alitvinova.countriesapp.network.entity.all.CountryListItemModel
import com.alitvinova.countriesapp.network.entity.bloc.CountryBlocListItemModel
import com.alitvinova.countriesapp.network.entity.info.CountryInfoModel
import com.alitvinova.countriesapp.presentation.list.RegionalBloc

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

    override suspend fun getBlocCountries(bloc: RegionalBloc): List<CountryListItem> =
        retrofitErrorHandler.apiCall {
            api.getFilteredCountries(bloc.toServerModel())
        }.map(CountryBlocListItemModel::asDomainModel)
}

private fun CountryListItemModel.asDomainModel() = CountryListItem(
    name = name?.common ?: "",
    flag = flags?.png,
    code = requireNotNull(cca2),
)

private fun CountryBlocListItemModel.asDomainModel() = CountryListItem(
    name = name ?: "",
    flag = flags?.png,
    code = requireNotNull(alpha2Code),
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

private fun RegionalBloc.toServerModel(): String = when (this) {
    RegionalBloc.EU -> "eu"
    RegionalBloc.EFTA -> "efta"
    RegionalBloc.CARICOM -> "caricom"
    RegionalBloc.PA -> "pa"
    RegionalBloc.AU -> "au"
    RegionalBloc.USAN -> "usan"
    RegionalBloc.EEU -> "eeu"
    RegionalBloc.AL -> "al"
    RegionalBloc.ASEAN -> "asean"
    RegionalBloc.CAIS -> "cais"
    RegionalBloc.CEFTA -> "cefta"
    RegionalBloc.NAFTA -> "nafta"
    RegionalBloc.SAARC -> "saarc"
}
