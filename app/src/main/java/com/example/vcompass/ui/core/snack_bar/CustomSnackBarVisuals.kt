package com.example.vcompass.ui.core.snack_bar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import com.vcompass.presentation.enums.SnackBarType

class CustomSnackBarVisuals(
    val type: SnackBarType,
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short
) : SnackbarVisuals