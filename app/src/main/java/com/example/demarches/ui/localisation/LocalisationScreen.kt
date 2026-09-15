package com.example.demarches.ui.localisation

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.demarches.data.local.entity.LocalisationAdmEntity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalisationScreen(
    onBack: () -> Unit,
    viewModel: LocalisationViewModel = hiltViewModel()
) {
    val localisations by viewModel.localisations.collectAsState()
    val administration by viewModel.administration.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(administration?.libelle ?: "Localisation") },
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
        val localisation = localisations.firstOrNull { it.latitude != null && it.longitude != null }

        if (localisation == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Aucune localisation disponible")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                GoogleMapView(
                    latitude = localisation.latitude ?: 0.0,
                    longitude = localisation.longitude ?: 0.0,
                    markerTitle = localisation.libelle ?: administration?.libelle ?: "Administration",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                LocalisationDetailsCard(localisation = localisation)
            }
        }
    }
}

@Composable
fun GoogleMapView(
    latitude: Double,
    longitude: Double,
    markerTitle: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, mapView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val position = remember(latitude, longitude) { LatLng(latitude, longitude) }

    AndroidView(
        factory = { mapView },
        modifier = modifier
    ) { view ->
        view.getMapAsync { googleMap ->
            googleMap.clear()
            googleMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .title(markerTitle)
            )
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(position, 15f)
            )
            googleMap.uiSettings.isZoomControlsEnabled = true
        }
    }
}

@Composable
fun LocalisationDetailsCard(localisation: LocalisationAdmEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = localisation.libelle ?: "Localisation",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            localisation.adresse?.let {
                Text(
                    text = "Adresse: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            localisation.codePostal?.let {
                Text(
                    text = "Code postal: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Text(
                text = "Latitude: ${localisation.latitude}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Longitude: ${localisation.longitude}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}