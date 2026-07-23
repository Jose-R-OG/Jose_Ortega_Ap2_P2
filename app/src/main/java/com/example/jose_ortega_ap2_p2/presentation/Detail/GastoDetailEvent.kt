package com.example.jose_ortega_ap2_p2.presentation.detail

sealed interface GastoDetailEvent {
    data class OnFechaChange(val value: String) : GastoDetailEvent
    data class OnSuplidorChange(val value: String) : GastoDetailEvent
    data class OnNcfChange(val value: String) : GastoDetailEvent
    data class OnItbisChange(val value: String) : GastoDetailEvent
    data class OnMontoChange(val value: String) : GastoDetailEvent
    data object Save : GastoDetailEvent
}