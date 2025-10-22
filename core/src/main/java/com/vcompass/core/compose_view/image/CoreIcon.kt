package com.vcompass.core.compose_view.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.vcompass.core.extensions.optional
import com.vcompass.core.resource.MyColor

@Composable
fun CoreIcon(
    boxModifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    @DrawableRes resDrawable: Int? = null,
    tintColor: Color = MyColor.Gray666,
    onClick: (() -> Unit)? = null
) {
    val clickableModifier = boxModifier.optional(onClick) { clickable { it.invoke() } }

    Box(
        modifier = clickableModifier.then(boxModifier),
        contentAlignment = Alignment.Center
    ) {
        when {
            imageVector != null -> {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = tintColor,
                    modifier = iconModifier
                )
            }

            resDrawable != null -> {
                Icon(
                    painter = painterResource(id = resDrawable),
                    contentDescription = null,
                    tint = tintColor,
                    modifier = iconModifier
                )
            }

            else -> {}
        }
    }
}