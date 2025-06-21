package com.l3on1kl.currencyconverter

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.l3on1kl.currencyconverter.navigation.AppNavHost
import com.l3on1kl.currencyconverter.ui.theme.CurrencyConverterTheme
import com.l3on1kl.currencyconverter.ui.theme.OnBackgroundApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT < 35) {
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
        }

        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }

        val customTextSelectionColors = TextSelectionColors(
            handleColor = OnBackgroundApp,
            backgroundColor = OnBackgroundApp.copy(alpha = 0.4f)
        )

        setContent {
            CurrencyConverterTheme {
                CompositionLocalProvider(
                    LocalTextSelectionColors provides customTextSelectionColors
                ) {
                    AppNavHost()
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }
    }
}
