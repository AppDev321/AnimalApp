package com.example.myapplication.retrofit

import com.example.myapplication.model.GeneralResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

import retrofit2.http.POST

interface ApiInterface {



    @POST("exec")
    @FormUrlEncoded
    fun loginUser(
        @Field("action") action: String,
        @Field("vEmail") email: String,
        @Field("vPassword") password: String
    ): Call<GeneralResponse>


    @POST("exec")
    @FormUrlEncoded
    fun signUpUser(
        @Field("action") action: String,
        @Field("vEmail") email: String,
        @Field("vPassword") password: String,
        @Field("vFirstName") firstName: String,
        @Field("vLastName") lastName: String,
        @Field("vPhone") phone: String,
    ): Call<GeneralResponse>



    @POST("exec")
    @FormUrlEncoded
    fun getAnimalList(
        @Field("action") action: String

    ): Call<GeneralResponse>
}