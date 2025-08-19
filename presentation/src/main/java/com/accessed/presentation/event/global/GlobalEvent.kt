package com.accessed.presentation.event.global

sealed class GlobalEvent {
    object Logout : GlobalEvent()
    object ForceUpdate : GlobalEvent()

    object Forbidden : GlobalEvent()
    data class ShowDialog(val message: String) : GlobalEvent()
}