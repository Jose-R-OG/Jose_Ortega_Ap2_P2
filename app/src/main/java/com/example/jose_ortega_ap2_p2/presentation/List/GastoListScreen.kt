package com.example.jose_ortega_ap2_p2.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jose_ortega_ap2_p2.domain.model.Gasto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastoListScreen(
    viewModel: GastoListViewModel = hiltViewModel(),
    onCreateGasto: () -> Unit,
    onGastoClick: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Registro de Gastos") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateGasto) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Gasto")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.error?.let {
                Text(
                    text = "Error: $it",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(state.gastos) { gasto ->
                    GastoItem(gasto = gasto, onClick = { onGastoClick(gasto.gastoId) })
                }
            }

            ElevatedCard() { }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Absolute.Left
            ) {
                Text("Total Gastos: ${state.gastos.size}", fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Absolute.Left
            ) {
                Text("Monto Total: $${state.gastos.sumOf { it.monto }}",
                    fontWeight = FontWeight.Bold)
            }


        }
    }
}

@Composable
fun GastoItem(
    gasto: Gasto,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("ID: ${gasto.gastoId}", style = MaterialTheme.typography.bodyMedium)
            Text("Suplidor: ${gasto.suplidor}", style = MaterialTheme.typography.bodyMedium)
            Text("Fecha: ${gasto.fecha}", style = MaterialTheme.typography.bodyMedium)
            Text("Monto: $${gasto.monto}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}