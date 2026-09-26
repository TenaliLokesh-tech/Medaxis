package com.medaxis.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val MedaxisBackground = Color(0xFFF7F4EE)
val MedaxisSurface = Color(0xFFFFFFFF)
val MedaxisOnSurface = Color(0xFF1C1B1A)
val MedaxisPrimary = Color(0xFF1F6B5A)
val MedaxisEmergency = Color(0xFFB3261E)

private val LightScheme: ColorScheme = lightColorScheme(
    primary = MedaxisPrimary,
    onPrimary = Color.White,
    background = MedaxisBackground,
    surface = MedaxisSurface,
    onBackground = MedaxisOnSurface,
    onSurface = MedaxisOnSurface,
    error = MedaxisEmergency,
    onError = Color.White
)

val MedaxisShapes = Shapes(
    extraSmall = RoundedCornerShape(16.dp),
    small = RoundedCornerShape(20.dp),
    medium = RoundedCornerShape(24.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

val FloatingCardElevation = 12.dp

@Composable
fun MedaxisTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightScheme,
        shapes = MedaxisShapes,
        content = content
    )
}
