package com.example.vcompass.ui.components.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.util.AppConstants.SCHEDULE_TEMP_IMAGE_URL
import com.example.vcompass.util.optional

@Composable
fun UserAvatar(
    pathUrl: String?,
    size: Dp = MyDimen.p36,
    isOnline: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Box {
        CoreImage(
            source = CoreImageSource.Url(pathUrl ?: SCHEDULE_TEMP_IMAGE_URL),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size)
                .optional(onClick != null) {
                    clickable { onClick?.invoke() }
                }
                .clip(CircleShape)
                .border(
                    width = MyDimen.p1,
                    color = MyColor.GrayF5,
                    shape = CircleShape
                )
        )
        if (isOnline) {
            Box(
                modifier = Modifier
                    .size(MyDimen.p10)
                    .background(
                        color = MyColor.Active,
                        shape = CircleShape
                    )
                    .border(
                        width = MyDimen.p1,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .align(Alignment.BottomEnd)
            )
        }
    }
}