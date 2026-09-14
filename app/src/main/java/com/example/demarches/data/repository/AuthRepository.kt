package com.example.demarches.data.repository

import com.example.demarches.data.local.dao.UserDao
import com.example.demarches.data.local.entity.UserEntity
import com.example.demarches.data.remote.api.ApiService
import com.example.demarches.data.remote.api.TokenManager
import com.example.demarches.data.remote.dto.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val tokenManager: TokenManager
) {
    suspend fun login(email: String, motDePasse: String): Result<AuthResponse> {
        return try {
            val response = apiService.login(LoginRequest(email, motDePasse))
            if (response.isSuccessful) {
                val authResponse = response.body()!!
                authResponse.token?.let { tokenManager.saveToken(it) }
                tokenManager.saveUserData(
                    authResponse.userId ?: 0,
                    authResponse.nom ?: "",
                    authResponse.prenom ?: "",
                    authResponse.email ?: "",
                    authResponse.profil ?: ""
                )
                userDao.insertUser(
                    UserEntity(
                        idUser = authResponse.userId ?: 0,
                        nom = authResponse.nom ?: "",
                        prenom = authResponse.prenom ?: "",
                        email = authResponse.email ?: "",
                        telephone = null,
                        cin = null,
                        dateNaissance = null,
                        profil = authResponse.profil
                    )
                )
                Result.success(authResponse)
            } else {
                Result.failure(Exception("Erreur de connexion"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(
        nom: String, prenom: String, email: String,
        telephone: String?, motDePasse: String,
        cin: String?, dateNaissance: String
    ): Result<AuthResponse> {
        return try {
            val response = apiService.register(
                RegisterRequest(nom, prenom, email, telephone, motDePasse, cin, dateNaissance)
            )
            if (response.isSuccessful) {
                val authResponse = response.body()!!
                authResponse.token?.let { tokenManager.saveToken(it) }
                tokenManager.saveUserData(
                    authResponse.userId ?: 0,
                    authResponse.nom ?: "",
                    authResponse.prenom ?: "",
                    authResponse.email ?: "",
                    authResponse.profil ?: ""
                )
                Result.success(authResponse)
            } else {
                Result.failure(Exception("Erreur d'inscription"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        tokenManager.clearAll()
        userDao.deleteAll()
    }

    suspend fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }

    suspend fun getUserProfil(): String? {
        return tokenManager.getUserProfil()
    }
}
