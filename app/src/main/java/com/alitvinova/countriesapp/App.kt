package com.alitvinova.countriesapp

import android.app.Application
import com.alitvinova.countriesapp.coutrieslist.data.Repository
import com.alitvinova.countriesapp.coutrieslist.data.RepositoryImpl
import com.alitvinova.countriesapp.network.Api
import com.alitvinova.countriesapp.network.RetrofitErrorHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    lateinit var retrofit: Retrofit
    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()

        retrofit = createRetrofit()
        val api = retrofit.create(Api::class.java)
        repository = RepositoryImpl(
            retrofitErrorHandler = RetrofitErrorHandler(),
            api = api
        )
    }

    private fun createRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}