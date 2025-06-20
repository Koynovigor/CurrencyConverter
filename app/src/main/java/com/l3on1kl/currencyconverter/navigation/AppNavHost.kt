package com.l3on1kl.currencyconverter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.l3on1kl.currencyconverter.ui.currencies.CurrenciesScreen
import com.l3on1kl.currencyconverter.ui.profile.ProfileScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.Currencies
    ) {
        composable(Destinations.Currencies) {
            CurrenciesScreen(navController = navController)
        }
        composable(Destinations.Profile) {
            ProfileScreen(navController = navController)
        }
    }
}
