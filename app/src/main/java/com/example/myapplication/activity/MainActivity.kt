package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.App
import com.example.myapplication.AppLocale
import com.example.myapplication.R
import com.example.myapplication.model.AnimalData
import com.example.myapplication.model.LifeStageAnimalData
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.DataManagerUtils
import com.example.myapplication.utils.ProgressDialog
import com.example.myapplication.viewmodel.AppViewModel
import com.example.myapplication.viewmodel.Result
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    var listAnimals: List<AnimalData> = arrayListOf()
    var listLifeStageAnimal: List<LifeStageAnimalData> = arrayListOf()
    var pregnancyStageAnimal: List<LifeStageAnimalData> = arrayListOf()
    lateinit var detailContainer: LinearLayout

    lateinit var dropDownView: TextInputLayout
    lateinit var dropDownLifeView: TextInputLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[AppViewModel::class.java]

        setContentView(R.layout.activity_main)



        dropDownView = findViewById(R.id.TextInputLayout)
        detailContainer = findViewById(R.id.continer_detail)


        val dialog = ProgressDialog.progressDialog(this)
        lifecycleScope.launch {
            viewModel.apiResult.collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        dialog.show()
                    }

                    is Result.Success -> {
                        dialog.dismiss()
                        val response = result.data
                        if (response.status == true) {

                            val animalDataList = ArrayList<AnimalData>()
                            val lifeStageList = ArrayList<LifeStageAnimalData>()
                            val pregnancyStageList = ArrayList<LifeStageAnimalData>()
                            for (item in response.data[0] as ArrayList<Any>) {
                                val gson = Gson()
                                val json = gson.toJson(item)
                                val animalData = gson.fromJson(json, AnimalData::class.java)
                                animalDataList.add(animalData)
                            }

                            for (item in response.data[1] as ArrayList<Any>) {
                                val gson = Gson()
                                val json = gson.toJson(item)
                                val lifeDataAnimal =
                                    gson.fromJson(json, LifeStageAnimalData::class.java)
                                lifeStageList.add(lifeDataAnimal)
                            }


                            for (item in response.data[2] as ArrayList<Any>) {
                                val gson = Gson()
                                val json = gson.toJson(item)
                                val pregnancyStage =
                                    gson.fromJson(json, LifeStageAnimalData::class.java)
                                pregnancyStageList.add(pregnancyStage)
                            }

                            listAnimals = animalDataList
                            listLifeStageAnimal = lifeStageList
                            pregnancyStageAnimal = pregnancyStageList


                            dropDownView.visibility = View.VISIBLE

                            showAnimalListing()

                        } else {
                            AppUtils.showSnackMessage(
                                response.message.toString(),
                                findViewById(R.id.rootView)
                            )
                        }
                    }

                    is Result.Error -> {
                        dialog.dismiss()
                        val error = result.exception
                        AppUtils.writeLogs(error.toString())
                        AppUtils.showSnackMessage(error.toString(), findViewById(R.id.rootView))
                    }

                    else -> {}
                }
            }
        }

        viewModel.fetchAnimalList()
        viewModel.fetchFeedCategoryList {
            DataManagerUtils.feedDataResponse = it
        }


    }

    private fun loadAnimalDetails(item: AnimalData, animalName: String) {


        detailContainer.visibility = View.VISIBLE

        weightCalculateView(animalName)


        val imageView = findViewById<ImageView>(R.id.img)
        imageView.visibility = View.INVISIBLE
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE

        Picasso.get()
            .load(item.image.toString())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.no_image)
            .fit()
            .into(imageView, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    progressBar.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                }
            })

        val btnVideoLink = findViewById<Button>(R.id.videoBtn)
        btnVideoLink.setOnClickListener {
            if (item.video.toString().isEmpty()) {
                AppUtils.showSnackMessage("Video Not Available", detailContainer)
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.video.toString()))
            intent.setPackage("com.google.android.youtube")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {

                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.video.toString()))
                startActivity(webIntent)
            }
        }


    }


    private fun weightCalculateView(animalName: String) {

        val edGirth = findViewById<EditText>(R.id.edGirth)
        edGirth.text.clear()
        val edLength = findViewById<EditText>(R.id.edLength)
        edLength.text.clear()
        val txtWeight = findViewById<TextView>(R.id.txtWeight)
        txtWeight.text = "0 KG"
        var weight = 0.0
        val btnCalculateWeight = findViewById<Button>(R.id.btnCheck)
        btnCalculateWeight.setOnClickListener {

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            val girth = edGirth.text.toString().toDoubleOrNull()
            val length = edLength.text.toString().toDoubleOrNull()

            if (girth == null || length == null) {
                AppUtils.showSnackMessage("Please enter a valid value", detailContainer)
            } else {
                weight = viewModel.calculateAnimalWeight(girth, length)
                if (weight == null) {
                    AppUtils.showSnackMessage("Could not calculate weight", detailContainer)
                } else {
                    txtWeight.text = "${String.format("%.2f", weight)} KG"
                }
            }

        }


        val btnNutrition = findViewById<Button>(R.id.btnNext)
        btnNutrition.setOnClickListener {

            if (weight > 1) {
                val intent = Intent(this@MainActivity, LifeStageActivity::class.java)
                intent.putExtra(LifeStageActivity.animalName, animalName)
                intent.putExtra(LifeStageActivity.weight, weight)
                intent.putParcelableArrayListExtra(
                    LifeStageActivity.pregnancyStageData,
                    ArrayList(pregnancyStageAnimal)
                )
                intent.putParcelableArrayListExtra(
                    LifeStageActivity.lifeStageData,
                    ArrayList(listLifeStageAnimal)
                )
                startActivity(intent)
            } else {
                AppUtils.showSnackMessage("Check weight first", detailContainer)
            }
        }


        /*   val btnFeedNutrition = findViewById<Button>(R.id.btnFeedNutrition)
           btnFeedNutrition.setOnClickListener {
               val feedData = viewModel._feedDataResponse
               if (feedData.categoryList.isNotEmpty()) {
                   FeedActivity.feedResponseData = feedData
                   startActivity(Intent(this, FeedActivity::class.java))
               } else
                   AppUtils.showSnackMessage("No Feed Category found", btnFeedNutrition)
           }*/

    }

    private fun showAnimalListing() {
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.AutoCompleteTextview)
        val animalNames: ArrayList<String> = arrayListOf()
        for (data in listAnimals) {
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
        autoCompleteTextView.showDropDown()
        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val item = listAnimals[position]
            loadAnimalDetails(item, animalNames[position])
        }
    }


}