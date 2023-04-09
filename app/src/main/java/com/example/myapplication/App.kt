package com.example.myapplication

import android.app.Application
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = LruCache(cacheSize)

        val picasso = Picasso.Builder(this)
            .memoryCache(cache)
            .build()

        Picasso.setSingletonInstance(picasso)
    }
}