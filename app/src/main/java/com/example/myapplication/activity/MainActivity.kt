package com.example.myapplication.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.model.AnimalData
import com.example.myapplication.model.GeneralResponse
import com.example.myapplication.retrofit.ApiClient
import com.example.myapplication.retrofit.ApiInterface
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.ProgressDialog
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback


class MainActivity : AppCompatActivity() {

        var listAnimals :List<AnimalData> = arrayListOf()
    lateinit var detailContainer :LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        detailContainer = findViewById<LinearLayout>(R.id.continer_detail)
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = LruCache(cacheSize)

        val picasso = Picasso.Builder(this)
            .memoryCache(cache)
            .build()

        Picasso.setSingletonInstance(picasso)


        getAnimalList()


    }


    private fun loadAnimalDetails(item :AnimalData)
    {
        detailContainer.visibility = View.VISIBLE
        val imageView = findViewById<ImageView>(R.id.img)

        Picasso.get()
            .load(item.image.toString())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.no_image)
            .fit()
            .centerCrop()
            .into(imageView)

        val btnVideoLink = findViewById<Button>(R.id.videoBtn)
        btnVideoLink.setOnClickListener{
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

    private fun showAnimalListing()
    {
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.AutoCompleteTextview)
        var animalNames :ArrayList<String> = arrayListOf()
            for(  data in listAnimals)

            {
                animalNames.add(data.animalName.toString())

            }

        val adapter = ArrayAdapter(this, R.layout.dropdown_item, animalNames)
        autoCompleteTextView.setAdapter(adapter)


        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->

           val item = listAnimals[position]
            loadAnimalDetails(item)


        }
    }



    private fun getAnimalList() {
        var dialog = ProgressDialog.progressDialog(this)
        val apiInterface = ApiClient.client.create(ApiInterface::class.java)

        dialog.show()
        val call = apiInterface.getAnimalList("animal-get")
        call.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: retrofit2.Response<GeneralResponse>
            ) {
                dialog.dismiss()

                var res = response.body() as GeneralResponse
                if (res.status == true) {

                    listAnimals = res.data
                    showAnimalListing()

                } else {

                    AppUtils.showSnackMessage(res.message.toString(), findViewById(R.id.AutoCompleteTextview))
                }

            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                dialog.dismiss()
                AppUtils.writeLogs("Failed Query :(  ${t.toString()}")
                AppUtils.showSnackMessage(t.toString(), findViewById(R.id.rootView))

            }

        })


    }

}