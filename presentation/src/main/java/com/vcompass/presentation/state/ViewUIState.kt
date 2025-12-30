package com.vcompass.presentation.state

sealed class ViewUIState {
    object Idle : ViewUIState()
    data class Loading(val type: LoadingType) : ViewUIState()
    object Success : ViewUIState()
    data class Error(val error: String?) : ViewUIState()
    object ShowSnackBar : ViewUIState()
}