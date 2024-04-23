package com.parthjpatel.imageloading.network

import org.json.JSONObject
import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            } else {
                val error = response.errorBody()?.string()
                val message = StringBuilder()
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("Message"))
                    } catch (e: Exception) {
                        message.append(e.message)
                    }
                }
                return NetworkResult.Error(message.toString())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.toString())
        }
        return NetworkResult.Error("Unknown Error")
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}