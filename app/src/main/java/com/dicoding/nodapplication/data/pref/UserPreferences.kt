package com.dicoding.nodapplication.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

    class UserPreferences(private val dataStore: DataStore<Preferences>) {

        suspend fun saveSession(email: String, isLogin: Boolean, token:String) {
            dataStore.edit {preferences ->
                preferences[EMAIL] = email
                preferences[LOGIN_KEY] = isLogin
                preferences[TOKEN] = token
            }
        }

        suspend fun clearSession() {
            dataStore.edit { preferences ->
                preferences[EMAIL] = ""
                preferences[LOGIN_KEY] = false
                preferences[TOKEN] = ""
            }
        }

        fun isLogin(): Flow<Boolean> {
            return dataStore.data.map { preferences ->
                preferences[LOGIN_KEY] ?: false
            }
        }

        fun getEmail(): Flow<String> {
            return dataStore.data.map {
                it[EMAIL] ?: ""
            }
        }

        fun getToken(): Flow<String> {
            return dataStore.data.map {
                it[TOKEN] ?: ""
            }
        }

        companion object {
            private var INSTANCE: UserPreferences? = null

            private val EMAIL = stringPreferencesKey("email")
            private val LOGIN_KEY = booleanPreferencesKey("isLogin")
            private val TOKEN = stringPreferencesKey("token")

            fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
                return INSTANCE ?: synchronized(this) {
                    val instance = UserPreferences(dataStore)
                    INSTANCE = instance
                    instance
                }
            }
        }
    }
