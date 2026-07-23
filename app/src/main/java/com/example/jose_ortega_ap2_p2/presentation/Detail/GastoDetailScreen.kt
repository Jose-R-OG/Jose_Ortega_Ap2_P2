package com.example.jose_ortega_ap2_p2.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastoDetailScreen(
    viewModel: GastoDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.gastoId == 0) "Registro de Gasto" else "Edición de Gasto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(GastoDetailEvent.Save) }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Guardar Gasto")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.fecha,
                onValueChange = { viewModel.onEvent(GastoDetailEvent.OnFechaChange(it)) },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.suplidor,
                onValueChange = { viewModel.onEvent(GastoDetailEvent.OnSuplidorChange(it)) },
                label = { Text("Suplidor") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.suplidor.isBlank() && state.errorMessage != null
            )

            OutlinedTextField(
                value = state.ncf,
                onValueChange = { viewModel.onEvent(GastoDetailEvent.OnNcfChange(it)) },
                label = { Text("NCF") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.itbis,
                onValueChange = { viewModel.onEvent(GastoDetailEvent.OnItbisChange(it)) },
                label = { Text("ITBIS") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.monto,
                onValueChange = { viewModel.onEvent(GastoDetailEvent.OnMontoChange(it)) },
                label = { Text("Monto") },
                modifier = Modifier.fillMaxWidth()
            )

            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}