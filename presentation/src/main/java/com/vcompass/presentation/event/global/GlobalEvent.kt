package com.vcompass.presentation.event.global

import com.vcompass.presentation.enums.SnackBarDurationType


sealed class GlobalEvent {
    object Idle : GlobalEvent()
    object Logout : GlobalEvent()
    object ForceUpdate : GlobalEvent()

    data class LoadingGlobal(val status: Boolean) : GlobalEvent()

    data class SnackBarInfoGlobal(
        val msg: String,
        val duration: SnackBarDurationType = SnackBarDurationType.SHORT,
        val onClick: (() -> Unit)? = null,
        val isRequired: Boolean = false
    ) : GlobalEvent()

    data class SnackBarWarningGlobal(
        val msg: String,
        val duration: SnackBarDurationType = SnackBarDurationType.SHORT,
        val onClick: (() -> Unit)? = null,
        val isRequired: Boolean = false
    ) : GlobalEvent()

    data class SnackBarErrorGlobal(
        val msg: String,
        val duration: SnackBarDurationType = SnackBarDurationType.SHORT,
        val onClick: (() -> Unit)? = null,
        val isRequired: Boolean = false
    ) : GlobalEvent()

    data class SnackBarSuccessGlobal(
        val msg: String,
        val duration: SnackBarDurationType = SnackBarDurationType.SHORT,
        val onClick: (() -> Unit)? = null,
        val isRequired: Boolean = false
    ) : GlobalEvent()
}