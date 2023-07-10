package com.example.myapplication

import android.app.Application
import com.example.myapplication.rosetta.LanguageSwitcher
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import java.util.*


class App:Application() {
companion object{
    private lateinit var firstLaunchLocale: Locale
    private lateinit var supportedLocales: HashSet<Locale>
    lateinit var languageSwitcher: LanguageSwitcher
}

    override fun onCreate() {
        super.onCreate()
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = LruCache(cacheSize)

        val picasso = Picasso.Builder(this)
            .memoryCache(cache)
            .build()

        Picasso.setSingletonInstance(picasso)

        init()
    }
    private fun init(){
        AutomatedSupportedLocales()
        manualSupportedLocales()
        languageSwitcher =  LanguageSwitcher(this, firstLaunchLocale)
        languageSwitcher.setSupportedLocales(supportedLocales)
    }

    private fun AutomatedSupportedLocales() {

    }

    private fun manualSupportedLocales() {
        firstLaunchLocale = Locale.ENGLISH
        supportedLocales =  HashSet<Locale>()
        supportedLocales.add(firstLaunchLocale)
        supportedLocales.add(Locale("hi"))
        supportedLocales.add(Locale("mr"))
    }


     fun getCurrentAppLocale():AppLocale
    {
       return if(App.languageSwitcher.currentLocale.language.equals("hi"))
        AppLocale.HINDI
        else if(App.languageSwitcher.currentLocale.language.equals("mr"))
        AppLocale.MARATHI
        else
        AppLocale.ENG
    }
}
enum class AppLocale{
    ENG,MARATHI,HINDI
}