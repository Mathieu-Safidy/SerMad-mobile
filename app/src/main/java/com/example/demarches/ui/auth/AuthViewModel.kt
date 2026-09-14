package com.example.demarches.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demarches.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null,
    val profil: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            val loggedIn = authRepository.isLoggedIn()
            val profil = authRepository.getUserProfil()
            _state.value = _state.value.copy(isLoggedIn = loggedIn, profil = profil)
        }
    }

    fun login(email: String, motDePasse: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = authRepository.login(email, motDePasse)
            result.fold(
                onSuccess = { auth ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        profil = auth.profil
                    )
                },
                onFailure = { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            )
        }
    }

    fun register(
        nom: String, prenom: String, email: String,
        telephone: String?, motDePasse: String,
        cin: String?, dateNaissance: String
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = authRepository.register(
                nom, prenom, email, telephone, motDePasse, cin, dateNaissance
            )
            result.fold(
                onSuccess = { auth ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        profil = auth.profil
                    )
                },
                onFailure = { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _state.value = AuthState()
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
