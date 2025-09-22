package com.example.vcompass.ui.core.bottombar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.vcompass.core.dimen.MyDimen

@Composable
fun NavButton(
    modifier: Modifier = Modifier,
    item: BottomBarUIModel,
    height: Dp,
    selected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color,
    contentColor: Color
) {
    val color = if (selected) selectedColor else contentColor
    Column(
        modifier = modifier
            .height(height)
            .clip(CircleShape)
            .padding(MyDimen.p4)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(if (selected) item.selectedIcon else item.unselectedIcon),
            contentDescription = item.label,
            modifier = Modifier.size(MyDimen.p18),
            tint = color
        )
    }
}