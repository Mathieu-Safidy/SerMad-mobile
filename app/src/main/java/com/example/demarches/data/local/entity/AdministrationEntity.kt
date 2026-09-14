package com.example.demarches.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Administration")
data class AdministrationEntity(
    @PrimaryKey val idAdministration: Long,
    val libelle: String,
    val typeAdmLibelle: String?
)
