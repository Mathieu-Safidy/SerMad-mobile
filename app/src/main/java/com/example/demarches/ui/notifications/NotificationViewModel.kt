package com.example.demarches.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demarches.data.local.entity.NotificationEntity
import com.example.demarches.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _notifications = MutableStateFlow<List<NotificationEntity>>(emptyList())
    val notifications: StateFlow<List<NotificationEntity>> = _notifications

    init {
        loadNotificationsLocal()
    }

    private fun loadNotificationsLocal() {
        viewModelScope.launch {
            notificationRepository.getNotificationsLocal().collect { notifs ->
                _notifications.value = notifs
            }
        }
    }

    fun refreshNotifications() {
        viewModelScope.launch {
            notificationRepository.refreshNotifications()
        }
    }

    fun markAsRead(id: Long) {
        viewModelScope.launch {
            notificationRepository.marquerLue(id)
        }
    }
}
