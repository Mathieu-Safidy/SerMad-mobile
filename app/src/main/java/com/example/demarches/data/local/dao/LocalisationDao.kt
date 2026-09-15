package com.example.demarches.data.local.dao

import androidx.room.*
import com.example.demarches.data.local.entity.LocalisationAdmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalisationDao {
    @Query("SELECT * FROM LocalisationAdm")
    fun getAllLocalisations(): Flow<List<LocalisationAdmEntity>>

    @Query("SELECT * FROM LocalisationAdm WHERE idAdministration = :idAdministration")
    fun getLocalisationsByAdministrationId(idAdministration: Long): Flow<List<LocalisationAdmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalisation(localisation: LocalisationAdmEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(localisations: List<LocalisationAdmEntity>)

    @Query("DELETE FROM LocalisationAdm")
    suspend fun deleteAll()
}
