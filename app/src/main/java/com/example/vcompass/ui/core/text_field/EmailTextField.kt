package com.example.vcompass.ui.core.text_field

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.vcompass.R

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    label: String = "Email",
    errorMessage: String? = stringResource(R.string.lb_field_required),
    imeAction: ImeAction = ImeAction.Next,
    enabled: Boolean = true
) {
    CoreTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        leadingIcon = {
            TextFieldIcon(imageVector = Icons.Rounded.Email)
        },
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        isError = isError,
        errorMessage = errorMessage,
        enabled = enabled
    )
}
