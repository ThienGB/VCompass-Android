package com.vcompass.core.compose_view.icon

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import com.vcompass.core.compose_view.image.CoreIcon
import com.vcompass.core.resource.MyDimen

@Composable
fun IconBack(
    size: Dp = MyDimen.p36,
    padding: Dp = MyDimen.p4,
    onClick: (() -> Unit)? = null
) {
    CoreIcon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
        onClick = { onClick },
        iconModifier = Modifier.size(size),
        boxModifier = Modifier.padding(padding).clip(CircleShape),
    )
}