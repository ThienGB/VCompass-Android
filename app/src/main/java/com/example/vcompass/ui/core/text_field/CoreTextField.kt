package com.example.vcompass.ui.core.text_field

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.resource.MyDimen.p12
import com.example.vcompass.resource.MyDimen.p16
import com.example.vcompass.resource.MyDimen.p4
import com.example.vcompass.resource.MyDimen.p48
import com.example.vcompass.resource.MyDimen.p6
import com.example.vcompass.resource.MyDimen.p8
import com.example.vcompass.resource.CoreTypography

@Composable
fun CoreTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    isRequired: Boolean = false,
    requiredErrorMessage: String = "Trường này là bắt buộc",
    customValidator: ((String) -> String?)? = null,
    isPasswordToggleEnabled: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    isSingleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    enabled: Boolean = true,
    textStyle: TextStyle = CoreTypography.labelLarge,
    errorColor: Color = Color.Red,
    containerColor: Color = MyColor.GrayF5,
    normalColor: Color = MyColor.TextColorPrimary,
) {
    var isFocused by rememberSaveable { mutableStateOf(false) }
    var hasBlurred by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var internalError by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(isFocused, value) {
        if (!isFocused && hasBlurred) {
            internalError = when {
                isRequired && value.isBlank() -> requiredErrorMessage
                customValidator != null && value.isNotBlank() -> customValidator(value)
                else -> null
            }
        }
    }

    val shouldFloatLabel by remember(isFocused, value) {
        derivedStateOf { isFocused || value.isNotEmpty() }
    }

    val finalVisualTransformation = when {
        isPasswordToggleEnabled -> if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        else -> visualTransformation
    }

    val finalKeyboardType = when {
        isPasswordToggleEnabled -> KeyboardType.Password
        else -> keyboardType
    }

    val finalIsError = isError || internalError != null
    val finalErrorMessage = errorMessage ?: internalError

    Column(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
                if (internalError != null && newValue.isNotBlank()) {
                    internalError = null
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    val wasFocused = isFocused
                    isFocused = focusState.isFocused

                    if (wasFocused && !focusState.isFocused) {
                        hasBlurred = true
                    }
                },
            singleLine = isSingleLine,
            maxLines = maxLines,
            minLines = minLines,
            enabled = enabled,
            readOnly = !enabled,
            interactionSource = remember { MutableInteractionSource() },
            keyboardActions = KeyboardActions.Default,
            keyboardOptions = KeyboardOptions(
                keyboardType = finalKeyboardType,
                imeAction = imeAction
            ),
            textStyle = textStyle,
            visualTransformation = finalVisualTransformation,
            cursorBrush = SolidColor(Color(0xFF666666)),
            decorationBox = { innerTextField ->
                TextFieldDecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    label = label,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    textStyle = textStyle,
                    isError = finalIsError,
                    isRequired = isRequired,
                    shouldFloatLabel = shouldFloatLabel,
                    isFocused = isFocused,
                    isPasswordToggleEnabled = isPasswordToggleEnabled,
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
                    errorColor = errorColor,
                    containerColor = containerColor,
                    normalColor = if (enabled) normalColor else normalColor.copy(alpha = 0.5f),
                )
            }
        )

        if (finalIsError && finalErrorMessage != null) {
            Text(
                text = finalErrorMessage,
                style = CoreTypography.labelSmall,
                color = errorColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = p16, top = p4)
            )
        }
    }
}

@Composable
private fun TextFieldDecorationBox(
    value: String,
    innerTextField: @Composable () -> Unit,
    label: String,
    placeholder: String?,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    textStyle: TextStyle,
    isError: Boolean,
    isRequired: Boolean,
    shouldFloatLabel: Boolean,
    isFocused: Boolean,
    isPasswordToggleEnabled: Boolean,
    passwordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit,
    containerColor: Color,
    errorColor: Color,
    normalColor: Color,
) {
    val labelScale by animateFloatAsState(
        targetValue = if (shouldFloatLabel) 0.8f else 1f,
        animationSpec = tween(200, easing = FastOutSlowInEasing),
        label = "LabelScale"
    )

    val labelOffsetX by animateDpAsState(
        targetValue = when {
            shouldFloatLabel -> p12
            leadingIcon != null -> p48
            else -> p12
        },
        animationSpec = tween(200, easing = FastOutSlowInEasing),
        label = "LabelOffsetX"
    )

    val labelOffsetY by animateDpAsState(
        targetValue = if (shouldFloatLabel) (-8).dp else MyDimen.p15,
        animationSpec = tween(200, easing = FastOutSlowInEasing),
        label = "LabelOffsetY"
    )

    Box {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(p6),
            colors = CardDefaults.outlinedCardColors(containerColor = containerColor)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = p16)
                    .heightIn(min = p48)
            ) {
                if (leadingIcon != null) {
                    Box(modifier = Modifier.padding(end = p16)) {
                        leadingIcon()
                    }
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                    if (!shouldFloatLabel && value.isEmpty() && placeholder != null) {
                        CoreText(
                            text = placeholder,
                            style = textStyle,
                            color = normalColor.copy(alpha = 0.6f)
                        )
                    }
                }
                when {
                    isPasswordToggleEnabled -> {
                        CoreIcon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            tintColor = MyColor.Black.copy(0.3f),
                            boxModifier = Modifier.clip(CircleShape),
                            iconModifier = Modifier.size(MyDimen.p22),
                            onClick = onPasswordVisibilityToggle
                        )
                    }

                    isError -> {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                            modifier = Modifier.padding(start = p8),
                            tint = errorColor
                        )
                    }

                    trailingIcon != null -> {
                        Box(modifier = Modifier.padding(start = p8)) {
                            trailingIcon()
                        }
                    }
                }
            }
        }
        if (label.isNotBlank()) {
            Box(
                modifier = Modifier
                    .offset(x = labelOffsetX, y = labelOffsetY)
                    .background(
                        color = containerColor,
                        shape = RoundedCornerShape(p4)
                    )
                    .padding(horizontal = p4)
            ) {
                Row {
                    CoreText(
                        text = label,
                        style = textStyle.copy(fontSize = (textStyle.fontSize.value * labelScale).sp),
                        color = normalColor.copy(alpha = 0.6f)
                    )
                    if (isRequired) {
                        CoreText(
                            text = " *",
                            style = textStyle.copy(fontSize = (textStyle.fontSize.value * labelScale).sp),
                            color = errorColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextFieldIcon(
    imageVector: ImageVector? = null,
    resDrawable: Int? = null,
    tintColor: Color = MyColor.Black.copy(0.3f),
    size: Dp = MyDimen.p22
) {
    CoreIcon(
        imageVector = imageVector,
        resDrawable = resDrawable,
        tintColor = tintColor,
        iconModifier = Modifier.size(size)
    )
}