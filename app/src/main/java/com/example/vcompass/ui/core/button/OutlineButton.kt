package com.example.vcompass.ui.core.button

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.vcompass.core.resource.MyDimen

@Composable
fun OutlineButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle? = null,
    @StringRes resString: Int? = null,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    radius: Dp = MyDimen.p8,
    fullWidth: Boolean = true,
    textColorActive: Color? = null,
    textColorDeactivate: Color? = null,
    backgroundColorActivate: Color? = null,
    backgroundColorDeactivate: Color? = null,
    content: (@Composable RowScope.() -> Unit)? = null,
) = CoreButton(
    text = text,
    resString = resString,
    onClick = onClick,
    textStyle = textStyle,
    modifier = modifier,
    style = CoreButtonStyle.Outline,
    enabled = enabled,
    radius = radius,
    fullWidth = fullWidth,
    textColorActive = textColorActive,
    textColorDeactivate = textColorDeactivate,
    backgroundColorActivate = backgroundColorActivate,
    backgroundColorDeactivate = backgroundColorDeactivate,
    content = content
)


