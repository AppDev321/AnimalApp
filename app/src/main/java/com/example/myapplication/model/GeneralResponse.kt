package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("data"    ) var data    : ArrayList<AnimalData> = arrayListOf()
)

data class AnimalData (
    @SerializedName("id"         ) var id         : Int?    = null,
    @SerializedName("animalName" ) var animalName : String? = null,
    @SerializedName("image"      ) var image      : String? = null,
    @SerializedName("video"      ) var video      : String? = null
)

