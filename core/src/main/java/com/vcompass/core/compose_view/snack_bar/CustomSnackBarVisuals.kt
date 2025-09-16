package com.vcompass.core.compose_view.snack_bar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import com.vcompass.core.enums.SnackBarType

class CustomSnackBarVisuals(
    val type: SnackBarType,
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short
) : SnackbarVisuals