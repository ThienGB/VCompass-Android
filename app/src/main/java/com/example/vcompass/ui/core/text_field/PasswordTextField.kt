package com.example.vcompass.ui.core.text_field

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.vcompass.R
import com.vcompass.core.compose_view.image.CoreIcon

/**
 * Một Composable wrapper cho CoreTextField,
 * được cấu hình sẵn cho việc nhập Mật khẩu.
 * Tự động bật chế độ ẩn/hiện mật khẩu.
 */
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    label: String = "Mật khẩu",
    errorMessage: String? = stringResource(R.string.lb_field_required),
    imeAction: ImeAction = ImeAction.Done, // Thường là hành động cuối cùng
    enabled: Boolean = true
) {
    CoreTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label, // Giá trị được "wrap"
        leadingIcon = { // Giá trị được "wrap"
            TextFieldIcon(imageVector = Icons.Rounded.Lock)
        },
        // Đây là key: Tự động bật chức năng ẩn/hiện mật khẩu
        isPasswordToggleEnabled = true,
        imeAction = imeAction,
        isError = isError,
        errorMessage = errorMessage,
        enabled = enabled
    )
}