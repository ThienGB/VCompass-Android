package com.example.vcompass.ui.core.divider

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen

enum class DividerOrientation { Horizontal, Vertical }

@Composable
fun ItemDivider(
    modifier: Modifier = Modifier,
    orientation: DividerOrientation = DividerOrientation.Horizontal,
    thickness: Dp = MyDimen.pHalf,
    color: Color = MyColor.GrayEEE
) {
    when (orientation) {
        DividerOrientation.Horizontal -> {
            HorizontalDivider(
                modifier = modifier,
                thickness = thickness,
                color = color
            )
        }
        DividerOrientation.Vertical -> {
            VerticalDivider(
                modifier = modifier,
                thickness = thickness,
                color = color
            )
        }
    }
}