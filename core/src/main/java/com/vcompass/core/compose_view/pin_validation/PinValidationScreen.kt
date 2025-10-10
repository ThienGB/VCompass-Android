//package com.vcompass.core.compose_view.pin_validation
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.layout.systemBarsPadding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.LocalTextStyle
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.key.Key
//import androidx.compose.ui.input.key.KeyEventType
//import androidx.compose.ui.input.key.key
//import androidx.compose.ui.input.key.onPreviewKeyEvent
//import androidx.compose.ui.input.key.type
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.dimensionResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.vcompass.core.R
//import com.vcompass.core.utils.extension.withAlphaIfDisabled
//import com.vcompass.domain.utils.MyConstants
//import kotlinx.coroutines.delay
//
//@Composable
//fun PinValidationScreen(
//    onContinueClick: () -> Unit = {},
//    onResendClick: () -> Unit = {},
//    onGetHelpClick: () -> Unit = {},
//    onBack: () -> Unit = {},
//    onVerificationCodeChange: (String) -> Unit,
//    errorMessage: Int = -1,
//    isShowCancelButton: Boolean = false
//) {
//    var verificationCode by remember { mutableStateOf("") }
//    val isCodeComplete = verificationCode.length == 6 && verificationCode.all { it.isDigit() }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .statusBarsPadding()
//    ) {
//        if (!isShowCancelButton)
//            _root_ide_package_.com.vcompass.core.compose_view.TitleBarAction(
//                isHaveActionRight = false,
//                onBack = onBack
//            )
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//                .padding(horizontal = dimensionResource(id = R.dimen.content_padding)),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.content_padding)))
//
//            Image(
//                painter = painterResource(id = R.drawable.img_header_verification),
//                contentDescription = null,
//                modifier = Modifier.size(dimensionResource(id = R.dimen.image_header_size))
//            )
//
//            Text(
//                text = stringResource(id = R.string.lb_verification_header),
//                color = colorResource(R.color.textColorDark),
//                fontWeight = FontWeight.W600,
//                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.content_padding)),
//                fontSize = dimensionResource(id = R.dimen.text_size_xxlarge).value.sp
//            )
//
//            Text(
//                text = stringResource(id = R.string.lb_verification_sub_header),
//                fontSize = 15.sp,
//                fontWeight = FontWeight.W500,
//                color = colorResource(R.color.textColorDark),
//                modifier = Modifier.padding(dimensionResource(id = R.dimen.content_padding)),
//                textAlign = TextAlign.Center
//            )
//
//            PinInput(
//                verificationCode = verificationCode,
//                onVerificationCodeChange = {
//                    verificationCode = it
//                    onVerificationCodeChange(it)
//                },
//                errorMessage = errorMessage
//            )
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            Row(
//                modifier = Modifier
//                    .padding(top = dimensionResource(id = R.dimen.vertical_space_xlarge))
//                    .fillMaxWidth(),
//            ) {
//                Text(
//                    text = stringResource(id = R.string.lb_verification_resend_code_question),
//                    fontSize = 15.sp,
//                    fontWeight = FontWeight.W500,
//                    color = colorResource(id = R.color.textColorLight),
//                )
//                Spacer(modifier = Modifier.weight(1f))
//                ResendButton(onResendClick = onResendClick)
//            }
//
//            Row(
//                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
//            ) {
//                if (isShowCancelButton) {
//                    _root_ide_package_.com.vcompass.core.compose_view.ButtonNoIcon(
//                        text = stringResource(id = R.string.btn_cancel),
//                        onClick = onBack,
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(end = dimensionResource(id = R.dimen.content_padding)),
//                        isFilled = false
//                    )
//                }
//                _root_ide_package_.com.vcompass.core.compose_view.ButtonNoIcon(
//                    text = stringResource(id = R.string.btn_continue),
//                    onClick = onContinueClick,
//                    modifier = Modifier.weight(1f),
//                    enabled = isCodeComplete
//                )
//            }
//
//            HavingTroubleSection(onGetHelpClick = onGetHelpClick)
//        }
//    }
//}
//
//@Composable
//fun PinInput(
//    verificationCode: String = "",
//    onVerificationCodeChange: (String) -> Unit = {},
//    errorMessage: Int = -1
//) {
//    val focusRequesters = remember { List(6) { FocusRequester() } }
//    var deletePrevious by remember { mutableStateOf<Int?>(null) }
//
//    LaunchedEffect(deletePrevious) {
//        deletePrevious?.let { index ->
//            focusRequesters[index].requestFocus()
//            val currentCode = verificationCode.padEnd(6, ' ')
//            val updatedCode = buildString {
//                append(currentCode.substring(0, index))
//                append(' ')
//                if (index + 1 < 6) append(currentCode.substring(index + 1))
//            }.trimEnd()
//            onVerificationCodeChange(updatedCode)
//            deletePrevious = null
//        }
//    }
//
//    Text(
//        text = stringResource(id = R.string.lb_verification_input_code_title),
//        color = colorResource(R.color.textColorDark),
//        fontSize = 15.sp,
//        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.vertical_space_xxxlarge)),
//        textAlign = TextAlign.Center
//    )
//
//    Row(
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.padding(top = 10.dp)
//    )
//    {
//        for (i in 0 until 6) {
//            val char = verificationCode.getOrNull(i)?.takeIf { it != ' ' }?.toString() ?: ""
//            BasicTextField(
//                value = char,
//                onValueChange = { newChar ->
//                    if (newChar.length > 1) return@BasicTextField
//                    val currentCode = verificationCode.padEnd(6, ' ')
//                    if (newChar.isNotEmpty() && newChar.isNotBlank() && newChar[0].isDigit()) {
//                        val updatedCode = buildString {
//                            append(currentCode.substring(0, i))
//                            append(newChar[0])
//                            if (i + 1 < 6) append(currentCode.substring(i + 1))
//                        }
//                        onVerificationCodeChange(updatedCode)
//                        if (i < 5) {
//                            focusRequesters[i + 1].requestFocus()
//                        }
//                    } else if (newChar.isEmpty()) {
//                        val updatedCode = buildString {
//                            append(currentCode.substring(0, i))
//                            append(' ')
//                            if (i + 1 < 6) append(currentCode.substring(i + 1))
//                        }
//                        onVerificationCodeChange(updatedCode)
//                    }
//                },
//                modifier = Modifier
//                    .size(45.dp)
//                    .background(
//                        color = colorResource(id = R.color.grayLight),
//                        shape = RoundedCornerShape(4.dp)
//                    )
//                    .padding(vertical = 12.dp)
//                    .focusRequester(focusRequesters[i])
//                    .onPreviewKeyEvent { keyEvent ->
//                        if (keyEvent.type == KeyEventType.KeyUp && keyEvent.key == Key.Backspace && char.isEmpty() && i > 0) {
//                            deletePrevious = i - 1
//                        }
//                        false
//                    },
//                textStyle = LocalTextStyle.current.copy(
//                    color = colorResource(id = R.color.textColorDark),
//                    fontSize = 15.sp,
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center,
//                    lineHeight = 20.sp
//                ),
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//            )
//        }
//    }
//
//    if (errorMessage != -1) {
//        Text(
//            text = stringResource(errorMessage),
//            color = colorResource(id = R.color.textColorRed),
//            fontSize = 15.sp,
//            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.vertical_space_xxxlarge)),
//            textAlign = TextAlign.Center
//        )
//    }
//}
//
//@Composable
//fun ResendButton(
//    onResendClick: () -> Unit
//) {
//    var countdown by remember { mutableStateOf(0) }
//
//    if (countdown > 0) {
//        LaunchedEffect(countdown) {
//            delay(1000L)
//            countdown -= 1
//        }
//    }
//    val enabled = countdown == 0
//    Text(
//        text = stringResource(id = R.string.lb_resend_emnail_text) + if (!enabled) " ($countdown)" else "",
//        color = colorResource(id = R.color.colorSecondaryDark).withAlphaIfDisabled(
//            enabled
//        ),
//        fontSize = 15.sp,
//        fontWeight = FontWeight.W500,
//        modifier = Modifier.clickable(enabled = enabled, onClick = {
//            onResendClick
//            countdown = MyConstants.COUNT_DOWN_TIMER_OTP.toInt() / 1000
//        })
//    )
//}
//
//@Composable
//fun HavingTroubleSection(
//    onGetHelpClick: () -> Unit = {}
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = dimensionResource(id = R.dimen.vertical_space_medium)),
//        horizontalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = stringResource(id = R.string.lb_forgot_password_have_trouble),
//            color = colorResource(id = R.color.textColorLight),
//            fontWeight = FontWeight.W500,
//            fontSize = 15.sp
//        )
//        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.horizontal_space_xlarge)))
//        Text(
//            text = stringResource(id = R.string.lb_forgot_password_button_get_help),
//            color = colorResource(id = R.color.colorSecondaryDark),
//            fontSize = 15.sp,
//            fontWeight = FontWeight.W600,
//            modifier = Modifier.clickable { onGetHelpClick() })
//    }
//}
//
//@Preview(showSystemUi = true)
//@Composable
//fun PinValidationScreenPreview() {
//    var pin by remember { mutableStateOf("") }
//    PinValidationScreen(
//        onContinueClick = {},
//        onResendClick = {},
//        onGetHelpClick = {},
//        onBack = {},
//        onVerificationCodeChange = { pin = it },
//        errorMessage = R.string.lb_invalid_pin,
//        isShowCancelButton = false
//    )
//    Text(pin)
//}
