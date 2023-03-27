package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.model.GeneralResponse
import com.example.myapplication.retrofit.ApiClient
import com.example.myapplication.retrofit.ApiInterface
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.ProgressDialog
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonSignUp = findViewById<TextView>(R.id.txtSignUP)
        buttonSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        val loginButton = findViewById<Button>(R.id.btnLogin)
        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextEmail)
            val password = findViewById<EditText>(R.id.editTextPassword)

                if(BuildConfig.DEBUG) {
                    getUserLogin("numiraaj@gmail.com", "123456")
                }
            else
                { getUserLogin(email.text.toString(), password.text.toString())

                }
        }


    }


    private fun getUserLogin(email: String, password: String) {
        var dialog = ProgressDialog.progressDialog(this)
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
                    AppUtils.showSnackMessage(res.message.toString(), findViewById(R.id.rootView))
                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))

                } else {

                    AppUtils.showSnackMessage(res.message.toString(), findViewById(R.id.rootView))
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