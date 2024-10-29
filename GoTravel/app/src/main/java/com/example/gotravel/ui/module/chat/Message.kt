package com.example.gotravel.ui.module.chat

data class Message(
    val sender: String,
    val content: String,
    val timestamp: String,
    val isUserMessage: Boolean // true if it's the user's message, false if it's from the other person
)
