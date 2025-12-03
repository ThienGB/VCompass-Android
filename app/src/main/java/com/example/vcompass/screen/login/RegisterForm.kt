package com.example.vcompass.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.vcompass.R
import com.example.vcompass.ui.core.button.PrimaryButton
import com.example.vcompass.ui.core.text.CoreRichText
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.text.RichTextItem
import com.example.vcompass.ui.core.text_field.EmailTextField
import com.example.vcompass.ui.core.text_field.NormalTextField
import com.example.vcompass.ui.core.text_field.PasswordTextField
import com.example.vcompass.util.add
import com.example.vcompass.ui.core.space.ExpandableSpacer
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceHeight8
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.CoreTypographySemiBold
import com.vcompass.presentation.util.CoreRoute

@Composable
fun RegisterForm(
    navController: NavController,
    onBackToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val enableRegister by remember {
        derivedStateOf {
            email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && name.isNotEmpty()
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CoreText(
            text = stringResource(R.string.lb_register_instantly),
            style = CoreTypographyBold.headlineMedium,
            color = MyColor.Primary
        )
        SpaceHeight()
        NormalTextField(
            value = name,
            onValueChange = { name = it },
            label = stringResource(R.string.lb_name),
            leadingIcon = Icons.Rounded.Person
        )
        SpaceHeight8()
        EmailTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(R.string.lb_email)
        )
        SpaceHeight8()
        PasswordTextField(
            value = password,
            onValueChange = { password = it },
            label = stringResource(R.string.hint_login_password)
        )
        SpaceHeight8()
        PasswordTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = stringResource(R.string.hint_confirm_password)
        )
        SpaceHeight()
        PrimaryButton(
            text = stringResource(R.string.btn_sign_up),
            enabled = enableRegister,
            onClick = {
                // viewModel.login(userName, password, hasRemember)
                navController.add(CoreRoute.Home.route)
            }
        )
        SpaceHeight()
        OrContinueWithDivider()
        SpaceHeight()
        GoogleSignInButton({ navController.add(CoreRoute.Home.route) })
        ExpandableSpacer()
        CoreRichText(
            items = listOf(
                RichTextItem.NormalTextItem(
                    content = stringResource(R.string.lb_already_have_account) + " ",
                    style = CoreTypography.labelMedium.toSpanStyle()
                ),
                RichTextItem.ClickableTextItem(
                    content = stringResource(R.string.btn_login),
                    style = CoreTypographySemiBold.labelMedium.copy(color = MyColor.Primary)
                        .toSpanStyle()
                ) {
                    onBackToLogin()
                },
            ),
            textAlign = TextAlign.Center
        )
    }
}
