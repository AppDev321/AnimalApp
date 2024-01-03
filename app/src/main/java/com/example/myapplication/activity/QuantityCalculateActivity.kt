package com.example.myapplication.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.example.myapplication.adapter.CategoryPagerAdapter
import com.example.myapplication.databinding.ActivityFeedBinding
import com.example.myapplication.databinding.ActivityQuantityCalculateBinding
import com.example.myapplication.fragment.FeedCalculateFragment
import com.example.myapplication.model.FeedDataResponse
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.DataManagerUtils
import com.example.myapplication.utils.hide
import com.example.myapplication.utils.invisible
import com.example.myapplication.utils.roundTo2DecimalPlaces
import com.example.myapplication.utils.show
import com.example.myapplication.viewmodel.AppViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class QuantityCalculateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuantityCalculateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuantityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
                    } +"\n Total DCP =${getTotalDCP()} KG\n\n" +

        "********* TDN of ITEM **********\n"+
                getCalTDNItems().map {
                    it + "\n"
                } +"\n Total TDN =${getTotalTDN()} KG"

    }

    fun getItems(): List<String> {
        val lifestageCalc = DataManagerUtils.lifeStageActivityData
        return DataManagerUtils.selectedFeetItem.toList().map { item ->
            "${item.name} = ${
                lifestageCalc.getDMValueBySelectedItem(item) /
                        lifestageCalc.getSameCatSelectedItem(DataManagerUtils.selectedFeetItem, item)
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

    fun getTotalDCP(): Double  = run {
        val lifestageCalc = DataManagerUtils.lifeStageActivityData
        var totalDCP = 0.0
        DataManagerUtils.selectedFeetItem.toList().map { item ->
            totalDCP += lifestageCalc.getItemDCPCalculation(DataManagerUtils.selectedFeetItem, item)
        }
        totalDCP.roundTo2DecimalPlaces()
    }
    fun getTotalTDN(): Double  = run {
        val lifestageCalc = DataManagerUtils.lifeStageActivityData
        var totalDCP = 0.0
        DataManagerUtils.selectedFeetItem.toList().map { item ->
            totalDCP += lifestageCalc.getItemTDNCalculation(DataManagerUtils.selectedFeetItem, item)
        }
        totalDCP.roundTo2DecimalPlaces()
    }

}