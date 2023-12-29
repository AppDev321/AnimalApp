package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.rosetta.LanguageSwitcher
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.ProgressDialog
import com.example.myapplication.utils.getHtmlSpannedText
import com.example.myapplication.viewmodel.AppViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.myapplication.viewmodel.Result
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this)[AppViewModel::class.java]

        val inputLayout = findViewById<TextInputLayout>(R.id.textInputPhone)

        val forgotBtn =  findViewById<TextView>(R.id.txtForgot)
        forgotBtn.setOnClickListener{
            startActivity(Intent(this, ForgotActivity::class.java))
        }

        val buttonSignUp = findViewById<TextView>(R.id.txtSignUP)
        buttonSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        val buttonDisclaimer = findViewById<TextView>(R.id.txtDisclaimer)
        buttonDisclaimer.setOnClickListener {
            showDisclaimerDialog()
        }

        val loginButton = findViewById<Button>(R.id.btnLogin)
        loginButton.setOnClickListener {

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            val phone = findViewById<EditText>(R.id.editTextPhone)

          /*  if (BuildConfig.DEBUG) {
                  viewModel.loginUser("03125149837")
            } else {*/
                if (phone.text.toString().isEmpty() ) {
                    inputLayout.error = "Please enter Phone Number"
                } else {
                    viewModel.loginUser(phone.text.toString())
                    inputLayout.error=""
                }
    // }
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
                           /* AppUtils.showSnackMessage(
                                response.message.toString(),
                                findViewById(R.id.rootView)
                            )*/

                            inputLayout.error = resources.getString(R.string.loginError)//response.message.toString()
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
        val menuItem  = toolbar.findViewById<LinearLayout>(R.id.menu_item_id)
        menuItem.setOnClickListener{

            App.languageSwitcher.showChangeLanguageDialog(this) { locale ->
                App.languageSwitcher = LanguageSwitcher(this,locale)
            }
        }
       // setSupportActionBar(toolbar)


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


    private fun showDisclaimerDialog() {
        val mDialog = MaterialAlertDialogBuilder(this)
        mDialog.setPositiveButton("Close") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }.setMessage(getString(R.string.disclaimer_data).getHtmlSpannedText())
            .setTitle(getString(R.string.txt_discalimer)).create()
        mDialog.show()
    }

}