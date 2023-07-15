package com.alitvinova.countriesapp.network

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException

class RetrofitErrorHandler {
    suspend fun <T : Any> apiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        call: suspend () -> Response<T>
    ): T? = withContext(dispatcher) {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (e: Throwable) {
            throw mapNetworkThrowableToDomain(e)
        }

        if (response.isSuccessful) {
            return@withContext response.body()
        } else {
            throw DomainException.Unknown
        }
    }
}


private fun mapNetworkThrowableToDomain(throwable: Throwable): Throwable = when (throwable) {
    is UnknownHostException -> DomainException.NoConnectionException
    is HttpException -> DomainException.NoConnectionException
    is ConnectException -> DomainException.NoConnectionException
    is InterruptedIOException -> DomainException.TimeOutException
    is CancellationException -> throwable
    else -> {
        DomainException.Unknown
    }
}

sealed class DomainException : Exception() {
    object TimeOutException : DomainException()
    object NoConnectionException : DomainException()
    object Unknown : DomainException()
    data class TextException(val text: String) : DomainException()
}
