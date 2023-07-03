package com.alitvinova.countriesapp.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RetrofitErrorHandler {
    suspend fun <T : Any> apiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        call: suspend () -> Response<T>
    ): T = withContext(dispatcher) {
        val response: Response<T> = call.invoke()

        if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                val request = (response.raw() as okhttp3.Response).request
                throw Exception("response.body() cannot be null for ${request.method} ${request.url}")
            } else {
                return@withContext body
            }
        } else {
            throw DomainException
        }
    }
}

object DomainException : Exception()


