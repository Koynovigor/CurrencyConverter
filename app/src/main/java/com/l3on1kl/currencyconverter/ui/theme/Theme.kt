@file:OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalSharedTransitionApi::class)

package com.l3on1kl.currencyconverter.ui.theme

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,

    secondary = Secondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    onSecondary = OnSecondary,

    tertiary = Tertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    onTertiary = OnTertiary,

    error = Error,
    onError = OnError,
    onErrorContainer = OnErrorContainer,
    errorContainer = ErrorContainer,
    inverseSurface = InverseSurface,
    inverseOnSurface = InverseOnSurface,
    scrim = Scrim,
    surfaceContainerLowest = SurfaceContainerLowest,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainer = SurfaceContainer,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceContainerHighest = SurfaceContainerHighest,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceVariant,
    surface = Surface,
    surfaceBright = SurfaceBright,
    outline = Outline,
    outlineVariant = OutlineVariant,
)

private val AppShapes = Shapes(
    extraSmall = CircleShape,
    small = RoundedCornerShape(20.dp),
    medium = RoundedCornerShape(24.dp),
    large = RoundedCornerShape(30.dp)
)

@Composable
fun CurrencyConverterTheme(content: @Composable () -> Unit) {
    MaterialExpressiveTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        shapes = AppShapes,
        motionScheme = MotionScheme.expressive(),
    ) {
        SharedTransitionLayout {
            CompositionLocalProvider {
                content()
            }
        }
    }
}
