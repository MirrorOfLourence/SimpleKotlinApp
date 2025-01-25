package com.example.mke

import android.content.Context
import android.text.InputType

class SharedPrefsUtils(context: Context) {

    private val storage = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    data class Credentials(
        val email: String?,
        val number: String?,
        val password: String?,
        val enterAutomatically: Boolean
    )

    fun writeCredentialsToSharedPrefs(loginType: Int, login: String, password: String) {
        if (loginType == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) {
            writeParamToSharedPrefs("email", login)
        } else if (loginType == InputType.TYPE_CLASS_PHONE) {
            writeParamToSharedPrefs("number", login)
        }
        writeParamToSharedPrefs("password", password)
    }

    fun <T> writeParamToSharedPrefs(name: String, value: T) {
        with(storage.edit()) {
            when (value) {
                is String -> putString(name, value)
                is Boolean -> putBoolean(name, value)
                is Int -> putInt(name, value)
                is Float -> putFloat(name, value)
                is Long -> putLong(name, value)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            apply()
        }
    }

    fun readCredentialsFromSharedPrefs(): Credentials {
        val email = storage.getString("email", null)
        val number = storage.getString("number", null)
        val password = storage.getString("password", null)
        val enterAutomatically = storage.getBoolean("enterAutomatically", false)

        return Credentials(email, number, password, enterAutomatically)
    }
}