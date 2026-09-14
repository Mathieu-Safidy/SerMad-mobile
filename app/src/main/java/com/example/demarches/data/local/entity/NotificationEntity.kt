package com.example.demarches.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notification")
data class NotificationEntity(
    @PrimaryKey val idNotification: Long,
    val objetNotif: String?,
    val corpsNotif: String?,
    val dateNotif: Double?,
    val estLue: Boolean = false
)
