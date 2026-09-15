package com.example.demarches.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demarches.data.local.entity.AdministrationEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdministrationListScreen(
    onLocaliserClick: (Long) -> Unit,
    viewModel: AdministrationViewModel = hiltViewModel()
) {
    val administrations by viewModel.administrations.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshAdministrations()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Administrations") })
        }
    ) { paddingValues ->
        if (administrations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(administrations) { admin ->
                    AdministrationCard(
                        admin = admin,
                        onLocaliserClick = { onLocaliserClick(admin.idAdministration) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdministrationCard(admin: AdministrationEntity, onLocaliserClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = admin.libelle,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = admin.typeAdmLibelle ?: "Type inconnu",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(onClick = onLocaliserClick) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Localiser",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Localiser")
            }
        }
    }
}
