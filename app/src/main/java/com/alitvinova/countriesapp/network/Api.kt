package com.alitvinova.countriesapp.network

import com.alitvinova.countriesapp.network.entity.CountryListItemModel
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("v3.1/all")
    suspend fun getAllCountries(): Response<List<CountryListItemModel>>
}
