package com.example.storyapp2.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveKey(userToken: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = userToken
        }
    }

    fun getKey(): Flow<String> {
        return dataStore.data.map { preferences ->
                preferences[TOKEN] ?: ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val TOKEN = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}