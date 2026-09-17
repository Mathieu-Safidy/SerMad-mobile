package com.example.demarches.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demarches.data.local.entity.AdministrationEntity
import com.example.demarches.ui.components.*
import com.example.demarches.ui.theme.MgGreen
import com.example.demarches.ui.theme.MgGreenContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdministrationListScreen(
    onLocaliserClick: (Long) -> Unit,
    viewModel: AdministrationViewModel = hiltViewModel()
) {
    val administrations by viewModel.administrations.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshAdministrations()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            SerMadTopBar(
                title = "Administrations",
                subtitle = "Guichets et services publics"
            )
        }
    ) { paddingValues ->
        if (administrations.isEmpty()) {
            EmptyState(
                icon = Icons.Filled.HourglassEmpty,
                title = if (isLoading) "Chargement..." else "Aucune administration",
                message = if (isLoading)
                    "Récupération des administrations en cours..."
                else
                    "Aucune administration enregistrée pour le moment.",
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
                items(administrations, key = { it.idAdministration }) { admin ->
                    AdministrationCard(
                        admin = admin,
                        onLocaliserClick = { onLocaliserClick(admin.idAdministration) }
                    )
                }
            }
        }
    }
}

@Composable
fun AdministrationCard(admin: AdministrationEntity, onLocaliserClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(16.dp)
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
                    color = MgGreenContainer,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.AccountBalance,
                            contentDescription = null,
                            tint = MgGreen,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = admin.libelle,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    admin.typeAdmLibelle?.let {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick = onLocaliserClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MgGreen
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, MgGreen.copy(alpha = 0.5f))
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Localiser",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Localiser sur la carte")
            }
        }
    }
}