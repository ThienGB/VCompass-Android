package com.example.vcompass.ui.core.button

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle? = null,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    radius: Dp = MyDimen.p8,
    fullWidth: Boolean = true,
    textColorActive: Color? = MyColor.White,
    textColorDeactivate: Color? = null,
    backgroundColorActivate: Color? = null,
    backgroundColorDeactivate: Color? = null,
    content: (@Composable RowScope.() -> Unit)? = null,
) = CoreButton(
    text = text,
    textStyle = textStyle,
    onClick = onClick,
    modifier = modifier,
    style = CoreButtonStyle.Primary,
    enabled = enabled,
    radius = radius,
    fullWidth = fullWidth,
    textColorActive = textColorActive,
    textColorDeactivate = textColorDeactivate,
    backgroundColorActivate = backgroundColorActivate,
    backgroundColorDeactivate = backgroundColorDeactivate,
    content = content
)


