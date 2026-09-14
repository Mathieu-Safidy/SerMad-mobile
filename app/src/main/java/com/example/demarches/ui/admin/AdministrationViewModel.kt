package com.example.demarches.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demarches.data.local.entity.AdministrationEntity
import com.example.demarches.data.repository.AdministrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdministrationViewModel @Inject constructor(
    private val administrationRepository: AdministrationRepository
) : ViewModel() {

    private val _administrations = MutableStateFlow<List<AdministrationEntity>>(emptyList())
    val administrations: StateFlow<List<AdministrationEntity>> = _administrations

    init {
        loadAdministrationsLocal()
    }

    private fun loadAdministrationsLocal() {
        viewModelScope.launch {
            administrationRepository.getAdministrationsLocal().collect { admins ->
                _administrations.value = admins
            }
        }
    }

    fun refreshAdministrations() {
        viewModelScope.launch {
            administrationRepository.refreshAdministrations()
        }
    }
}
