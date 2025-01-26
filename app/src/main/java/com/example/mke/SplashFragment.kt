package com.example.mke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_splash, container, false)

        val navController = NavHostFragment.findNavController(this)

        val sharedPrefsUtils = SharedPrefsUtils(requireContext())
        val creds = sharedPrefsUtils.readCredentialsFromSharedPrefs()
        if ((!creds.number.isNullOrEmpty() || !creds.email.isNullOrEmpty()) &&
            !creds.password.isNullOrEmpty())
        {
            if (creds.enterAutomatically){
                navController.navigate(R.id.firstFragment)
            }
            else {
                navController.navigate(R.id.loginFragment)
            }
        }
        else {
            navController.navigate(R.id.registerFragment)
        }

        return root
    }
}