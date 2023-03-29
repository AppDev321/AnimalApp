package com.example.myapplication.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.EditText
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.R
import com.example.myapplication.activity.MainActivity
import com.example.myapplication.model.GeneralResponse
import com.example.myapplication.retrofit.ApiClient
import com.example.myapplication.retrofit.ApiInterface
import com.example.myapplication.ui.theme.SnackbarManager
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.ProgressDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback

class AppViewModel ( application: Application) : AndroidViewModel(application)
{

    fun showMessage(message: String) {
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            SnackbarManager.snackbarHostState.showSnackbar(message)
        }

    }




 fun getUserLogin(context: Context, email: String, password: String) {
    var dialog = ProgressDialog.progressDialog(context)
    val apiInterface = ApiClient.client.create(ApiInterface::class.java)

    dialog.show()
    val call = apiInterface.loginUser("Login", email, password)
    call.enqueue(object : Callback<GeneralResponse> {
        override fun onResponse(
            call: Call<GeneralResponse>,
            response: retrofit2.Response<GeneralResponse>
        ) {
            dialog.dismiss()

            var res = response.body() as GeneralResponse
            if (res.status == true) {

                context.startActivity(Intent(context, MainActivity::class.java))

            } else {
                showMessage(res.message.toString())

            }

        }

        override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
            dialog.dismiss()
            AppUtils.writeLogs("Failed Query :(  ${t.toString()}")
            showMessage(t.toString())

        }

    })


}
}