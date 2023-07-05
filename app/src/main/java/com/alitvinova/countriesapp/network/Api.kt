package com.alitvinova.countriesapp.network

import com.alitvinova.countriesapp.network.entity.CountryInfoModel
import com.alitvinova.countriesapp.network.entity.CountryListItemModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("v3.1/all")
    suspend fun getAllCountries(): Response<List<CountryListItemModel>>

    @GET("v3.1/alpha/{code}")
    suspend fun getCountryInfo(@Path("code") code: String): Response<CountryInfoModel>
}
