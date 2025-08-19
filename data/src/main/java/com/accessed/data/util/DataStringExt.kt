package com.accessed.data.util

fun String.actionIfNotNull(onAction: (String) -> Unit) {
    if (this != null) onAction.invoke(this)
}

fun Boolean.actionIfNotNull(onAction: (Boolean) -> Unit) {
    if (this != null) onAction.invoke(this)
}
