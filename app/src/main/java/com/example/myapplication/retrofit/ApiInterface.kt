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
        @Field("vEmail") email:String,
        @Field("vPassword") password:String,
        @Field("vFirstName") firstName:String,
        @Field("vPhone") phone:String,
        @Field("vVillage") village:String,
        @Field("vDistrict") district:String,
        @Field("vState") state:String,
        @Field("vTehsil") tehsil:String,
        @Field("vPin") pin:String,
        @Field("vLand") land:String,
        @Field("vHybrid") hybrid:String,
        @Field("vNative") native:String,
        @Field("vBuffalow") buffalow:String,
        @Field("vGoat") goat:String,
        @Field("vOx") ox:String,
        @Field("vSheep") sheep:String,

        ): Call<GeneralResponse>



    @POST("exec")
    @FormUrlEncoded
    fun getAnimalList(
        @Field("action") action: String

    ): Call<GeneralResponse>



    @POST("exec")
    @FormUrlEncoded
    fun getIngredientList(
        @Field("action") action: String

    ): Call<GeneralResponse>


    @POST("exec")
    @FormUrlEncoded
    fun getFeedCatList(
        @Field("action") action: String,
    ): Call<GeneralResponse>


}