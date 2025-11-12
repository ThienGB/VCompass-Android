package com.example.vcompass.ui.core.icon

sealed class CoreImageSource {
    data class Url(val url: String) : CoreImageSource()
    data class File(val path: String) : CoreImageSource()
    data class Drawable(val resId: Int) : CoreImageSource()
    data class Uri(val uri: android.net.Uri) : CoreImageSource()
    data class Bitmap(val bitmap: android.graphics.Bitmap) : CoreImageSource()
}
