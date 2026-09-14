package com.example.demarches.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    profil: String?,
    onDemandesClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onAdministrationsClick: () -> Unit,
    onDocumentsClick: () -> Unit,
    onCreerDemandeClick: () -> Unit,
    onQRCodeClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val isAgent = profil == "Agent" || profil == "Admin"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Demarches Admin") },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(Icons.Default.Logout, contentDescription = "Déconnexion")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Bonjour, $profil",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Agent/Admin: Créer une demande
            if (isAgent) {
                HomeCard(
                    title = "Créer une demande",
                    subtitle = "Saisir les informations d'un citoyen",
                    icon = Icons.Default.AddCircle,
                    onClick = onCreerDemandeClick
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            HomeCard(
                title = "Mes demandes",
                subtitle = "Consulter et valider vos demandes",
                icon = Icons.Default.List,
                onClick = onDemandesClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            HomeCard(
                title = "Notifications",
                subtitle = "Vos alertes et messages",
                icon = Icons.Default.Notifications,
                onClick = onNotificationsClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            HomeCard(
                title = "Administrations",
                subtitle = "Localiser les administrations",
                icon = Icons.Default.LocationOn,
                onClick = onAdministrationsClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            HomeCard(
                title = "Documents",
                subtitle = "Documents administratifs disponibles",
                icon = Icons.Default.Description,
                onClick = onDocumentsClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            HomeCard(
                title = "Scanner QR Code",
                subtitle = "Valider votre identité au guichet",
                icon = Icons.Default.QrCodeScanner,
                onClick = onQRCodeClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
