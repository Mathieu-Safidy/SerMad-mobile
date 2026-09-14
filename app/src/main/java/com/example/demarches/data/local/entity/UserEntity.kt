package com.example.demarches.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_")
data class UserEntity(
    @PrimaryKey val idUser: Long,
    val nom: String,
    val prenom: String,
    val email: String,
    val telephone: String?,
    val cin: String?,
    val dateNaissance: String?,
    val estActif: Boolean = true,
    val profil: String?
)
