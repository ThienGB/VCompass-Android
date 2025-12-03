package com.example.vcompass.ui.core.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.util.optional

@Composable
fun MoreOptionIcon(
    iconSize: Dp = MyDimen.p24,
    contentPadding: Dp = MyDimen.p6,
    onClick: (() -> Unit)? = null
) {
    CoreIcon(
        imageVector = Icons.Default.MoreVert,
        iconModifier = Modifier.size(iconSize),
        boxModifier = Modifier
            .optional(onClick) { clickable { it.invoke() } }
            .padding(contentPadding)
            .clip(CircleShape)
    )
}