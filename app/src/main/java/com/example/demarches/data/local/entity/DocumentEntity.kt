package com.example.demarches.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Document")
data class DocumentEntity(
    @PrimaryKey val idDocument: Long,
    val libelle: String,
    val ageMinimum: Int?,
    val estLieuUnique: Boolean?,
    val dossier: String?,
    val administrationId: Long?,
    val categorieLibelle: String?,
    val administrationLibelle: String?,
    val typeDocumentLibelle: String?
)
