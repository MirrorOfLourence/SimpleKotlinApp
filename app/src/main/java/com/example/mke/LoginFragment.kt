package com.example.mke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        val etEmail = root.findViewById<EditText>(R.id.etEmailLogin)
        val etPassword = root.findViewById<EditText>(R.id.etPasswordLogin)
        val btnLogin = root.findViewById<Button>(R.id.btnLogin)
        val cbAutoLogin = root.findViewById<CheckBox>(R.id.cbAutoLogin)

        val sharedPrefsUtils = SharedPrefsUtils(requireContext())
        val creds = sharedPrefsUtils.readCredentialsFromSharedPrefs()
        cbAutoLogin.isChecked = creds.enterAutomatically

        btnLogin.setOnClickListener {
            val inputEmail = etEmail.text.toString()
            val inputPassword = etPassword.text.toString()

            val savedEmail = creds.email
            val savedPassword = creds.password

            if (inputEmail == savedEmail && inputPassword == savedPassword) {
                sharedPrefsUtils.writeParamToSharedPrefs("enterAutomatically", cbAutoLogin.isChecked)

                val navController = NavHostFragment.findNavController(this)
                navController.navigate(R.id.firstFragment)
            } else {
                Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        return root
    }
}