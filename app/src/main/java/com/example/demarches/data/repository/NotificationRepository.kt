package com.example.demarches.data.repository

import com.example.demarches.data.local.dao.NotificationDao
import com.example.demarches.data.local.entity.NotificationEntity
import com.example.demarches.data.remote.api.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val apiService: ApiService,
    private val notificationDao: NotificationDao
) {
    fun getNotificationsLocal(): Flow<List<NotificationEntity>> {
        return notificationDao.getAllNotifications()
    }

    suspend fun refreshNotifications() {
        try {
            val response = apiService.getNotifications()
            if (response.isSuccessful) {
                val notifications = response.body()?.map {
                    NotificationEntity(
                        idNotification = it.idNotification,
                        objetNotif = it.objetNotif,
                        corpsNotif = it.corpsNotif,
                        dateNotif = it.dateNotif,
                        estLue = it.estLue ?: false
                    )
                } ?: emptyList()
                notificationDao.deleteAll()
                notificationDao.insertAll(notifications)
            }
        } catch (e: Exception) {
            // Offline, use cache
        }
    }

    suspend fun marquerLue(id: Long) {
        try {
            apiService.marquerNotificationLue(id)
            notificationDao.markAsRead(id)
        } catch (e: Exception) {
            notificationDao.markAsRead(id)
        }
    }
}
