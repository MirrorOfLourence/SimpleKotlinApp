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
import com.example.mke.SharedPrefsUtils.Credentials
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        val etNumberOrEmailLogin = root.findViewById<EditText>(R.id.etNumberOrEmailLogin)
        val etPassword = root.findViewById<EditText>(R.id.etPasswordLogin)
        val btnLogin = root.findViewById<Button>(R.id.btnLogin)
        val cbAutoLogin = root.findViewById<CheckBox>(R.id.cbAutoLogin)

        val sharedPrefsUtils = SharedPrefsUtils(requireContext())
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

            val auth = FirebaseAuth.getInstance()
            val navController = NavHostFragment.findNavController(this)
            auth.signInWithEmailAndPassword(inputLogin, inputPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sharedPrefsUtils.writeParamToSharedPrefs("enterAutomatically", cbAutoLogin.isChecked)
                        navController.navigate(R.id.firstFragment)
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        return root
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