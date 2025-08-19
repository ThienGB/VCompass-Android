package com.accessed.presentation.state

sealed class ViewUIState {
    object Idle : ViewUIState()
    object Loading : ViewUIState()
    object Success : ViewUIState()
    data class Error(val message: String) : ViewUIState()
}