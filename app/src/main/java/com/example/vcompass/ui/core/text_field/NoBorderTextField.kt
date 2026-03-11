package com.example.vcompass.ui.core.text_field

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.MyColor

@Composable
fun NoBorderTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String? = null,
    errorMessage: String? = stringResource(R.string.lb_field_required),
    imeAction: ImeAction = ImeAction.Next,
    enabled: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    isSingleLine: Boolean = true,
    textStyle: TextStyle = CoreTypography.labelLarge,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    containerColor: Color = MyColor.Transparent,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    CoreTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        maxLines = maxLines,
        minLines = minLines,
        leadingIcon = {
            if (leadingIcon != null)
                TextFieldIcon(imageVector = leadingIcon)
        },
        trailingIcon = {
            if (trailingIcon != null)
                trailingIcon()
        },
        placeholder = placeholder,
        textStyle = textStyle,
        keyboardType = keyboardType,
        imeAction = imeAction,
        errorMessage = errorMessage,
        enabled = enabled,
        containerColor = containerColor,
        isSingleLine = isSingleLine,
    )
}
