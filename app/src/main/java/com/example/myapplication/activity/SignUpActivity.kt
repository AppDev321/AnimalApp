package com.example.myapplication.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.model.GeneralResponse
import com.example.myapplication.retrofit.ApiClient
import com.example.myapplication.retrofit.ApiInterface
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.ProgressDialog
import retrofit2.Call
import retrofit2.Callback

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextEmail)
            val password = findViewById<EditText>(R.id.editTextPassword)
            val firstName = findViewById<EditText>(R.id.editTextFirstName)
            val lastName = findViewById<EditText>(R.id.editTextLastName)
            val phone = findViewById<EditText>(R.id.editTextPhone)


            if( email.text.toString().isEmpty() ||
            password.text.toString().isEmpty()||
            firstName.text.toString().isEmpty()||
            lastName.text.toString().isEmpty() ||
            phone.text.toString().isEmpty())
            {
                AppUtils.showSnackMessage("Please enter detail", findViewById(R.id.btnSignUp))
            }
            else {
                setUserSignUp(
                    email.text.toString(),
                    password.text.toString(),
                    firstName.text.toString(),
                    lastName.text.toString(),
                    phone.text.toString()
                )
            }

        }
    }


    private fun setUserSignUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String
    ) {
        var dialog = ProgressDialog.progressDialog(this)
        val apiInterface = ApiClient.client.create(ApiInterface::class.java)

        dialog.show()
        val call = apiInterface.signUpUser("SignUp", email, password, firstName, lastName, phone)
        call.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: retrofit2.Response<GeneralResponse>
            ) {
                dialog.dismiss()

                var res = response.body() as GeneralResponse
                if (res.status == true) {
                    AppUtils.showSnackMessage(res.message.toString(), findViewById(R.id.btnSignUp))

                } else {

                    AppUtils.showSnackMessage(res.message.toString(), findViewById(R.id.btnSignUp))
                }

            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                dialog.dismiss()
                AppUtils.writeLogs("Failed Query :(  ${t.toString()}")
                AppUtils.showSnackMessage(t.toString(), findViewById(R.id.btnSignUp))

            }

        })


    }

    fun login(v: View) {
        finish()
    }
}