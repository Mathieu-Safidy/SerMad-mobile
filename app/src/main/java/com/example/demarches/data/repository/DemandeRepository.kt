package com.example.demarches.data.repository

import com.example.demarches.data.local.dao.DemandeDao
import com.example.demarches.data.local.entity.DemandeEntity
import com.example.demarches.data.remote.api.ApiService
import com.example.demarches.data.remote.dto.DemandeRequest
import com.example.demarches.data.remote.dto.DemandeResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DemandeRepository @Inject constructor(
    private val apiService: ApiService,
    private val demandeDao: DemandeDao
) {
    fun getDemandesLocal(): Flow<List<DemandeEntity>> {
        return demandeDao.getAllDemandes()
    }

    suspend fun refreshDemandes() {
        try {
            val response = apiService.getDemandes()
            if (response.isSuccessful) {
                val demandes = response.body()?.map { it.toEntity() } ?: emptyList()
                demandeDao.deleteAll()
                demandeDao.insertAll(demandes)
            }
        } catch (e: Exception) {
            // Offline, use cache
        }
    }

    suspend fun creerDemande(request: DemandeRequest): Result<DemandeResponse> {
        return try {
            val response = apiService.creerDemande(request)
            if (response.isSuccessful) {
                refreshDemandes()
                Result.success(response.body()!!.demande!!)
            } else {
                Result.failure(Exception("Erreur lors de la création"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun validerDemande(id: Long): Result<DemandeResponse> {
        return try {
            val response = apiService.validerDemande(id)
            if (response.isSuccessful) {
                refreshDemandes()
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erreur lors de la validation"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun validerQRCode(token: String): Result<Boolean> {
        return try {
            val response = apiService.validerQRCode(mapOf("token" to token))
            if (response.isSuccessful) {
                Result.success(response.body()?.valide ?: false)
            } else {
                Result.failure(Exception("QR Code invalide"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun DemandeResponse.toEntity() = DemandeEntity(
        idDemande = idDemande,
        libelle = libelle,
        reference = reference,
        dateDebut = dateDebut,
        dateFin = dateFin,
        statutLibelle = statutDemande?.libelle,
        procedureMereId = procedureMere?.idProcedureMere,
        documentLibelle = procedureMere?.document?.libelle,
        userId = user?.idUser ?: 0
    )
}
