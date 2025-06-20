package com.l3on1kl.currencyconverter.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Gradient1 = Color(0xFFB11BA0)
val Gradient2 = Color(0xFFCD3944)
val Gradient3 = Color(0xFFDCAB6f)

val GradientMain = Brush.horizontalGradient(
    colors = listOf(
        Gradient1,
        Gradient2,
        Gradient3
    )
)

val BackgroundApp = Color(0xFF180A48)
val OnBackgroundApp = Color(0xFFE6DFBE)
val BorderCard = Color(0xFF706C54)

// Primary
val Primary = Color(0xFF34A853)
val OnPrimary = Color.White
val PrimaryContainer = Color(0xFF1F8F44)
val OnPrimaryContainer = Color.White

// Secondary
val Secondary = Color(0xFFC8E6C9)
val OnSecondary = Color(0xFF1B5E20)
val SecondaryContainer = Color(0xFFB2DFDB)
val OnSecondaryContainer = Color(0xFF004D40)

// Tertiary
val Tertiary = Color(0xFF202124)
val OnTertiary = Color.White
val TertiaryContainer = Color(0xFF37474F)
val OnTertiaryContainer = Color.White

// Error colors
val Error = Color(0xFFBA1A1A) // Red
val OnError = Color(0xFFFFFFFF) // White
val ErrorContainer = Color(0xFFFFDAD6) // Light Red
val OnErrorContainer = Color(0xFF93000A) // Dark Red

// Surface colors
val Surface = Color(0xFFF1F3F4) // White
val SurfaceBright = Color(0xFFE6F4EA) // Light Green
val InverseSurface = Color(0xFF313030) // Dark Gray
val InverseOnSurface = Color(0xFFF3F0EF) // Light gray
val SurfaceContainerLowest = Color(0xFFFFFFFF) // Off White
val SurfaceContainerLow = Color(0xFFEEF0F2) // Light gray
val SurfaceContainer = Color(0xFFE8EBED) // Gray
val SurfaceContainerHigh = Color(0xFFE5E9EB)
val SurfaceContainerHighest = Color(0xFFE5E9EB)

// Others colors
val OnSurface = Color(0xFF202124) // Black
val OnSurfaceVariant = Color(0xFF434846) // Dark Gray
val Outline = Color(0xFF202124) // Dark Gray
val OutlineVariant = Color(0xFF313030) // Light Dark Gray
val Scrim = Color(0xFF000000) // Black