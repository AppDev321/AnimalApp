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
import com.example.myapplication.adapter.FeedChartAdapter
import com.example.myapplication.adapter.IngredientAdapter
import com.example.myapplication.adapter.QutationAdapter
import com.example.myapplication.model.IngredientData
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.DataManagerUtils
import com.example.myapplication.utils.ProgressDialog
import com.example.myapplication.viewmodel.AppViewModel
import com.example.myapplication.viewmodel.Result
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FeedChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_feed_chart)
        val listChart = findViewById<RecyclerView>(R.id.rec_feed_chart)
        listChart.layoutManager = LinearLayoutManager(this)
        val feedChartAdapter = FeedChartAdapter(DataManagerUtils.finalFeedChartList)
        listChart.adapter = feedChartAdapter
    }




}