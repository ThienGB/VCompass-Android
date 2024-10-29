package com.example.gotravel.ui.module.notifications

data class Notification(
    val title: String,
    val content: String,
    val imageUrl: String,
    val time: String,
    val buttonText: String,
    val isRead: Boolean
)
