package com.example.demarches.ui.citoyen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demarches.data.local.entity.DemandeEntity
import com.example.demarches.data.repository.DemandeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DemandeViewModel @Inject constructor(
    private val demandeRepository: DemandeRepository
) : ViewModel() {

    private val _demandes = MutableStateFlow<List<DemandeEntity>>(emptyList())
    val demandes: StateFlow<List<DemandeEntity>> = _demandes

    private val _selectedDemande = MutableStateFlow<DemandeEntity?>(null)
    val selectedDemande: StateFlow<DemandeEntity?> = _selectedDemande

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadDemandesLocal()
    }

    private fun loadDemandesLocal() {
        viewModelScope.launch {
            demandeRepository.getDemandesLocal().collect { demandes ->
                _demandes.value = demandes
            }
        }
    }

    fun refreshDemandes() {
        viewModelScope.launch {
            _isLoading.value = true
            demandeRepository.refreshDemandes()
            _isLoading.value = false
        }
    }

    fun validerDemande(id: Long) {
        viewModelScope.launch {
            demandeRepository.validerDemande(id)
            refreshDemandes()
        }
    }
}
