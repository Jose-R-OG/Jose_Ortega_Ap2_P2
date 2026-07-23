package com.example.jose_ortega_ap2_p2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.jose_ortega_ap2_p2.presentation.list.GastoListScreen
import com.example.jose_ortega_ap2_p2.presentation.detail.GastoDetailScreen

@Composable
fun ApiGastoNavDisplay(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.GastoList
    ) {
        composable<Screen.GastoList> {
            GastoListScreen(
                onCreateGasto = {
                    navController.navigate(Screen.GastoDetail(0))
                },
                onGastoClick = { gastoId ->
                    navController.navigate(Screen.GastoDetail(gastoId))
                }
            )
        }

        composable<Screen.GastoDetail> {
            GastoDetailScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}