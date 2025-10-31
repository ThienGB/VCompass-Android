package com.example.vcompass.ui.core.text

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.accessed.core.compose_view.text.CoreText
import com.vcompass.core.resource.MyColor
import com.vcompass.core.typography.CoreTypography

@Composable
fun BorderLessTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    hint: String = "",
    enabled: Boolean = true,
    textStyle: TextStyle = CoreTypography.labelMedium
) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        enabled = enabled,
        textStyle = textStyle,
        placeholder = {
            CoreText(
                text = hint,
                style = textStyle,
                color = MyColor.TextColorGray
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledTextColor = Color.Black
        ),
        modifier = modifier
    )
}