package com.parthjpatel.imageloading.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    private const val BASE_URL = "https://acharyaprashant.org/api/v2/content/misc/"

    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
    }

    val apiService by lazy {
        retrofit.create(APIInterface::class.java)
    }

}