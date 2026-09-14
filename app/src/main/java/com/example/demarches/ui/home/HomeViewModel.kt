package com.example.demarches.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demarches.data.remote.api.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _profil = MutableStateFlow<String?>(null)
    val profil: StateFlow<String?> = _profil

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _profil.value = tokenManager.getUserProfil()
            val prenom = tokenManager.getUserPrenom()
            val nom = tokenManager.getUserNom()
            _userName.value = "$prenom $nom"
        }
    }
}
