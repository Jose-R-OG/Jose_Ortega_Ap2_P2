package com.example.jose_ortega_ap2_p2.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.jose_ortega_ap2_p2.data.remote.Resource
import com.example.jose_ortega_ap2_p2.domain.usecase.GetGastoDetailUseCase
import com.example.jose_ortega_ap2_p2.domain.usecase.SaveGastoUseCase
import com.example.jose_ortega_ap2_p2.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GastoDetailViewModel @Inject constructor(
    private val getGastoDetailUseCase: GetGastoDetailUseCase,
    private val saveGastoUseCase: SaveGastoUseCase,
    savedState: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(GastoDetailUiState())
    val state = _state.asStateFlow()

    init {
        val args = savedState.toRoute<Screen.GastoDetail>()
        if (args.id > 0) {
            loadGasto(args.id)
        }
    }

    fun onEvent(event: GastoDetailEvent) {
        when (event) {
            is GastoDetailEvent.OnFechaChange -> _state.update { it.copy(fecha = event.value) }
            is GastoDetailEvent.OnSuplidorChange -> _state.update { it.copy(suplidor = event.value) }
            is GastoDetailEvent.OnNcfChange -> _state.update { it.copy(ncf = event.value) }
            is GastoDetailEvent.OnItbisChange -> _state.update { it.copy(itbis = event.value) }
            is GastoDetailEvent.OnMontoChange -> _state.update { it.copy(monto = event.value) }
            is GastoDetailEvent.Save -> saveGasto()
        }
    }

    private fun loadGasto(id: Int) {
        viewModelScope.launch {
            getGastoDetailUseCase(id).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        result.data?.let { gasto ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    gastoId = gasto.gastoId,
                                    fecha = gasto.fecha,
                                    suplidor = gasto.suplidor,
                                    ncf = gasto.ncf,
                                    itbis = gasto.itbis.toString(),
                                    monto = gasto.monto.toString()
                                )
                            }
                        }
                    }
                    is Resource.Error -> _state.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
            }
        }
    }

    private fun saveGasto() {
        val currentState = _state.value

        if (currentState.suplidor.isBlank()) {
            _state.update { it.copy(errorMessage = "El suplidor no puede estar vacío.") }
            return
        }

        val montoParsed = currentState.monto.toDoubleOrNull() ?: 0.0
        if (montoParsed <= 0) {
            _state.update { it.copy(errorMessage = "El monto debe ser mayor a 0.") }
            return
        }

        viewModelScope.launch {
            saveGastoUseCase(
                gastoId = currentState.gastoId,
                fecha = currentState.fecha,
                suplidor = currentState.suplidor,
                ncf = currentState.ncf,
                itbis = currentState.itbis.toDoubleOrNull() ?: 0.0,
                monto = montoParsed
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true, errorMessage = null) }
                    is Resource.Success -> _state.update { it.copy(isLoading = false, isSuccess = true) }
                    is Resource.Error -> _state.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
            }
        }
    }
}