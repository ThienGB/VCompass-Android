package com.example.vcompass.ui.core.bottombar

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.vcompass.core.dimen.MyDimen
import com.example.vcompass.ui.theme.MyColor

@Composable
fun CenterDockedFab(
    modifier: Modifier,
    barHeight: Dp,
    cutoutRadius: Dp,
    onClick: () -> Unit
) {
    val density = LocalDensity.current

    val offsetY = with(density) {
        val fabR = barHeight / 2
        val cutR = cutoutRadius
        val overlap = (fabR - cutR + MyDimen.p10).toPx() + (barHeight / 2).toPx()
        -(barHeight.toPx() - overlap)
    }

    FloatingActionButton(
        onClick = onClick,
        containerColor = MyColor.Primary,
        shape = RoundedCornerShape(MyDimen.p48),
        modifier = modifier
            .offset(y = Dp(offsetY / density.density)),
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = MyDimen.zero),
    ) {
        Icon(Icons.Filled.Add, contentDescription = "", tint = MyColor.White)
    }
}