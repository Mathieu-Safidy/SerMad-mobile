package com.example.demarches.data.repository

import com.example.demarches.data.local.dao.AdministrationDao
import com.example.demarches.data.local.dao.DocumentDao
import com.example.demarches.data.local.dao.LocalisationDao
import com.example.demarches.data.local.entity.AdministrationEntity
import com.example.demarches.data.local.entity.DocumentEntity
import com.example.demarches.data.local.entity.LocalisationAdmEntity
import com.example.demarches.data.remote.api.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdministrationRepository @Inject constructor(
    private val apiService: ApiService,
    private val administrationDao: AdministrationDao,
    private val localisationDao: LocalisationDao,
    private val documentDao: DocumentDao
) {
    fun getAdministrationsLocal(): Flow<List<AdministrationEntity>> {
        return administrationDao.getAllAdministrations()
    }

    fun getLocalisationsLocal(): Flow<List<LocalisationAdmEntity>> {
        return localisationDao.getAllLocalisations()
    }

    fun getDocumentsLocal(): Flow<List<DocumentEntity>> {
        return documentDao.getAllDocuments()
    }

    suspend fun refreshAdministrations() {
        try {
            val response = apiService.getAdministrations()
            if (response.isSuccessful) {
                val admins = response.body()?.map {
                    AdministrationEntity(
                        idAdministration = it.idAdministration,
                        libelle = it.libelle ?: "",
                        typeAdmLibelle = it.typeAdm?.libelle
                    )
                } ?: emptyList()
                administrationDao.deleteAll()
                administrationDao.insertAll(admins)

                // Load localisations for each administration
                response.body()?.forEach { admin ->
                    val locResponse = apiService.getLocalisation(admin.idAdministration)
                    if (locResponse.isSuccessful) {
                        val localisations = locResponse.body()?.map {
                            LocalisationAdmEntity(
                                idLocalisationAdm = it.idLocalisationAdm,
                                libelle = it.libelle,
                                adresse = it.adresse,
                                longitude = it.longitude,
                                latitude = it.latitude,
                                codePostal = it.codePostal,
                                idAdministration = it.idAdministration
                            )
                        } ?: emptyList()
                        localisationDao.insertAll(localisations)
                    }
                }
            }
        } catch (e: Exception) {
            // Offline, use cache
        }
    }

    suspend fun refreshDocuments() {
        try {
            val response = apiService.getDocuments()
            if (response.isSuccessful) {
                val documents = response.body()?.map {
                    DocumentEntity(
                        idDocument = it.idDocument,
                        libelle = it.libelle ?: "",
                        ageMinimum = it.ageMinimum,
                        categorieLibelle = null,
                        administrationLibelle = null,
                        typeDocumentLibelle = null
                    )
                } ?: emptyList()
                documentDao.deleteAll()
                documentDao.insertAll(documents)
            }
        } catch (e: Exception) {
            // Offline, use cache
        }
    }
}
