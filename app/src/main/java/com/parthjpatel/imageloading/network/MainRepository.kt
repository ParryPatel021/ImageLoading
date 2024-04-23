package com.parthjpatel.imageloading.network

import com.parthjpatel.imageloading.model.ResponseModel

class MainRepository : BaseApiResponse() {

    suspend fun getAllImage(itemPerPage: Int): NetworkResult<ArrayList<ResponseModel>> {
        return safeApiCall { APIClient.apiService.getImageList(itemPerPage) }
    }

}