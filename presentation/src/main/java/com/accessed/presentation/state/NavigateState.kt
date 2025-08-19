package com.accessed.presentation.state

sealed class NavigateState {
    object None : NavigateState()
    data class AllowNavigate(
        val route: String,
        val isReplace: Boolean = false,
        val isClearStack: Boolean = false,
    ) : NavigateState()
}