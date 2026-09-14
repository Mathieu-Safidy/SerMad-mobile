package com.example.demarches.data.local.dao

import androidx.room.*
import com.example.demarches.data.local.entity.AdministrationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AdministrationDao {
    @Query("SELECT * FROM Administration")
    fun getAllAdministrations(): Flow<List<AdministrationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdministration(admin: AdministrationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(admins: List<AdministrationEntity>)

    @Query("DELETE FROM Administration")
    suspend fun deleteAll()
}
