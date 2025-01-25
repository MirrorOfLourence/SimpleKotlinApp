package com.example.mke

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.isIndeterminate = true

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val sharedPrefsUtils = SharedPrefsUtils(this)
        val creds = sharedPrefsUtils.readCredentialsFromSharedPrefs()
        if ((!creds.number.isNullOrEmpty() || !creds.email.isNullOrEmpty()) &&
             !creds.password.isNullOrEmpty())
        {
            if (creds.enterAutomatically){
                startActivity(Intent(this, ContentActivity::class.java))
            }
            else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        else {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }
}