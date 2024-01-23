package com.example.myapplication.activity

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityQuantityCalculateBinding
import com.example.myapplication.model.DMChartData
import com.example.myapplication.model.FeedItem
import com.example.myapplication.utils.DataManagerUtils
import com.example.myapplication.utils.roundTo2DecimalPlaces
import retrofit2.http.Body

class QuantityCalculateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuantityCalculateBinding
    lateinit var  allItems : MutableList<FeedItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuantityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allItems = DataManagerUtils.selectedFeetItem
        //first set only
       // DataManagerUtils.allSelectedItems =

        //now transform to skip 1,2 categrory items
        DataManagerUtils.selectedFeetItem = DataManagerUtils.selectedFeetItem.filterNot {
            it.catID == 1 || it.catID == 2
        }.toMutableList()



        binding.txtCalculation.text =
            "Body Weight = ${DataManagerUtils.lifeStageActivityData.animalWeight} KG\n" +
            "DRY Matter = ${DataManagerUtils.lifeStageActivityData.dryMatter} KG\n" +
            "ConstraintRequire = ${DataManagerUtils.lifeStageActivityData.constraintRequire} KG\n" +
             "Roughages = ${DataManagerUtils.lifeStageActivityData.rough} KG\n" +
                "Dry Roughages = ${DataManagerUtils.lifeStageActivityData.dryRough} KG\n" +
        "Green Roughages = ${DataManagerUtils.lifeStageActivityData.greenRough} KG\n" +
        "NON Legume GR = ${DataManagerUtils.lifeStageActivityData.nonLegumeGreenRough} KG\n" +
        "Legume GR = ${DataManagerUtils.lifeStageActivityData.legumeGreenRough} KG\n\n\n" +

        "********* Item DM **********\n"+

                    getItems().map {
                        it + "\n"
                    } +"\n\n\n"+

        "********* Amount of Fooder **********\n"+

                getCalFooderItems().map {
                    it + "\n"
                } +"\n\n" +

                    "********* DCP of ITEM **********\n"+
                    getCalDCPItems().map {
                        it + "\n"
                    } +"\n Total DCP =${DataManagerUtils.lifeStageActivityData.getCurrentTotalDCP()} KG\n\n" +

        "********* TDN of ITEM **********\n"+
                getCalTDNItems().map {
                    it + "\n"
                } +"\n Total TDN =${DataManagerUtils.lifeStageActivityData.getCurrentTotalTDN()} KG\n\n" +
                    "********* DM Chart **********\n"+
                    "Body WEgit = "+getDMChartofWeight().weight.roundTo2DecimalPlaces()+"\n"+
                    DataManagerUtils.lifeStageActivityData.getRemainingDCPChart() +"\n"+
                    "Con DCP = ${ DataManagerUtils.lifeStageActivityData.getConReqOfDryItem()}% DCP \n\n"+
                    "***** Pearcing formula Values ***********\n\n"+
                    getPearcingMethodFormala() +"\n\n"


    }

    fun getItems(): List<String> {
        val lifestageCalc = DataManagerUtils.lifeStageActivityData
        return DataManagerUtils.selectedFeetItem.toList().map { item ->
            "${item.name} = ${
                (lifestageCalc.getDMValueBySelectedItem(item) /
                        lifestageCalc.getSameCatSelectedItem(DataManagerUtils.selectedFeetItem, item)).roundTo2DecimalPlaces()
            } KG"
        }
    }
    fun getCalFooderItems(): List<String> {
        val lifestageCalc = DataManagerUtils.lifeStageActivityData
        return DataManagerUtils.selectedFeetItem.toList().map { item ->
            "${item.name} = ${
                lifestageCalc.getItemFooderCalculation(DataManagerUtils.selectedFeetItem, item)
            } KG"
        }
    }

    fun getCalDCPItems(): List<String> {
        val lifestageCalc = DataManagerUtils.lifeStageActivityData
        return DataManagerUtils.selectedFeetItem.toList().map { item ->

            "${item.name} = ${
                lifestageCalc.getItemDCPCalculation(DataManagerUtils.selectedFeetItem, item)
            } KG"
        }
    }

    fun getCalTDNItems(): List<String> {
        val lifestageCalc = DataManagerUtils.lifeStageActivityData
        return DataManagerUtils.selectedFeetItem.toList().map { item ->
            "${item.name} = ${
                lifestageCalc.getItemTDNCalculation(DataManagerUtils.selectedFeetItem, item)
            } KG"
        }
    }




    fun getDMChartofWeight(): DMChartData
    {
        val lifestageCalc = DataManagerUtils.lifeStageActivityData
         return lifestageCalc.getDMChartAccordingToWeight()
    }

    fun getPearcingMethodFormala():String
    {
        val nonGreenItems = allItems.filter {
            (it.catID == 1 || it.catID ==2)
        }.toMutableList()
        return DataManagerUtils.lifeStageActivityData.pearsonSquareFormula(nonGreenItems)
    }

}