package com.alitvinova.countriesapp.network

import com.alitvinova.countriesapp.network.entity.info.CountryInfoModel
import com.alitvinova.countriesapp.network.entity.bloc.CountryBlocListItemModel
import com.alitvinova.countriesapp.network.entity.all.CountryListItemModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("v3.1/all")
    suspend fun getAllCountries(): Response<List<CountryListItemModel>>

    @GET("v3.1/alpha/{code}")
    suspend fun getCountryInfo(@Path("code") code: String): Response<List<CountryInfoModel>>

    @GET("v2/regionalbloc/{bloc}")
    suspend fun getFilteredCountries(@Path("bloc") bloc: String): Response<List<CountryBlocListItemModel>>
}
