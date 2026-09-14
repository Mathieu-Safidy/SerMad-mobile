package com.example.demarches.data.local.dao

import androidx.room.*
import com.example.demarches.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query("SELECT * FROM Notification ORDER BY dateNotif DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notifications: List<NotificationEntity>)

    @Query("UPDATE Notification SET estLue = 1 WHERE idNotification = :id")
    suspend fun markAsRead(id: Long)

    @Query("DELETE FROM Notification")
    suspend fun deleteAll()
}
