package com.example.myapplication.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object ApiClient {

    lateinit var retrofit: Retrofit

    val client: Retrofit
        get() {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build()


            retrofit = Retrofit.Builder()
              // .baseUrl("https://script.google.com/macros/s/AKfycbw15-cuKzYXjV7ikToJumYgLNHjuy2RScl9WPOG-N48sniWsN0j_dqEO_yhlf7aI7fw/")//test
               .baseUrl("https://script.google.com/macros/s/AKfycby_JeiSkwPqRGW5c3MSVYCVr7id1FtZ9zFKxVtBUsY5v5tlcaOHl0zh-oN0YNz6d9KK/")//Live
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit
        }

}