package com.example.demarches.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LocalisationAdm")
data class LocalisationAdmEntity(
    @PrimaryKey val idLocalisationAdm: Long,
    val libelle: String?,
    val adresse: String?,
    val longitude: Double?,
    val latitude: Double?,
    val codePostal: String?,
    val idAdministration: Long
)
