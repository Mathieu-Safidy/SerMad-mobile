package com.example.demarches.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProcedureMere")
data class ProcedureMereEntity(
    @PrimaryKey val idProcedureMere: Long,
    val documentLibelle: String?,
    val delai: Double?,
    val cout: Double?
)
