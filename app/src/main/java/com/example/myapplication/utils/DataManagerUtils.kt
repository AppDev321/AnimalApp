package com.example.myapplication.utils

import com.example.myapplication.model.FeedDataResponse
import com.example.myapplication.model.FeedItem

object DataManagerUtils {

    var feedDataResponse: FeedDataResponse = FeedDataResponse()
    var lifeStageActivityData: LifeStageActivityData = LifeStageActivityData()
    var selectedFeetItem: MutableList<FeedItem> = arrayListOf()
}


class LifeStageActivityData(
    var animalName: String = "",
    var animalWeight: Double = 0.0,
    var avgDailyWeightGain: Int = 0,
    var lifeStage: String = "",
    var milkYield: Int = 0,
    var fat: Int = 0,
    var pregnancyDuration: String = "",
) {
    var dryMatter = 0.0
    var constraintRequire = 0.0
    var rough = 0.0
    var dryRough = 0.0
    var greenRough = 0.0
    var nonLegumeGreenRough = 0.0
    var legumeGreenRough = 0.0

    init {
        dryMatter = calcDryMatter()
        constraintRequire = calcConstraintRequire()
        rough = calcRough()
        dryRough = calcDryRough()
        greenRough = calcGreenRough()
        nonLegumeGreenRough = calcNonLegumeGreenRough()
        legumeGreenRough = calcLegumeGreenRough()
    }

    private fun calcDryMatter(): Double {
        return animalWeight.percentOf(2.5)
    }

    private fun calcConstraintRequire(): Double {
        return dryMatter.partOf(1, 3)
    }

    private fun calcRough(): Double {
        return dryMatter.partOf(2, 3)
    }

    private fun calcDryRough(): Double {
        return rough.partOf(2, 3)
    }

    private fun calcGreenRough(): Double {
        return rough.partOf(1, 3)
    }


    private fun calcNonLegumeGreenRough(): Double {
        return greenRough.partOf(3, 4)
    }

    private fun calcLegumeGreenRough(): Double {
        return greenRough.partOf(1, 4)
    }

}


infix fun Double.percentOf(value: Double): Double {
    return String.format("%.2f", this * (value / 100)).toDouble()
}

fun Double.partOf(numerator: Int,denominator:Int): Double {
    return  String.format("%.2f", this * numerator / denominator).toDouble()
}