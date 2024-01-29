package com.example.myapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GeneralResponse(
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: ArrayList<Any> = arrayListOf(),
    @SerializedName("singleData") var singleData: FeedDataResponse? = FeedDataResponse(),
)

data class AnimalData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("animalName") var animalName: String? = null,
    @SerializedName("hindi") var hindi: String? = null,
    @SerializedName("marathi") var marathi: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("video") var video: String? = null
)

@Parcelize
data class LifeStageAnimalData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var animalName: String? = null,
    @SerializedName("hindi") var hindi: String? = null,
    @SerializedName("marathi") var marathi: String? = null,
): Parcelable
data class IngredientData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("cp") var cp: Double? = null,
    @SerializedName("price") var price: Double? = null,
    @SerializedName("Qty") var qty: Int? = null,
)


data class FeedDataResponse(
    @SerializedName("category") var categoryList: ArrayList<FeedCategory> = arrayListOf(),
    @SerializedName("items") var feetItemsList:  ArrayList<FeedItem> = arrayListOf(),
)

data class FeedCategory(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("eng") var name: String? = null,
    @SerializedName("hindi") var hindi: String? = null,
    @SerializedName("marathi") var marathi: String? = null,
)



data class FeedItem(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("catID") var catID: Int? = null,
    @SerializedName("subCatID") var subCatID: Int? = null,
    @SerializedName("eng") var name: String? = null,
    @SerializedName("hindi") var hindi: String? = null,
    @SerializedName("marathi") var marathi: String? = null,
    @SerializedName("dm") var dm: Double = 0.0,
    @SerializedName("dcp") var dcp: Double = 0.0,
    @SerializedName("tdn") var tdn: Double = 0.0,
    @SerializedName("cp") var cp: Double = 0.0,
    var categoryName:String = "",
    var amountOfKg :Double = 0.0
)


data class DMChartData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("lifeStageID") var lifeStageID: Int? = null,
    @SerializedName("weight") var weight: Double = 0.0,
    @SerializedName("dm") var dm: Double = 0.0,
    @SerializedName("dcp") var dcp: Double = 0.0,
    @SerializedName("tdn") var tdn: Double = 0.0,
    @SerializedName("mcal") var mcal: Double = 0.0,
)
