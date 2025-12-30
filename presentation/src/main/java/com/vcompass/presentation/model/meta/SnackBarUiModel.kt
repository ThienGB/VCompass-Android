package com.vcompass.presentation.model.meta

import androidx.compose.ui.graphics.Color
import com.vcompass.presentation.enums.SnackBarDurationType
import com.vcompass.presentation.enums.SnackBarType

data class SnackBarUiModel(
    val title: String = "",
    val message: String = "",
    val onDismiss: (() -> Unit)? = null,
    val type: SnackBarType = SnackBarType.INFO,
    val durationType: SnackBarDurationType = SnackBarDurationType.SHORT
)

fun SnackBarUiModel.getBackgroundColor() = when (type) {
    SnackBarType.SUCCESS -> Color(0xFF4CAF50)
    SnackBarType.WARNING -> Color(0xFFDBA500)
    SnackBarType.ERROR -> Color(0xFFF44336)
    SnackBarType.INFO -> Color(0xFFFFFFFF)
}

fun SnackBarUiModel.getTextColor() = when (type) {
    SnackBarType.INFO -> Color(0xFF000000)
    else -> Color(0xFFFFFFFF)
}

fun SnackBarUiModel.getDurationTime() = when (durationType) {
    SnackBarDurationType.SHORT -> 4000L
    SnackBarDurationType.LONG -> 10_000L
    else -> Long.MAX_VALUE
}