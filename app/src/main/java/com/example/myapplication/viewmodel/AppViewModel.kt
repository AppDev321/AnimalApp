package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.AnimalData
import com.example.myapplication.model.GeneralResponse
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


    fun showMessage(message: String) {
        viewModelScope.launch {
            _snackMessage.emit(message)
        }
    }


    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _apiResult.emit(Result.Loading)
            try {
                val apiInterface = ApiClient.client.create(ApiInterface::class.java)
                val call = apiInterface.loginUser("Login", email, password)
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


}


sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}