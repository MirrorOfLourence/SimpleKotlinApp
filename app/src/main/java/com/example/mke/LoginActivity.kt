package com.example.mke

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mke.SharedPrefsUtils.Credentials

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etNumberOrEmailLogin = findViewById<EditText>(R.id.etNumberOrEmailLogin)
        val etPassword = findViewById<EditText>(R.id.etPasswordLogin)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val cbAutoLogin = findViewById<CheckBox>(R.id.cbAutoLogin)

        val sharedPrefsUtils = SharedPrefsUtils(this)
        val creds = sharedPrefsUtils.readCredentialsFromSharedPrefs()
        cbAutoLogin.isChecked = creds.enterAutomatically

        if (creds.number != null){
            etNumberOrEmailLogin.hint = getString(R.string.enterNumber)
            etNumberOrEmailLogin.inputType = android.text.InputType.TYPE_CLASS_PHONE
        }
        else {
            etNumberOrEmailLogin.hint = getString(R.string.enterEmail)
            etNumberOrEmailLogin.inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }

        btnLogin.setOnClickListener {

            val inputLogin = etNumberOrEmailLogin.text.toString()
            val inputPassword = etPassword.text.toString()

            val savedLogin = getSavedLogin(creds)
            val savedPassword = creds.password

            if (inputLogin == savedLogin && inputPassword == savedPassword) {
                sharedPrefsUtils.writeParamToSharedPrefs("enterAutomatically", cbAutoLogin.isChecked)
                startActivity(Intent(this, ContentActivity::class.java))
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun getSavedLogin(creds : Credentials) : String
    {
        if (creds.number != null){
            return creds.number
        }
        else  if (creds.email != null) {
            return creds.email
        }
        return ""
    }
}