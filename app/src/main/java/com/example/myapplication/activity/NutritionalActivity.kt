package com.example.myapplication.activity

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.IngredientAdapter
import com.example.myapplication.adapter.QutationAdapter
import com.example.myapplication.model.IngredientData
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.ProgressDialog
import com.example.myapplication.viewmodel.AppViewModel
import com.example.myapplication.viewmodel.Result
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class NutritionalActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    private var ingredientList: List<IngredientData> = arrayListOf()

    lateinit var dropDownView: TextInputLayout

    private var qutationList: MutableList<IngredientData> = arrayListOf()

    private var selectedIngredientIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AppViewModel::class.java]


        setContentView(R.layout.activity_nutrition)
        setTitle("Add Nutritions")

        dropDownView = findViewById(R.id.TextInputLayout)


        val weight = intent.getStringExtra("weight") ?: "0 Kg"
        val txtWeight = findViewById<TextView>(R.id.txtWeight)
        txtWeight.text = weight

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

                            val list = ArrayList<IngredientData>()
                            for (item in response.data) {
                                val gson = Gson()
                                val json = gson.toJson(item)
                                val ingredientData = gson.fromJson(json, IngredientData::class.java)
                                list.add(ingredientData)
                            }

                            ingredientList = list
                            dropDownView.visibility = View.VISIBLE
                            showIngredientData()

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
                        AppUtils.showSnackMessage(error.toString(), findViewById(R.id.rootView))
                    }
                    else -> {}
                }
            }
        }


        viewModel.fetchIngredientList()


    }


    private fun showIngredientData() {


        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.AutoCompleteTextview)
        val txtQty = findViewById<TextInputEditText>(R.id.edQuantity)

        var animalNames: ArrayList<String> = arrayListOf()
        for (data in ingredientList) {
            animalNames.add(data.name.toString())
        }

        val adapter = ArrayAdapter(this, R.layout.dropdown_item, animalNames)
        autoCompleteTextView.setAdapter(adapter)


        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            selectedIngredientIndex = position
            val item = ingredientList[position]
            findViewById<TextInputEditText>(R.id.edCP).setText(item.cp.toString())
            txtQty.text?.clear()

        }

        val btnView = findViewById<Button>(R.id.btnView)
        btnView.setOnClickListener {
            showIngredientDialog(ingredientList)
        }



        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val listQutation = findViewById<RecyclerView>(R.id.rec_qutation)
        listQutation.layoutManager = LinearLayoutManager(this)
        val qutationAdapter = QutationAdapter(qutationList)
        listQutation.adapter = qutationAdapter

        btnAdd.setOnClickListener {
            if (selectedIngredientIndex > 0) {
                val item = ingredientList[selectedIngredientIndex]

                val qty = txtQty.text.toString().toIntOrNull() ?:1

                item.qty = qty

                qutationList.add(item)
                qutationAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun showIngredientDialog(listIngredientData: List<IngredientData>) {

        val dialog = Dialog(this@NutritionalActivity)
        dialog.setContentView(R.layout.dilaog_ingrediant_list)
        val list = dialog.findViewById<RecyclerView>(R.id.rec_ingrediant)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = IngredientAdapter(listIngredientData)
        dialog.show()
    }

}