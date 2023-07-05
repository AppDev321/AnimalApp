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
import com.example.myapplication.databinding.ActivitySignupBinding
import com.example.myapplication.model.GeneralResponse
import com.example.myapplication.retrofit.ApiClient
import com.example.myapplication.retrofit.ApiInterface
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.ProgressDialog
import retrofit2.Call
import retrofit2.Callback

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    val emptySpaceError = "*** Detail Required"
    val resetError = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val btnSignUp = binding.btnSignUp

        btnSignUp.setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextEmail)

            val firstName = findViewById<EditText>(R.id.editTextFirstName)
            val phone = findViewById<EditText>(R.id.editTextPhone)
            val village = findViewById<EditText>(R.id.editTextVillage)
            val district = findViewById<EditText>(R.id.editTextDistrict)
            val tehsil = findViewById<EditText>(R.id.editTextTehsil)
            val state = findViewById<EditText>(R.id.editTextState)
            val pin = findViewById<EditText>(R.id.editTextPin)
            val land = findViewById<EditText>(R.id.editTextLand)
            val hybrid = findViewById<EditText>(R.id.editTextHybrid)
            val native = findViewById<EditText>(R.id.editTextNative)
            val buffalo = findViewById<EditText>(R.id.editTextBuffalow)
            val ox = findViewById<EditText>(R.id.editTextOx)
            val goat = findViewById<EditText>(R.id.editTextGoat)
            val sheep = findViewById<EditText>(R.id.editTextSheep)

            binding.textInputEmail.error =resetError
            binding.textInputName.error =resetError
            binding.textInputVillage.error = resetError
            binding.textInputMobile.error = resetError
            binding.textInputDistrict.error = resetError
            binding.textInputTehsil.error = resetError
            binding.textInputPin.error = resetError
            binding.textInputLand.error = resetError
            binding.textInputState.error = resetError


                if (email.text.toString().isEmpty())
            {
                binding.textInputEmail.error = emptySpaceError
            }
             else if(firstName.text.toString().isEmpty())
            {
                binding.textInputName.error = emptySpaceError
            }
            else if(village.text.toString().isEmpty())
            {
                binding.textInputVillage.error = emptySpaceError
            }
            else if(phone.text.toString().isEmpty())
            {
                binding.textInputMobile.error = emptySpaceError
            }
            else if(district.text.toString().isEmpty())
            {
                binding.textInputDistrict.error = emptySpaceError
            }
            else if(tehsil.text.toString().isEmpty())
            {
                binding.textInputTehsil.error = emptySpaceError
            }
            else if(pin.text.toString().isEmpty())
            {
                binding.textInputPin.error = emptySpaceError
            }
            else if(land.text.toString().isEmpty())
            {
                binding.textInputLand.error = emptySpaceError
            }
            else if(state.text.toString().isEmpty())
            {
                binding.textInputState.error = emptySpaceError
            }
            else
            {
                setUserSignUp(
                    email.text.toString(),
                    firstName.text.toString(),
                    phone.text.toString(),
                    village.text.toString(),
                    district.text.toString(),
                    state.text.toString(),
                    tehsil.text.toString(),
                    pin.text.toString(),
                    land.text.toString(),
                    hybrid.text.toString(),
                    native.text.toString(),
                    buffalo.text.toString(),
                    goat.text.toString(),
                    ox.text.toString(),
                    sheep.text.toString()

                )
            }

        }
    }


    private fun setUserSignUp(
        email: String,

        firstName: String,
        phone: String,
        village: String,
        district: String,
        state: String,
        tehsil: String,
        pin: String,
        land: String,
        hybrid: String,
        native: String,
        buffalow: String,
        goat: String,
        ox: String,
        sheep: String
    ) {
        var dialog = ProgressDialog.progressDialog(this)
        val apiInterface = ApiClient.client.create(ApiInterface::class.java)

        dialog.show()
        val call = apiInterface.signUpUser(
            "SignUp",
            email,

            firstName,
            phone,
            village,
            district,
            state,
            tehsil,
            pin,
            land,
            hybrid,
            native,
            buffalow,
            goat,
            ox,
            sheep
        )
        call.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: retrofit2.Response<GeneralResponse>
            ) {
                dialog.dismiss()

                var res = response.body() as GeneralResponse
                if (res.status == true) {
                    AppUtils.showSnackMessage(res.message.toString(), findViewById(R.id.btnSignUp))
                    Handler().postDelayed({

                        finish()
                    }, 1000)
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