package com.example.demarches.data.local.dao

import androidx.room.*
import com.example.demarches.data.local.entity.DemandeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DemandeDao {
    @Query("SELECT * FROM Demande")
    fun getAllDemandes(): Flow<List<DemandeEntity>>

    @Query("SELECT * FROM Demande WHERE idDemande = :id")
    suspend fun getDemandeById(id: Long): DemandeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDemande(demande: DemandeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(demandes: List<DemandeEntity>)

    @Query("DELETE FROM Demande")
    suspend fun deleteAll()
}
