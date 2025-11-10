package com.example.vcompass.ui.core.text_field

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.vcompass.R

@Composable
fun NormalTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Email",
    errorMessage: String? = stringResource(R.string.lb_field_required),
    imeAction: ImeAction = ImeAction.Next,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null
) {
    CoreTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        leadingIcon = {
            if (leadingIcon != null)
                TextFieldIcon(imageVector = leadingIcon)
        },
        keyboardType = KeyboardType.Email, // Giá trị được "wrap"
        imeAction = imeAction,
        errorMessage = errorMessage,
        enabled = enabled
    )
}
