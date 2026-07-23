package com.example.jose_ortega_ap2_p2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object GastoList : Screen()

    @Serializable
    data class GastoDetail(val id: Int) : Screen()
}