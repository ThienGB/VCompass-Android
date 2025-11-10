package com.example.vcompass.ui.core.button

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.vcompass.core.extensions.conditional
import com.vcompass.core.resource.MyDimen
import com.vcompass.core.typography.CoreTypographySemiBold

@Composable
fun CoreButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle? = null,
    radius: Dp = MyDimen.p8,
    @StringRes resString: Int? = null,
    onClick: () -> Unit = {},
    style: CoreButtonStyle,
    enabled: Boolean = true,
    fullWidth: Boolean = true,
    textColorActive: Color? = null,
    textColorDeactivate: Color? = null,
    backgroundColorActivate: Color? = null,
    backgroundColorDeactivate: Color? = null,
    content: (@Composable RowScope.() -> Unit)? = null,
) {
    val finalTextStyle = textStyle
        ?: CoreTypographySemiBold.labelMedium.copy(color = MaterialTheme.colorScheme.onPrimary)

    val colorDisable = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    val colorTextDisable = MaterialTheme.colorScheme.onSurface

    val finalText = resString?.let { stringResource(it) } ?: text.orEmpty()

    val finalModifier = modifier
        .conditional(fullWidth) { fillMaxWidth() }
        .height(MyDimen.p44)

    val buttonContent: @Composable RowScope.() -> Unit = content ?: {
        if (finalText.isNotEmpty()) {
            Text(text = finalText, style = finalTextStyle)
        }
    }

    when (style) {
        CoreButtonStyle.Primary -> {
            Button(
                onClick = onClick,
                modifier = finalModifier,
                enabled = enabled,
                shape = RoundedCornerShape(radius),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColorActivate ?: MaterialTheme.colorScheme.primary,
                    disabledContainerColor = backgroundColorDeactivate ?: colorDisable,
                    contentColor = textColorActive ?: MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = textColorDeactivate ?: colorTextDisable,
                )
            ) { buttonContent() }
        }

        CoreButtonStyle.Secondary -> {
            Button(
                onClick = onClick,
                modifier = finalModifier,
                enabled = enabled,
                shape = RoundedCornerShape(radius),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColorActivate
                        ?: MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    disabledContainerColor = backgroundColorDeactivate ?: colorDisable,
                    contentColor = textColorActive ?: MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = textColorDeactivate ?: colorTextDisable,
                )
            ) { buttonContent() }
        }

        CoreButtonStyle.Outline -> {
            OutlinedButton(
                onClick = onClick,
                modifier = finalModifier,
                enabled = enabled,
                shape = RoundedCornerShape(radius),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = textColorActive ?: MaterialTheme.colorScheme.outline,
                    disabledContentColor = textColorDeactivate ?: colorDisable,
                ),
                border = BorderStroke(
                    width = MyDimen.p1,
                    color = if (enabled)
                        backgroundColorActivate ?: MaterialTheme.colorScheme.outline
                    else
                        backgroundColorDeactivate ?: colorDisable
                )
            ) { buttonContent() }
        }
    }
}
