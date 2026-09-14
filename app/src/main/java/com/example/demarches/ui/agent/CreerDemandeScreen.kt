package com.example.demarches.ui.agent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreerDemandeScreen(
    onDemandeCreee: () -> Unit,
    onBack: () -> Unit,
    viewModel: CreerDemandeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var libelle by remember { mutableStateOf("") }
    var reference by remember { mutableStateOf("") }
    var idProcedureMere by remember { mutableStateOf("") }
    var idCitoyen by remember { mutableStateOf("") }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onDemandeCreee()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Créer une demande") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = libelle,
                onValueChange = { libelle = it },
                label = { Text("Libellé") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = reference,
                onValueChange = { reference = it },
                label = { Text("Référence") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = idProcedureMere,
                onValueChange = { idProcedureMere = it },
                label = { Text("ID Procédure") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = idCitoyen,
                onValueChange = { idCitoyen = it },
                label = { Text("ID Citoyen") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.creerDemande(
                        libelle = libelle.ifBlank { null },
                        reference = reference,
                        idProcedureMere = idProcedureMere.toLongOrNull(),
                        idCitoyen = idCitoyen.toLongOrNull()
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading && reference.isNotBlank()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                } else {
                    Text("Créer la demande")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBack) {
                Text("Retour")
            }

            state.error?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            state.qrCodeBase64?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "QR Code généré ! Présentez-le au citoyen.",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
