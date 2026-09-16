package com.example.demarches.ui.document

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demarches.data.local.entity.DocumentEntity
import com.example.demarches.data.repository.AdministrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(
    private val administrationRepository: AdministrationRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val documents: StateFlow<List<DocumentEntity>> = administrationRepository
        .getDocumentsLocal()
        .combine(_searchQuery) { docs, query ->
            if (query.isBlank()) {
                docs
            } else {
                docs.filter {
                    it.libelle.contains(query, ignoreCase = true) ||
                            (it.categorieLibelle?.contains(query, ignoreCase = true) == true) ||
                            (it.typeDocumentLibelle?.contains(query, ignoreCase = true) == true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun refreshDocuments() {
        viewModelScope.launch {
            administrationRepository.refreshDocuments()
        }
    }
}