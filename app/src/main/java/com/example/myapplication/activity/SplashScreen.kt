package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
        finish()
       startActivity(Intent(this,LoginActivity::class.java))

        }, 3000)

    }
}
