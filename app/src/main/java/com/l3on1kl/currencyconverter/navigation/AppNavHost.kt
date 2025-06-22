package com.l3on1kl.currencyconverter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.l3on1kl.currencyconverter.ui.currencies.CurrenciesScreen
import com.l3on1kl.currencyconverter.ui.exchange.ExchangeScreen
import com.l3on1kl.currencyconverter.ui.profile.ProfileScreen
import com.l3on1kl.currencyconverter.ui.transactions.TransactionsScreen

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

        composable(
            route = "exchange/{to}/{from}/{amount}",
            arguments = listOf(
                navArgument("to") { type = NavType.StringType },
                navArgument("from") { type = NavType.StringType },
                navArgument("amount") { type = NavType.FloatType }
            )
        ) {
            ExchangeScreen(navController = navController)
        }

        composable(Destinations.Transactions) {
            TransactionsScreen(navController = navController)
        }

    }
}
