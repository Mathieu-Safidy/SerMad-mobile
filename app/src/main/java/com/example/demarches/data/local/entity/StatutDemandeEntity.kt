package com.example.demarches.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StatutDemande")
data class StatutDemandeEntity(
    @PrimaryKey val idStatutDemande: Long,
    val libelle: String
)
