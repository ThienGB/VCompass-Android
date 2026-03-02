package com.example.vcompass.ui.core.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.resource.MyDimen

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(MyDimen.p56)
            .clip(
                shape = RoundedCornerShape(MyDimen.p56)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        CoreIcon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            iconModifier = Modifier.align(Alignment.Center),
            tintColor = MaterialTheme.colorScheme.inverseSurface
        )
    }
}