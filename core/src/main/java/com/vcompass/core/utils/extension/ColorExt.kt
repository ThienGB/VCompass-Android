package com.vcompass.core.utils.extension

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun Color.withAlphaIfDisabled(isEnabled: Boolean, disabledAlpha: Float = 0.5f): Color {
    return if (isEnabled) this else this.copy(alpha = disabledAlpha)
}
fun randomColor(): Color {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    val alpha = 0.3f

    return Color(red / 255f, green / 255f, blue / 255f, alpha)
}