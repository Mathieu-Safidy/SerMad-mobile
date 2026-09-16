package com.example.demarches.data.local.dao

import androidx.room.*
import com.example.demarches.data.local.entity.DocumentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {
    @Query("SELECT * FROM Document")
    fun getAllDocuments(): Flow<List<DocumentEntity>>

    @Query("SELECT * FROM Document WHERE estLieuUnique = 1")
    fun getDocumentsLieuUnique(): Flow<List<DocumentEntity>>

    @Query("SELECT * FROM Document WHERE idDocument = :idDocument")
    fun getDocumentById(idDocument: Long): Flow<DocumentEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: DocumentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(documents: List<DocumentEntity>)

    @Query("DELETE FROM Document")
    suspend fun deleteAll()
}
