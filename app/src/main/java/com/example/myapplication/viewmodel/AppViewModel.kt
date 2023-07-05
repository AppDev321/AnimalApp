package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.*
import com.example.myapplication.retrofit.ApiClient
import com.example.myapplication.retrofit.ApiInterface
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppViewModel : ViewModel() {
    private val _snackMessage = MutableSharedFlow<String>()
    val snackMessage = _snackMessage.asSharedFlow()


    private val _apiResult = MutableSharedFlow<Result<GeneralResponse>>()
    val apiResult = _apiResult.asSharedFlow()

    private var _feedCatList : MutableList<FeedCategory> = arrayListOf()
    val getFeedCategoryList = _feedCatList

    private var _feedSubCatList : MutableList<FeedSubCategory> = arrayListOf()
    val getfeedSubCatList = _feedSubCatList

    private var _feedItemsList : MutableList<FeedItem> = arrayListOf()
    val getfeedItemsList = _feedItemsList

     var _feedDataResponse = FeedDataResponse()
     val getFeedDataResposne = _feedDataResponse

    fun showMessage(message: String) {
        viewModelScope.launch {
            _snackMessage.emit(message)
        }
    }


    fun loginUser(phone: String) {
        viewModelScope.launch {
            _apiResult.emit(Result.Loading)
            try {
                val apiInterface = ApiClient.client.create(ApiInterface::class.java)
                val call = apiInterface.loginUser("Login", phone)
                val response = withContext(Dispatchers.IO) {
                    call.execute().body() as GeneralResponse
                }
                _apiResult.emit(Result.Success(response))
            } catch (e: Exception) {
                _apiResult.emit(Result.Error(e))
            }
        }
    }

    fun fetchIngredientList() {
        viewModelScope.launch {
            _apiResult.emit(Result.Loading)
            try {
                val apiInterface = ApiClient.client.create(ApiInterface::class.java)
                val call = apiInterface.getAnimalList("ingredient-get")
                val response = withContext(Dispatchers.IO) {
                    call.execute().body() as GeneralResponse
                }


                _apiResult.emit(Result.Success(response))
            } catch (e: Exception) {
                _apiResult.emit(Result.Error(e))
            }
        }
    }


    fun fetchAnimalList() {
        viewModelScope.launch {
            _apiResult.emit(Result.Loading)
            try {
                val apiInterface = ApiClient.client.create(ApiInterface::class.java)
                val call = apiInterface.getAnimalList("animal-get")
                val response = withContext(Dispatchers.IO) {
                    call.execute().body() as GeneralResponse
                }
                _apiResult.emit(Result.Success(response))

            } catch (e: Exception) {
                _apiResult.emit(Result.Error(e))
            }
        }
    }


     fun calculateAnimalWeight(girth: Int, length: Int): Double {
        return ((girth * girth * length) / 300)*0.454
    }

    fun fetchFeedCategoryList(wantResponse:Boolean= false) {
        viewModelScope.launch {

            if(wantResponse)
                _apiResult.emit(Result.Loading)
            try {
                val apiInterface = ApiClient.client.create(ApiInterface::class.java)
                val call = apiInterface.getFeedCatList("feedCat-get")
                val response = withContext(Dispatchers.IO) {
                    call.execute().body() as GeneralResponse
                }

                val data = response.singleData as FeedDataResponse
                _feedDataResponse = data
                _feedCatList.addAll(data.categoryList)
                _feedSubCatList.addAll(data.subCategoryList)
                _feedItemsList.addAll(data.feetItemsList)

                if(wantResponse)
                    _apiResult.emit(Result.Success(response))
            } catch (e: Exception) {
                if(wantResponse)
                    _apiResult.emit(Result.Error(e))
            }
        }
    }
}


sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}