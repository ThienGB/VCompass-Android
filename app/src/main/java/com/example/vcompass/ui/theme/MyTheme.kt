package com.example.vcompass.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.vcompass.core.typography.CoreTypography
import com.vcompass.core.resource.MyColor

val DarkColorScheme = darkColorScheme(
    primary = MyColor.Primary,
    onPrimary = MyColor.White,
    background = MyColor.White,
    primaryContainer = MyColor.White,
    errorContainer = MyColor.White,
    surface = MyColor.White,
    onSurface = MyColor.Black
)

val LightColorScheme = lightColorScheme(
    primary = MyColor.Primary,
    background = MyColor.White,
    primaryContainer = MyColor.White,
    errorContainer = MyColor.White,
    surface = MyColor.White,
    onSurface = MyColor.Black
)

@Composable
fun AccessedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CoreTypography,
        content = content
    )
}