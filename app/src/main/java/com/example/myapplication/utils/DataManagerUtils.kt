package com.example.myapplication.utils

import android.util.Log
import com.example.myapplication.model.DMChartData
import com.example.myapplication.model.FeedDataResponse
import com.example.myapplication.model.FeedItem

object DataManagerUtils {

    var feedDataResponse: FeedDataResponse = FeedDataResponse()
    var lifeStageActivityData: LifeStageActivityData = LifeStageActivityData()
    var selectedFeetItem: MutableList<FeedItem> = arrayListOf()
    var allSelectedItems: MutableList<FeedItem> = arrayListOf() //skip cat 1,2 items now
    var dmChartList: MutableList<DMChartData> = arrayListOf()
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
        return try {
            getDMChartAccordingToWeight().dm.roundTo2DecimalPlaces()
        } catch (e:Exception) {
            0.0
        }
    // return animalWeight.percentOf(2.5)
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

    fun getDMValueBySelectedItem(item: FeedItem): Double {
        return when (item.catID) {
            3 -> dryRough
            4 -> legumeGreenRough
            5 -> nonLegumeGreenRough
            2,1-> constraintRequire
            else -> 1.0
        }
    }

    fun getSameCatSelectedItem(items: List<FeedItem>, selectedItem: FeedItem): Int =
        items.count { it.catID == selectedItem.catID }

    fun getItemDMCalculation(items: List<FeedItem>, selectedItem: FeedItem): Double {

       return getDMValueBySelectedItem(selectedItem) / getSameCatSelectedItem(items, selectedItem)

    }
    fun getItemFooderCalculation(items: List<FeedItem>, selectedItem: FeedItem): Double {
        val selectedItemDMCalc = getItemDMCalculation(items,selectedItem)
         return with(selectedItem) {
            Log.e("Cal"," DM = ${dm}")
            val dmValue = dm

            val fooderCalc = (100 * selectedItemDMCalc) /dmValue
            Log.e("Cal"," 100 * ${selectedItemDMCalc}/${dmValue} = ${ fooderCalc.roundTo2DecimalPlaces()}")
            fooderCalc.roundTo2DecimalPlaces()
        }
    }

    fun getItemDCPCalculation(items: List<FeedItem>, selectedItem: FeedItem): Double {
        val selectedItemFoodCalc =  getItemDMCalculation(items,selectedItem)//getItemFooderCalculation(items,selectedItem)
        return with(selectedItem) {
            Log.e("Cal"," DCP = $dcp")
            val cpVaue = dcp
            val fooddcp = (cpVaue * selectedItemFoodCalc) /100
            Log.e("Cal"," ${cpVaue} * ${selectedItemFoodCalc}/${100} = ${ fooddcp.roundTo2DecimalPlaces()}")
            fooddcp.roundTo2DecimalPlaces()
        }
    }

    fun getItemTDNCalculation(items: List<FeedItem>, selectedItem: FeedItem): Double {
        val selectedItemDMCalc = getItemDMCalculation(items,selectedItem)
        return with(selectedItem) {
            Log.e("Cal"," TDN = $tdn")
            val tdnValue = tdn
            val fooddcp = (tdnValue * selectedItemDMCalc) /100
            Log.e("Cal"," ${tdnValue} * ${selectedItemDMCalc}/${100} = ${ fooddcp.roundTo2DecimalPlaces()}")
            fooddcp.roundTo2DecimalPlaces()
        }
    }
    fun getCurrentTotalDCP(): Double  = run {
        val lifestageCalc = DataManagerUtils.lifeStageActivityData
        var totalDCP = 0.0
        DataManagerUtils.selectedFeetItem.toList().map { item ->
            totalDCP += lifestageCalc.getItemDCPCalculation(DataManagerUtils.selectedFeetItem, item)
        }
        totalDCP.roundTo2DecimalPlaces()
    }
    fun getCurrentTotalTDN(): Double  = run {
        val lifestageCalc = DataManagerUtils.lifeStageActivityData
        var totalTDN = 0.0
        DataManagerUtils.selectedFeetItem.toList().map { item ->
            totalTDN += lifestageCalc.getItemTDNCalculation(DataManagerUtils.selectedFeetItem, item)
        }
        totalTDN.roundTo2DecimalPlaces()
    }
    fun getDMChartAccordingToWeight():DMChartData
    {
        return DataManagerUtils.dmChartList.firstOrNull { it.weight >= animalWeight } ?: DataManagerUtils.dmChartList.last()
    }

    fun getRemainingDCPChart():String
    {
        val getDMChart = getDMChartAccordingToWeight()
      val requiredDCPFromBodyWeight =  getDMChart.dcp.roundTo2DecimalPlaces()
        val currentDCPFromBodyWeight =  getCurrentTotalDCP()
        val calculatedDCPDifference = requiredDCPFromBodyWeight - currentDCPFromBodyWeight

        val returnData = "Total Req: DM=${getDMChart.dm} , DCP = ${getDMChart.dcp}, tdn = ${getDMChart.tdn} \n"+
                "Curent Req: DM=${calcRough()} , DCP = ${getCurrentTotalDCP()}, tdn = ${getCurrentTotalTDN()} \n"+
                "Diff  : DM=${(getDMChart.dm - calcRough()).roundTo2DecimalPlaces() } , DCP = ${(getDMChart.dcp-getCurrentTotalDCP()).roundTo2DecimalPlaces() }, tdn = ${(getDMChart.tdn - getCurrentTotalTDN()).roundTo2DecimalPlaces()} \n"
        return returnData
    }

    fun getConReqOfDryItem():Double
    {
        val getDMChart = getDMChartAccordingToWeight()
        val currentTotalConReq = calcConstraintRequire()
        var difDCP = (getDMChart.dcp - getCurrentTotalDCP()).roundTo2DecimalPlaces()
        var diffDM = (getDMChart.dm - calcRough()).roundTo2DecimalPlaces()
       // difDCP = (difDCP * 100 / currentTotalConReq).roundTo2DecimalPlaces()
        difDCP = (difDCP * 100 / diffDM).roundTo2DecimalPlaces()
        return difDCP
    }
    fun get40PerConReqDCPItemAvg()
    {

    }

    fun getLess40PerConReqDCPItemAvg()
    {

    }


}


infix fun Double.percentOf(value: Double): Double {
    return (this * (value / 100)).roundTo2DecimalPlaces()
}
fun Double.roundTo2DecimalPlaces(): Double {
    return "%.2f".format(this).toDouble()
}

fun Double.partOf(numerator: Int, denominator: Int): Double {
    return ( this * numerator / denominator).roundTo2DecimalPlaces()
}


//40% DCP wlay alag kr lo  phr on ka average
// less walon ka avg