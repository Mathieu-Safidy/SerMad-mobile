package com.example.demarches.ui.document

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demarches.data.local.entity.DocumentEntity
import com.example.demarches.data.remote.dto.LieuResponse
import com.example.demarches.data.repository.AdministrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentDetailViewModel @Inject constructor(
    private val administrationRepository: AdministrationRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val idDocument: Long = savedStateHandle.get<Long>("idDocument") ?: 0L

    private val _lieu = MutableStateFlow<LieuResponse?>(null)
    val lieu: StateFlow<LieuResponse?> = _lieu.asStateFlow()

    val document: StateFlow<DocumentEntity?> =
        administrationRepository.getDocumentById(idDocument)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            val lieu = administrationRepository.getDocumentLieu(idDocument)
            _lieu.value = lieu
            lieu?.idAdministration?.let { idAdministration ->
                administrationRepository.refreshAdministrationWithLocalisations(idAdministration)
            }
        }
    }
}