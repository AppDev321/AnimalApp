package com.example.myapplication.utils

import android.util.Log
import android.view.View
import com.example.myapplication.BuildConfig

import com.google.android.material.snackbar.Snackbar

object AppUtils {

    fun showSnackMessage(msg: String, view: View) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }

    fun writeLogs(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e("Animal App", msg)
        }
    }
}