package com.example.demarches.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demarches.data.local.entity.NotificationEntity
import com.example.demarches.ui.components.*
import com.example.demarches.ui.theme.GoldContainer
import com.example.demarches.ui.theme.GoldMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationListScreen(
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val notifications by viewModel.notifications.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshNotifications()
    }

    val unreadCount = notifications.count { !it.estLue }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            SerMadTopBar(
                title = "Notifications",
                subtitle = if (unreadCount > 0)
                    "$unreadCount non lue(s)"
                else
                    "Toutes les notifications sont lues"
            )
        }
    ) { paddingValues ->
        if (notifications.isEmpty()) {
            EmptyState(
                icon = Icons.Filled.Campaign,
                title = "Aucune notification",
                message = "Vous serez informé(e) de l'avancement de vos démarches ici.",
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notifications, key = { it.idNotification }) { notification ->
                    NotificationCard(
                        notification = notification,
                        onMarkRead = { viewModel.markAsRead(notification.idNotification) }
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationCard(
    notification: NotificationEntity,
    onMarkRead: () -> Unit
) {
    val isUnread = !notification.estLue
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnread)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isUnread) 1.dp else 0.dp
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = if (isUnread) GoldMain else GoldContainer,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.Campaign,
                            contentDescription = null,
                            tint = if (isUnread) Color.White else GoldMain,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = notification.objetNotif ?: "Notification",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f),
                        maxLines = 1
                    )
                    if (isUnread) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .background(GoldMain, CircleShape)
                        )
                    }
                }
            }

            notification.corpsNotif?.let { corps ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = corps,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3
                )
            }

            if (isUnread) {
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(
                    onClick = onMarkRead,
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Marquer comme lue")
                }
            }
        }
    }
}