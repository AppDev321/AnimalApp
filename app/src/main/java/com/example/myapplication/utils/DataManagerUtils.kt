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
        } catch (e: Exception) {
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
            2, 1 -> constraintRequire
            else -> 1.0
        }
    }

    fun getSameCatSelectedItem(items: List<FeedItem>, selectedItem: FeedItem): Int =
        items.count { it.catID == selectedItem.catID }

    fun getItemDMCalculation(items: List<FeedItem>, selectedItem: FeedItem): Double {

        return getDMValueBySelectedItem(selectedItem) / getSameCatSelectedItem(items, selectedItem)

    }

    fun getItemFooderCalculation(items: List<FeedItem>, selectedItem: FeedItem): Double {
        val selectedItemDMCalc = getItemDMCalculation(items, selectedItem)
        return with(selectedItem) {
            Log.e("Cal", " DM = ${dm}")
            val dmValue = dm

            val fooderCalc = (100 * selectedItemDMCalc) / dmValue
            Log.e(
                "Cal",
                " 100 * ${selectedItemDMCalc}/${dmValue} = ${fooderCalc.roundTo2DecimalPlaces()}"
            )
            fooderCalc.roundTo2DecimalPlaces()
        }
    }

    fun getItemDCPCalculation(items: List<FeedItem>, selectedItem: FeedItem): Double {
        val selectedItemFoodCalc =
            getItemDMCalculation(items, selectedItem)//getItemFooderCalculation(items,selectedItem)
        return with(selectedItem) {
            Log.e("Cal", " DCP = $dcp")
            val cpVaue = dcp
            val fooddcp = (cpVaue * selectedItemFoodCalc) / 100
            Log.e(
                "Cal",
                " ${cpVaue} * ${selectedItemFoodCalc}/${100} = ${fooddcp.roundTo2DecimalPlaces()}"
            )
            fooddcp.roundTo2DecimalPlaces()
        }
    }

    fun getItemTDNCalculation(items: List<FeedItem>, selectedItem: FeedItem): Double {
        val selectedItemDMCalc = getItemDMCalculation(items, selectedItem)
        return with(selectedItem) {
            Log.e("Cal", " TDN = $tdn")
            val tdnValue = tdn
            val fooddcp = (tdnValue * selectedItemDMCalc) / 100
            Log.e(
                "Cal",
                " ${tdnValue} * ${selectedItemDMCalc}/${100} = ${fooddcp.roundTo2DecimalPlaces()}"
            )
            fooddcp.roundTo2DecimalPlaces()
        }
    }

    fun getCurrentTotalDCP(): Double = run {
        val lifestageCalc = DataManagerUtils.lifeStageActivityData
        var totalDCP = 0.0
        DataManagerUtils.selectedFeetItem.toList().map { item ->
            totalDCP += lifestageCalc.getItemDCPCalculation(DataManagerUtils.selectedFeetItem, item)
        }
        totalDCP.roundTo2DecimalPlaces()
    }

    fun getCurrentTotalTDN(): Double = run {
        val lifestageCalc = DataManagerUtils.lifeStageActivityData
        var totalTDN = 0.0
        DataManagerUtils.selectedFeetItem.toList().map { item ->
            totalTDN += lifestageCalc.getItemTDNCalculation(DataManagerUtils.selectedFeetItem, item)
        }
        totalTDN.roundTo2DecimalPlaces()
    }

    fun getDMChartAccordingToWeight(): DMChartData {
        return DataManagerUtils.dmChartList.firstOrNull { it.weight >= animalWeight }
            ?: DataManagerUtils.dmChartList.last()
    }

    fun getRemainingDCPChart(): String {
        val getDMChart = getDMChartAccordingToWeight()
        val requiredDCPFromBodyWeight = getDMChart.dcp.roundTo2DecimalPlaces()
        val currentDCPFromBodyWeight = getCurrentTotalDCP()
        val calculatedDCPDifference = requiredDCPFromBodyWeight - currentDCPFromBodyWeight

        val returnData =
            "Total Req: DM=${getDMChart.dm} , DCP = ${getDMChart.dcp}, tdn = ${getDMChart.tdn} \n" +
                    "Curent Req: DM=${calcRough()} , DCP = ${getCurrentTotalDCP()}, tdn = ${getCurrentTotalTDN()} \n" +
                    "Diff  : DM=${(getDMChart.dm - calcRough()).roundTo2DecimalPlaces()} , DCP = ${(getDMChart.dcp - getCurrentTotalDCP()).roundTo2DecimalPlaces()}, tdn = ${(getDMChart.tdn - getCurrentTotalTDN()).roundTo2DecimalPlaces()} \n"
        return returnData
    }

    fun getConReqOfDryItem(): Double {
        val getDMChart = getDMChartAccordingToWeight()
        val currentTotalConReq = calcConstraintRequire()
        var difDCP = (getDMChart.dcp - getCurrentTotalDCP()).roundTo2DecimalPlaces()
        var diffDM = (getDMChart.dm - calcRough()).roundTo2DecimalPlaces()
        // difDCP = (difDCP * 100 / currentTotalConReq).roundTo2DecimalPlaces()
        difDCP = (difDCP * 100 / diffDM).roundTo2DecimalPlaces()
        return difDCP
    }

    fun get40PerConReqDCPItemAvg(selectedFeetItem: List<FeedItem>): List<FeedItem> {
        return selectedFeetItem.filter {
            it.dcp >= 44
        }
    }

    fun getLess40PerConReqDCPItemAvg(selectedFeetItem: List<FeedItem>): List<FeedItem> {
        return selectedFeetItem.filter {
            it.dcp < 44
        }
    }

    fun pearsonSquareFormula(nonGreenItems: List<FeedItem> ,greenItems : List<FeedItem> ):String{
        val itemsUpperRightCorner = get40PerConReqDCPItemAvg(nonGreenItems)
        val itemsBottomRightCorner = getLess40PerConReqDCPItemAvg(nonGreenItems)
        val avgDCPUpperRight = itemsUpperRightCorner.map { it.dcp }.average().roundTo2DecimalPlaces()
        val avgDCPBottomRight = itemsBottomRightCorner.map { it.dcp }.average().roundTo2DecimalPlaces()
        val requiredDCP = getConReqOfDryItem()
        val getDMChart = getDMChartAccordingToWeight()
        val diffDM = (getDMChart.dm - calcRough()).roundTo2DecimalPlaces()

        val upperCalcDCP = (avgDCPBottomRight - requiredDCP).roundTo2DecimalPlaces()
        val bottomCalcDCP = (avgDCPUpperRight - requiredDCP).roundTo2DecimalPlaces()

        val totalDCP = (upperCalcDCP + bottomCalcDCP).roundTo2DecimalPlaces()

        val totalConcentrait = ((upperCalcDCP * diffDM) / totalDCP).roundTo2DecimalPlaces()
        val equalyDistribute = (diffDM - totalConcentrait).roundTo2DecimalPlaces()

        val distributedNonRoughes = (equalyDistribute / itemsBottomRightCorner.size).roundTo2DecimalPlaces()


        return "DCP Remaing Nutrient = $requiredDCP\n " +
                "avgDCPUpperRight=$avgDCPUpperRight \n " +
                "avgDCPBottomRight=$avgDCPBottomRight \n" +
                "Upper Item Calc = $upperCalcDCP \n " +
                "Lower Item Calc=$bottomCalcDCP\n"+
                "Total Calc = $totalDCP\n"+
                "Conc mix will have = $totalConcentrait \n"+
                "Equally divide = $equalyDistribute KG \n"+
                "Distributed Value = $distributedNonRoughes KG \n total Non-Rough Item = ${ itemsBottomRightCorner.size} \n\n"+
                "***** CHART VALUES ***** \n\n"+
                itemsBottomRightCorner.toList().map { item ->
                    val dcpValue =(distributedNonRoughes * item.dcp /100).roundTo2DecimalPlaces()
                    val dmValue =(distributedNonRoughes * item.dm /100).roundTo2DecimalPlaces()
                    val tdnValue =(distributedNonRoughes * item.tdn /100).roundTo2DecimalPlaces()
                    "${item.name} = Amount: $distributedNonRoughes DCP: $dcpValue  DM: $dmValue    TDN: $tdnValue \n"
                }+
                itemsUpperRightCorner.toList().map { item ->
                    val dcpValue =(totalConcentrait * item.dcp /100).roundTo2DecimalPlaces()
                    val dmValue =(totalConcentrait * item.dm /100).roundTo2DecimalPlaces()
                    val tdnValue =(totalConcentrait * item.tdn /100).roundTo2DecimalPlaces()
                    "${item.name} =Amount: $totalConcentrait DCP: $dcpValue  DM: $dmValue    TDN: $tdnValue \n"
                }+
                greenItems.toList().map { item ->
                    val fooderValue = getItemFooderCalculation(greenItems, item)
                    val dcpValue = getItemDCPCalculation(greenItems, item)
                    val dmValue = (getDMValueBySelectedItem(item) / getSameCatSelectedItem(greenItems, item)).roundTo2DecimalPlaces()
                    val tdnValue = getItemTDNCalculation(DataManagerUtils.selectedFeetItem, item)

                    "${item.name} = Amount: $fooderValue DCP: $dcpValue  DM: $dmValue    TDN: $tdnValue \n"
                }


    }



}


infix fun Double.percentOf(value: Double): Double {
    return (this * (value / 100)).roundTo2DecimalPlaces()
}

fun Double.roundTo2DecimalPlaces(): Double {
    return "%.2f".format(this).toDouble()
}

fun Double.partOf(numerator: Int, denominator: Int): Double {
    return (this * numerator / denominator).roundTo2DecimalPlaces()
}


