package com.example.vcompass.ui.core.divider

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen

@Composable
fun SectionDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = MyDimen.p2,
    color: Color = MyColor.Gray999
){
    HorizontalDivider(thickness = thickness, color = color, modifier = modifier)
}
