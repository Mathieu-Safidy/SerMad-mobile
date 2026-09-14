package com.example.demarches.ui.citoyen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demarches.data.local.entity.DemandeEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemandeListScreen(
    onDemandeClick: (Long) -> Unit,
    viewModel: DemandeViewModel = hiltViewModel()
) {
    val demandes by viewModel.demandes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshDemandes()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mes demandes") })
        }
    ) { paddingValues ->
        if (demandes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Aucune demande")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(demandes) { demande ->
                    DemandeCard(demande = demande, onClick = { onDemandeClick(demande.idDemande) })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemandeCard(demande: DemandeEntity, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = demande.libelle ?: "Demande",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Réf: ${demande.reference ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Document: ${demande.documentLibelle ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            AssistChip(
                onClick = {},
                label = { Text(demande.statutLibelle ?: "Inconnu") }
            )
        }
    }
}
