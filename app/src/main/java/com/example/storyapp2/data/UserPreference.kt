package com.example.storyapp2.data

import android.content.Context

class UserPreference(context: Context) {
    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setToken(token: String) {
        val edit = preference.edit()
        edit.putString(TOKEN, token)
        edit.apply()
    }

    fun getToken(): String? {
        return preference.getString(TOKEN, null)
    }

    fun clearToken() {
        val edit = preference.edit().clear()
        edit.apply()
    }

    companion object {
        const val PREF_NAME = "login_pref"
        const val TOKEN = "token"
    }
}