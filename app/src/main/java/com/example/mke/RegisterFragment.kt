package com.example.mke

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import java.util.regex.Pattern

class RegisterFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_register, container, false)

        val btnByNumber = root.findViewById<Button>(R.id.btnNumber)
        val btnByEmail = root.findViewById<Button>(R.id.btnEmail)
        val editNumberOrEmail = root.findViewById<EditText>(R.id.etEditNumberOrEmail)
        val etPassword = root.findViewById<EditText>(R.id.etPasswordRegister)
        val etPasswordConfirm = root.findViewById<EditText>(R.id.etPasswordConfirmRegister)
        val btnRegister = root.findViewById<Button>(R.id.btnRegister)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        btnByNumber.setOnClickListener {
            btnByNumber.setTextColor(resources.getColor(R.color.purple, null))
            btnByEmail.setTextColor(resources.getColor(R.color.black, null))
            editNumberOrEmail.hint = getString(R.string.enterNumber)
            editNumberOrEmail.inputType = android.text.InputType.TYPE_CLASS_PHONE
        }

        btnByEmail.setOnClickListener {
            btnByEmail.setTextColor(resources.getColor(R.color.purple, null))
            btnByNumber.setTextColor(resources.getColor(R.color.black, null))
            editNumberOrEmail.hint = getString(R.string.enterEmail)
            editNumberOrEmail.inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }

        btnByNumber.callOnClick()

        btnRegister.setOnClickListener {
            val input = editNumberOrEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etPasswordConfirm.text.toString()

            when {
                !isValidEmail(input) && !isValidPhone(input) -> {
                    val message = if (editNumberOrEmail.inputType == android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) {
                        getString(R.string.invalidEmail)
                    } else {
                        getString(R.string.invalidNumber)
                    }
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
                password.length < 8 -> {
                    Toast.makeText(requireContext(), "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                }
                password != confirmPassword -> {
                    Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val auth = FirebaseAuth.getInstance()
                    val navController = NavHostFragment.findNavController(this)
                    auth.createUserWithEmailAndPassword(input, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val sharedPrefsUtils = SharedPrefsUtils(requireContext())
                                sharedPrefsUtils.writeCredentialsToSharedPrefs(
                                    editNumberOrEmail.inputType,
                                    input,
                                    password
                                )
                                navController.navigate(R.id.firstFragment)
                            }
                        }
                        .addOnFailureListener { exception ->
                            if (exception is FirebaseAuthUserCollisionException) {
                                Toast.makeText(requireContext(), "Email is already registered. Redirecting to Login.", Toast.LENGTH_LONG).show()
                                navController.navigate(R.id.loginFragment)
                            } else {
                                Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        }
        return root
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "^[A-Za-z0-9_+&*-]+(?:\\.[A-Za-z0-9_+&*-]+)*@" +
                    "(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,7}$"
        )
        return emailPattern.matcher(email).matches()
    }

    private fun isValidPhone(phone: String): Boolean {
        return phone.startsWith("+") && phone.length >= 10
    }
}
