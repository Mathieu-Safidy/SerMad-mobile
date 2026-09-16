package com.example.demarches.ui.document

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demarches.data.local.entity.DocumentEntity
import com.example.demarches.data.remote.dto.LieuResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentDetailScreen(
    onLocaliserClick: (Long) -> Unit,
    onBack: () -> Unit,
    viewModel: DocumentDetailViewModel = hiltViewModel()
) {
    val document by viewModel.document.collectAsState()
    val lieu by viewModel.lieu.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(document?.libelle ?: "Document") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (document == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            DocumentDetailContent(
                document = document!!,
                lieu = lieu,
                onLocaliserClick = onLocaliserClick,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    }
}

@Composable
fun DocumentDetailContent(
    document: DocumentEntity,
    lieu: LieuResponse?,
    onLocaliserClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val pieces: List<String> = document.dossier
        ?.split("\n")
        ?.map { it.trim() }
        ?.filter { it.isNotBlank() }
        ?: emptyList()

    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        item {
            Text(
                text = document.libelle,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            document.categorieLibelle?.let {
                Text(
                    text = "Catégorie: $it",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            document.typeDocumentLibelle?.let {
                Text(
                    text = "Type: $it",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            document.ageMinimum?.let {
                if (it > 0) {
                    Text(
                        text = "Âge minimum: $it ans",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Dossier nécessaire",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (pieces.isEmpty()) {
            item {
                Text(
                    text = "Aucune information disponible.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(pieces) { piece ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = piece,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Lieu administratif",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = lieu?.libelle ?: document.administrationLibelle ?: "Administration non renseignée",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    (lieu?.idAdministration ?: document.administrationId)?.let { idAdministration ->
                        OutlinedButton(
                            onClick = { onLocaliserClick(idAdministration) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Localiser sur la carte")
                        }
                    }
                }
            }
        }
    }
}