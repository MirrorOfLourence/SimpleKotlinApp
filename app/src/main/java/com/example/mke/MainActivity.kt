package com.example.mke

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnByNumber = findViewById<Button>(R.id.btnNumber)
        val btnByEmail = findViewById<Button>(R.id.btnEmail)
        val editNumberOrEmail = findViewById<EditText>(R.id.etEditNumberOrEmail)
        val etPassword = findViewById<EditText>(R.id.etPasswordRegister)
        val etPasswordConfirm = findViewById<EditText>(R.id.etPasswordConfirmRegister)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        btnByNumber.setOnClickListener {
            btnByNumber.setTextColor(getResources().getColor(R.color.purple))
            btnByEmail.setTextColor(getResources().getColor(R.color.black))
            editNumberOrEmail.hint = getResources().getString(R.string.enterNumber)
            editNumberOrEmail.inputType = android.text.InputType.TYPE_CLASS_PHONE
        }

        btnByEmail.setOnClickListener {
            btnByEmail.setTextColor(getResources().getColor(R.color.purple))
            btnByNumber.setTextColor(getResources().getColor(R.color.black))
            editNumberOrEmail.hint = getResources().getString(R.string.enterEmail)
            editNumberOrEmail.inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }

        // Default to phone number mode
        btnByNumber.callOnClick()

        btnRegister.setOnClickListener {
            val input = editNumberOrEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etPasswordConfirm.text.toString()

            when {
                !isValidEmail(input) && !isValidPhone(input) -> {
                    if (editNumberOrEmail.inputType == android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) {
                        Toast.makeText(
                            this,
                            getResources().getString(R.string.invalidEmail),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else {
                        Toast.makeText(
                            this,
                            getResources().getString(R.string.invalidNumber),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                password.length < 8 -> {
                    Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                }
                password != confirmPassword -> {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Valid input
                    // Proceed with next steps (currently nothing happens)
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
        )
        return emailPattern.matcher(email).matches()
    }

    private fun isValidPhone(phone: String): Boolean {
        return phone.startsWith("+") && phone.length >= 10
    }
}