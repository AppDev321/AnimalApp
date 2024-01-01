package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.App
import com.example.myapplication.AppLocale
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLifeStageBinding
import com.example.myapplication.model.LifeStageAnimalData
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.DataManagerUtils
import com.example.myapplication.utils.InputFilterMinMax
import com.example.myapplication.utils.LifeStageActivityData
import com.example.myapplication.utils.getSafeString
import com.example.myapplication.utils.hide
import com.example.myapplication.utils.show


class LifeStageActivity : AppCompatActivity() {
    private var listLifeStageAnimal: List<LifeStageAnimalData> = arrayListOf()
    private var pregnancyStage: List<LifeStageAnimalData> = arrayListOf()

    private lateinit var binding: ActivityLifeStageBinding

    companion object {
        const val lifeStageData = "lifeStageData"
        const val pregnancyStageData = "pregnancyStageData"
        const val weight = "weight"
        const val animalName = "animalName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLifeStageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listLifeStageAnimal =
            intent.extras?.getParcelableArrayList<LifeStageAnimalData>(lifeStageData) as List<LifeStageAnimalData>

        pregnancyStage =
            intent.extras?.getParcelableArrayList<LifeStageAnimalData>(pregnancyStageData) as List<LifeStageAnimalData>

        val weight = intent.extras?.getDouble(weight) ?: 0.0
        val animalName = intent.extras?.getString(animalName)
        binding.txtAnimalName.text = animalName
        binding.txtWeigh.text = "$weight KG"


        binding.textInputPreLifeStage.hide()
        showLifeStageListing()
        showPregnancyListing()
        binding.editTextMilk.filters = arrayOf(InputFilterMinMax(1, 30))
        binding.editTextFAT.filters = arrayOf(InputFilterMinMax(1, 100))
        binding.editAvgGain.filters = arrayOf(InputFilterMinMax(1, 1000))
        binding.btnFeed.setOnClickListener{

            val avgWeight = Integer.parseInt(binding.editAvgGain.text.toString().getSafeString())
            val fat = Integer.parseInt(binding.editTextFAT.text.toString().getSafeString())
            val milkYield = Integer.parseInt(binding.editTextMilk.text.toString().getSafeString())


            val data = LifeStageActivityData(
                animalName  = animalName!!,
                animalWeight = weight!!,
                avgDailyWeightGain = avgWeight,
                fat = fat,
                milkYield =  milkYield,
                lifeStage = binding.AutoCompleteTextview.text.toString(),
                pregnancyDuration = binding.txtPreLifeStage.text.toString()
            )
            DataManagerUtils.lifeStageActivityData = data
            DataManagerUtils.selectedFeetItem.clear()
            startActivity(Intent(this,FeedActivity::class.java))
        }

    }

    private fun showPregnancyListing() {
        val autoCompleteTextView = binding.txtPreLifeStage
        val animalNames: ArrayList<String> = arrayListOf()
        for (data in pregnancyStage) {
            animalNames.add(
                when (App().getCurrentAppLocale()) {
                    AppLocale.ENG ->
                        data.animalName.toString()
                    AppLocale.HINDI ->
                        data.hindi.toString()
                    AppLocale.MARATHI ->
                        data.marathi.toString()
                }
            )
        }
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, animalNames)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->

        }
    }

    private fun showLifeStageListing() {
        val autoCompleteTextView = binding.AutoCompleteTextview
        val animalNames: ArrayList<String> = arrayListOf()
        for (data in listLifeStageAnimal) {
            animalNames.add(
                when (App().getCurrentAppLocale()) {
                    AppLocale.ENG ->
                        data.animalName.toString()
                    AppLocale.HINDI ->
                        data.hindi.toString()
                    AppLocale.MARATHI ->
                        data.marathi.toString()
                }
            )
        }
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, animalNames)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val animalName = listLifeStageAnimal[position].animalName.toString().lowercase()
            binding.textInputPreLifeStage.visibility = if (animalName.contains("pregnant")) View.VISIBLE else View.GONE
            if (animalName.contains("heifer")) {
                binding.textInputMilk.hide()
                binding.textInputFAT.hide()
            } else {
                binding.textInputMilk.show()
                binding.textInputFAT.show()
            }
        }
    }
}