package com.parthjpatel.imageloading.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parthjpatel.imageloading.model.ResponseModel
import com.parthjpatel.imageloading.network.MainRepository
import com.parthjpatel.imageloading.network.NetworkResult
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val mainRepository: MainRepository by lazy {
        MainRepository()
    }

    private val _imageList: MutableLiveData<NetworkResult<ArrayList<ResponseModel>>> =
        MutableLiveData()
    val imageList: LiveData<NetworkResult<ArrayList<ResponseModel>>> = _imageList

    init {
        getImageList(100)
    }

    fun getImageList(itemPerPage: Int) {
        viewModelScope.launch {
            _imageList.postValue(NetworkResult.Loading())
            viewModelScope.launch {
                _imageList.postValue(mainRepository.getAllImage(itemPerPage))
            }
        }
    }

}