package com.example.myapplication.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.App
import com.example.myapplication.AppLocale
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLifeStageBinding
import com.example.myapplication.model.LifeStageAnimalData
import com.example.myapplication.utils.InputFilterMinMax
import com.example.myapplication.utils.hide
import com.example.myapplication.utils.show


class LifeStageActivity : AppCompatActivity() {
   private var listLifeStageAnimal: List<LifeStageAnimalData> = arrayListOf()
   private var pregnancyStage: List<LifeStageAnimalData> = arrayListOf()

    private lateinit var binding: ActivityLifeStageBinding

    companion object {
        val lifeStageData = "lifeStageData"
        val pregnancyStageData = "pregnancyStageData"
        val weight = "weight"
        val animalName = "animalName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLifeStageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listLifeStageAnimal =
            intent.extras?.getParcelableArrayList<LifeStageAnimalData>(lifeStageData) as List<LifeStageAnimalData>

        pregnancyStage =
            intent.extras?.getParcelableArrayList<LifeStageAnimalData>(pregnancyStageData) as List<LifeStageAnimalData>

        val weight = intent.extras?.getString(weight)
        val animalName = intent.extras?.getString(animalName)
        binding.txtAnimalName.text = animalName
        binding.txtWeigh.text = weight


        binding.textInputPreLifeStage.hide()
        showLifeStageListing()
        showPregnancyListing()
        binding.editTextMilk.filters = arrayOf(InputFilterMinMax(1, 30))
        binding.editTextFAT.filters = arrayOf(InputFilterMinMax(1, 100))

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
            if (listLifeStageAnimal[position].animalName.toString().lowercase()
                    .contains("pregnant")
            )
                binding.textInputPreLifeStage.show()
            else
                binding.textInputPreLifeStage.hide()
        }
    }
}