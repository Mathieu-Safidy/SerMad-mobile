package com.example.demarches.ui.agent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demarches.data.remote.dto.DemandeRequest
import com.example.demarches.data.repository.DemandeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreerDemandeState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val qrCodeBase64: String? = null
)

@HiltViewModel
class CreerDemandeViewModel @Inject constructor(
    private val demandeRepository: DemandeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreerDemandeState())
    val state: StateFlow<CreerDemandeState> = _state

    fun creerDemande(
        libelle: String?,
        reference: String,
        idProcedureMere: Long?,
        idCitoyen: Long?
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val request = DemandeRequest(
                libelle = libelle,
                reference = reference,
                idProcedureMere = idProcedureMere,
                idCitoyen = idCitoyen
            )

            val result = demandeRepository.creerDemande(request)
            result.fold(
                onSuccess = { response ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        qrCodeBase64 = "QR Code généré"
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
}
