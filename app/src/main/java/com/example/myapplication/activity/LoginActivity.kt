package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.App
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.model.GeneralResponse
import com.example.myapplication.retrofit.ApiClient
import com.example.myapplication.retrofit.ApiInterface
import com.example.myapplication.rosetta.LanguageSwitcher
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.ProgressDialog
import com.example.myapplication.viewmodel.AppViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import com.example.myapplication.viewmodel.Result
import java.util.*


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this)[AppViewModel::class.java]


        val buttonSignUp = findViewById<TextView>(R.id.txtSignUP)
        buttonSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        val loginButton = findViewById<Button>(R.id.btnLogin)
        loginButton.setOnClickListener {

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            val email = findViewById<EditText>(R.id.editTextEmail)
            val password = findViewById<EditText>(R.id.editTextPassword)
            if (BuildConfig.DEBUG) {
                viewModel.loginUser("numiraaj@gmail.com", "123456")
            } else {
                if (email.text.toString().isEmpty() ||  password.text.toString().isEmpty() ) {
                    AppUtils.showSnackMessage(
                        "Please enter Email/Password",
                        findViewById(R.id.btnLogin)
                    )
                } else {
                    viewModel.loginUser(email.text.toString(), password.text.toString())

                }
     }
        }


        val dialog = ProgressDialog.progressDialog(this)
        lifecycleScope.launch {
            viewModel.apiResult.collectLatest { result ->
                // AppUtils.showSnackMessage(it, findViewById(R.id.rootView))
                when (result) {
                    is Result.Loading -> {
                        dialog.show()
                    }
                    is Result.Success -> {
                        dialog.dismiss()
                        val response = result.data
                        if (response.status == true) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        } else {
                            AppUtils.showSnackMessage(
                                response.message.toString(),
                                findViewById(R.id.rootView)
                            )
                        }
                    }
                    is Result.Error -> {
                        dialog.dismiss()
                        val error = result.exception
                        AppUtils.showSnackMessage(error.toString(), findViewById(R.id.rootView))
                    }
                    else -> {}
                }
            }
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)


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

                val res = response.body() as GeneralResponse
                if (res.status == true) {
                    finish()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))

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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {

            App.languageSwitcher.showChangeLanguageDialog(this) { locale ->
                App.languageSwitcher = LanguageSwitcher(this,locale)
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}