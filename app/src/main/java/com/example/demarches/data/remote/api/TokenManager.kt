package com.example.demarches.data.remote.api

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class TokenManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_NOM_KEY = stringPreferencesKey("user_nom")
        private val USER_PRENOM_KEY = stringPreferencesKey("user_prenom")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_PROFIL_KEY = stringPreferencesKey("user_profil")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[TOKEN_KEY]
        }.first()
    }

    suspend fun saveUserData(userId: Long, nom: String, prenom: String, email: String, profil: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId.toString()
            prefs[USER_NOM_KEY] = nom
            prefs[USER_PRENOM_KEY] = prenom
            prefs[USER_EMAIL_KEY] = email
            prefs[USER_PROFIL_KEY] = profil
        }
    }

    suspend fun getUserId(): Long? {
        return context.dataStore.data.map { prefs ->
            prefs[USER_ID_KEY]?.toLongOrNull()
        }.first()
    }

    suspend fun getUserProfil(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[USER_PROFIL_KEY]
        }.first()
    }

    suspend fun getUserNom(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[USER_NOM_KEY]
        }.first()
    }

    suspend fun getUserPrenom(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[USER_PRENOM_KEY]
        }.first()
    }

    suspend fun getUserEmail(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[USER_EMAIL_KEY]
        }.first()
    }

    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}
