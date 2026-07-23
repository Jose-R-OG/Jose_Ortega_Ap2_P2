package com.example.jose_ortega_ap2_p2.presentation.list

sealed interface GastoListEvent {
    data object LoadGastos : GastoListEvent
}