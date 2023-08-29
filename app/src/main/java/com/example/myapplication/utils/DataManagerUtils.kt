package com.example.myapplication.utils

import com.example.myapplication.model.FeedDataResponse
import com.example.myapplication.model.FeedItem

object DataManagerUtils {

     var feedDataResponse: FeedDataResponse = FeedDataResponse()
     var lifeStageActivityData :LifeStageActivityData =LifeStageActivityData()
     var selectedFeetItem :MutableList<FeedItem> = arrayListOf()
}


class LifeStageActivityData(
    var animalName :String ="",
    var animalWeight:String ="",
    var avgDailyWeightGain: Int = 0,
    var lifeStage: String = "",
    var milkYield: Int = 0,
    var fat: Int = 0,
    var pregnancyDuration: String = ""

)