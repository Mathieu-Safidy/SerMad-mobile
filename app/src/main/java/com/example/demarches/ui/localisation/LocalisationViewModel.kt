package com.example.demarches.ui.localisation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demarches.data.local.entity.AdministrationEntity
import com.example.demarches.data.local.entity.LocalisationAdmEntity
import com.example.demarches.data.repository.AdministrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalisationViewModel @Inject constructor(
    private val administrationRepository: AdministrationRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val idAdministration: Long = savedStateHandle.get<Long>("idAdministration") ?: 0L

    val localisations: StateFlow<List<LocalisationAdmEntity>> =
        administrationRepository.getLocalisationsForAdministration(idAdministration)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val administration: StateFlow<AdministrationEntity?> =
        administrationRepository.getAdministration(idAdministration)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun refresh() {
        viewModelScope.launch {
            administrationRepository.refreshAdministrationWithLocalisations(idAdministration)
        }
    }
}