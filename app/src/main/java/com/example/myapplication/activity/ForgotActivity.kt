package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFeedBinding
import com.example.myapplication.databinding.ActivityForgotBinding
import com.example.myapplication.databinding.ActivitySignupBinding
import com.example.myapplication.model.GeneralResponse
import com.example.myapplication.retrofit.ApiClient
import com.example.myapplication.retrofit.ApiInterface
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.ProgressDialog
import retrofit2.Call
import retrofit2.Callback

class ForgotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotBinding

    val emptySpaceError = "*** Detail Required"
    val resetError = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val btnSignUp = binding.btnSignUp

        btnSignUp.setOnClickListener {
            val email = binding.editTextEmail
            val firstName =  binding.editTextFirstName
            binding.txtNumber.text =""

            binding.textInputEmail.error =resetError
            binding.textInputName.error =resetError


                if (email.text.toString().isEmpty())
            {
                binding.textInputEmail.error = emptySpaceError
            }
             else if(firstName.text.toString().isEmpty())
            {
                binding.textInputName.error = emptySpaceError
            }

            else
            {
                setUserSignUp(
                    email.text.toString(),
                    firstName.text.toString()

                )
            }

        }

        binding.btnCancel.setOnClickListener{
            finish()
        }
    }


    private fun setUserSignUp(
        email: String,
        firstName: String,

    ) {
        var dialog = ProgressDialog.progressDialog(this)
        val apiInterface = ApiClient.client.create(ApiInterface::class.java)

        dialog.show()
        val call = apiInterface.forgotPhone(
            "Forgot",
            email,
            firstName
        )
        call.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: retrofit2.Response<GeneralResponse>
            ) {
                dialog.dismiss()

                var res = response.body() as GeneralResponse
                if (res.status == true) {
                   /* AppUtils.showSnackMessage(res.message.toString(), findViewById(R.id.btnSignUp))
                    Handler().postDelayed({

                        finish()
                    }, 1000)*/

                    binding.txtNumber.text = res.message
                } else {

                   // AppUtils.showSnackMessage(res.message.toString(), findViewById(R.id.btnSignUp))
                    binding.textInputEmail.error = resources.getString(R.string.forgotErrorMsg)
                }

            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                dialog.dismiss()
                AppUtils.writeLogs("Failed Query :(  ${t.toString()}")
                AppUtils.showSnackMessage(t.toString(), findViewById(R.id.btnSignUp))

            }

        })


    }
}