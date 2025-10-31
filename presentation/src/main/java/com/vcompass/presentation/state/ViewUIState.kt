package com.vcompass.presentation.state

sealed class ViewUIState {
    object Idle : ViewUIState()
    object Loading : ViewUIState()
    object Success : ViewUIState()
    object Error : ViewUIState()
    object ShowSnackBar : ViewUIState()
}