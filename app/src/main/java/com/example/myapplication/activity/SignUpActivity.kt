package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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



            if (email.text.toString().isEmpty() ||
                password.text.toString().isEmpty() ||
                firstName.text.toString().isEmpty() ||
                village.text.toString().isEmpty() ||
                phone.text.toString().isEmpty() ||
                district.text.toString().isEmpty() ||

                tehsil.text.toString().isEmpty() ||
                pin.text.toString().isEmpty() ||
                land.text.toString().isEmpty() ||
                state.text.toString().isEmpty()
               /* || hybrid.text.toString().isEmpty() ||
                native.text.toString().isEmpty() ||

                buffalo.text.toString().isEmpty() ||
                ox.text.toString().isEmpty() ||
                goat.text.toString().isEmpty() ||
                sheep.text.toString().isEmpty()*/


            ) {
                AppUtils.showSnackMessage("Please enter all detail", findViewById(R.id.btnSignUp))
            } else {
                setUserSignUp(
                    email.text.toString(),
                    password.text.toString(),
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
        password: String,
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
            password,
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