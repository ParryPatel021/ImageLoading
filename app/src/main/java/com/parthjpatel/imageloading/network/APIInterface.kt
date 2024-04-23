package com.parthjpatel.imageloading.network

import com.parthjpatel.imageloading.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("media-coverages")
    suspend fun getImageList(@Query("limit") itemPerPage: Int): Response<ArrayList<ResponseModel>>

}