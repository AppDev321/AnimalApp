package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.App
import com.example.myapplication.AppLocale
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFeedBinding
import com.example.myapplication.databinding.ActivityLifeStageBinding
import com.example.myapplication.model.AnimalData
import com.example.myapplication.model.LifeStageAnimalData
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.ProgressDialog
import com.example.myapplication.utils.hide
import com.example.myapplication.utils.show
import com.example.myapplication.viewmodel.AppViewModel
import com.example.myapplication.viewmodel.Result
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class LifeStageActivity : AppCompatActivity() {
    var listLifeStageAnimal: List<LifeStageAnimalData> = arrayListOf()
    private lateinit var binding: ActivityLifeStageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLifeStageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listLifeStageAnimal = intent.extras?.
        getParcelableArrayList<LifeStageAnimalData>("lifeStageData") as List<LifeStageAnimalData>

        showLifeStageListing()
        binding.textInputPreLifeStage.hide()
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
           if( listLifeStageAnimal[position].animalName.toString().lowercase().contains("pregnant"))
               binding.textInputPreLifeStage.show()
            else
               binding.textInputPreLifeStage.hide()
        }
    }
}