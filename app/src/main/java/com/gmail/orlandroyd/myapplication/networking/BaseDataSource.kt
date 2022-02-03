package com.gmail.orlandroyd.myapplication.networking

import android.util.Log
import retrofit2.Response

/**
 * This helps to properly handle the response gotten from the API - Be it error, success etc
 */
abstract class BaseDataSource {

    private val tag = "CONNECTION"

    /**
     * API RETROFIT SAFE-CALL
     */
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): DataState<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return DataState.success(body)
                }
            } else {
                return DataState.error("No se pudo conectar con el servidor")
            }
            return DataState.failed("Algo salió mal, inténtelo nuevamente")
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            return DataState.failed("No se pudo establecer contacto con el servidor, revise su conexión a Iternet")
        }
    }
}