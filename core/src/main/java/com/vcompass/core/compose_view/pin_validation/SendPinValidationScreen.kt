package com.vcompass.core.compose_view.pin_validation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vcompass.core.R

// TempEnums, TODO: need to replace
enum class RequestType {
    FORGOT_USERNAME,
    FORGOT_PASSWORD,
    CHANGE_PASSWORD,
    CHANGE_USERNAME,
    DEACTIVATE_USER,
    GENERATE_NEW_QR_CODE;
}

@Composable
fun SendPinValidationScreen(
    onContinueClick: () -> Unit = {},
    onGetHelpClick: () -> Unit = {},
    onBack: () -> Unit = {},
    onRegisterNow: () -> Unit = {},
    image: Int = R.drawable.img_header_verification,
    title: Int = R.string.lb_verification_header,
    subTitle: Int = R.string.lb_verification_sub_header,
    isForgotRequest: Boolean = false,
    onForgetOptionSelected: (RequestType) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
) {
    var enabled by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        if (isForgotRequest) {
            enabled = false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        _root_ide_package_.com.vcompass.core.compose_view.TitleBarAction(
            isHaveActionRight = false,
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = dimensionResource(id = R.dimen.content_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.content_padding)))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(id = R.dimen.image_header_size))
            )

            Text(
                text = stringResource(title),
                color = colorResource(R.color.textColorDark),
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.content_padding)),
                fontSize = dimensionResource(id = R.dimen.text_size_xxlarge).value.sp
            )

            Text(
                text = stringResource(subTitle),
                fontSize = 15.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(R.color.textColorDark),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.content_padding)),
                textAlign = TextAlign.Center
            )
            if (isForgotRequest) {
                ForgotPasswordSection(
                    onEmailChange = onEmailChange,
                    onOptionSelected = onForgetOptionSelected,
                    onValidationChange = { it -> enabled = it }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (isForgotRequest) {
                DoNotHaveAccountSection(onRegisterNow = onRegisterNow)
            }

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                _root_ide_package_.com.vcompass.core.compose_view.ButtonNoIcon(
                    text = stringResource(id = R.string.btn_continue),
                    onClick = onContinueClick,
                    modifier = Modifier.weight(1f),
                    enabled = enabled
                )
            }
            HavingTroubleSection(onGetHelpClick = onGetHelpClick)
        }
    }
}

@Composable
fun ForgotPasswordSection(
    onEmailChange: (String) -> Unit,
    onOptionSelected: (RequestType) -> Unit,
    onValidationChange: (Boolean) -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf(RequestType.FORGOT_PASSWORD) }
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(top = dimensionResource(id = R.dimen.vertical_space_xlarge))
            .fillMaxWidth()
    ) {

        // TODO: Replace with BaseTextField
        OutlinedTextField(
            value = email,
            onValueChange = {
                // temp logic to check validation TODO: Replace it with func in BaseTextField
                email = it
                onValidationChange(email.length > 6)
                onEmailChange(it)
            },
            label = { Text(stringResource(id = R.string.hint_sign_up_email)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mail_outline_24dp),
                    contentDescription = null
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = false
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            _root_ide_package_.com.vcompass.core.compose_view.RadioButtonWithLabel(
                label = stringResource(id = R.string.lb_forgot_username),
                selected = selectedOption == RequestType.FORGOT_USERNAME,
                onClick = {
                    selectedOption = RequestType.FORGOT_USERNAME
                    onOptionSelected(RequestType.FORGOT_USERNAME)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            _root_ide_package_.com.vcompass.core.compose_view.RadioButtonWithLabel(
                label = stringResource(id = R.string.lb_forgot_password_header),
                selected = selectedOption == RequestType.FORGOT_PASSWORD,
                onClick = {
                    selectedOption = RequestType.FORGOT_PASSWORD
                    onOptionSelected(RequestType.FORGOT_PASSWORD)
                }
            )
        }
    }
}


@Composable
fun DoNotHaveAccountSection(
    onRegisterNow: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.vertical_space_xlarge))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.lb_forgot_password_dont_have_account),
            fontSize = 15.sp,
            fontWeight = FontWeight.W500,
            color = colorResource(id = R.color.textColorLight),
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.horizontal_space_xlarge)))
        Text(
            text = stringResource(id = R.string.btn_register_now),
            color = colorResource(id = R.color.colorSecondaryDark),
            fontSize = 15.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .clickable { onRegisterNow() }
                .padding(dimensionResource(id = R.dimen.vertical_space_small))
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SendPinValidationScreenForgotPreview() {
    SendPinValidationScreen(
        image = R.drawable.img_header_forgot_pasword,
        isForgotRequest = true,
        onEmailChange = {},
        onForgetOptionSelected = {}
    )
}
@Preview(showSystemUi = true)
@Composable
fun SendPinValidationScreenPreview() {
    SendPinValidationScreen(
        image = R.drawable.img_header_reset_password,
        isForgotRequest = false
    )
}
