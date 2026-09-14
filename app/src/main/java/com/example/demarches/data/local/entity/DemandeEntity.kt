package com.example.demarches.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Demande")
data class DemandeEntity(
    @PrimaryKey val idDemande: Long,
    val libelle: String?,
    val reference: String?,
    val dateDebut: Double?,
    val dateFin: Double?,
    val statutLibelle: String?,
    val procedureMereId: Long?,
    val documentLibelle: String?,
    val userId: Long
)
