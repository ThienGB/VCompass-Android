package com.example.vcompass.ui.core.icon

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vcompass.util.optional
import java.io.File

@Composable
fun CoreImage(
    source: CoreImageSource,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: Painter? = null,
    error: Painter? = null,
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current

    val model = remember(source) {
        when (source) {
            is CoreImageSource.Url -> source.url
            is CoreImageSource.File -> File(source.path)
            is CoreImageSource.Drawable -> source.resId
            is CoreImageSource.Bitmap -> source.bitmap
            is CoreImageSource.Uri -> source.uri
        }
    }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(model)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier.optional(onClick) { clickable { it.invoke() } },
        contentScale = contentScale,
        placeholder = placeholder,
        error = error
    )
}
