package com.example.vcompass.screen.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vcompass.R
import com.example.vcompass.ui.core.button.PrimaryButton
import com.example.vcompass.ui.core.check_box.CoreCheckBox
import com.example.vcompass.ui.core.row.RowBetween
import com.example.vcompass.ui.core.text.CoreRichText
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.text.RichTextItem
import com.example.vcompass.ui.core.text_field.EmailTextField
import com.example.vcompass.ui.core.text_field.PasswordTextField
import com.example.vcompass.util.add
import com.example.vcompass.util.goWithState
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.ExpandableSpacer
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceHeight8
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyMedium
import com.example.vcompass.resource.CoreTypographySemiBold
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.login.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginForm(
    viewModel: LoginViewModel = koinViewModel(),
    navController: NavController,
    onRegisterClick: () -> Unit,
    onForgotClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val enableLogin = remember {
        derivedStateOf {
            email.isNotEmpty() && password.isNotEmpty()
        }
    }
    var hasRemember by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.getRememberMe { user, rememberMe ->
            hasRemember = rememberMe
            if (user.isNotEmpty())
                email = user
        }
        viewModel.navigate.collect {
            navController.goWithState(it)
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        EmailTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(R.string.btn_login)
        )
        SpaceHeight8()
        PasswordTextField(
            value = password,
            onValueChange = { password = it },
            label = stringResource(R.string.hint_login_password)
        )
        SpaceHeight8()
        RowBetween(leftItem = {
            CoreCheckBox(
                modifier = Modifier.offset(x = ((-12).dp)),
                text = stringResource(R.string.lb_remember_me),
                checked = hasRemember,
                textColor = MyColor.Primary,
                onClick = { hasRemember = !hasRemember })
        }, rightItem = {
            CoreText(
                resString = R.string.btn_forgot_password,
                color = MyColor.Primary,
                style = CoreTypographyMedium.labelMedium
            ) { onForgotClick() }
        })
        SpaceHeight()
        PrimaryButton(
            text = stringResource(R.string.btn_sign_up),
            enabled = enableLogin.value,
            onClick = {
                // viewModel.login(userName, password, hasRemember)
                navController.add(CoreRoute.Home.route)
            }
        )
        SpaceHeight()
        OrContinueWithDivider()
        SpaceHeight()
        GoogleSignInButton()
        ExpandableSpacer()
        CoreRichText(
            items = listOf(
                RichTextItem.NormalTextItem(
                    content = "${stringResource(R.string.lb_dont_have_account)} ",
                    style = CoreTypography.labelMedium.toSpanStyle()
                ),
                RichTextItem.ClickableTextItem(
                    content = stringResource(R.string.btn_join_now),
                    style = CoreTypographySemiBold.labelMedium.copy(color = MyColor.Primary)
                        .toSpanStyle()
                ) {
                    onRegisterClick()
                },
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OrContinueWithDivider() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            Modifier
                .weight(1f)
                .height(1.dp),
            DividerDefaults.Thickness, Color.Gray.copy(alpha = 0.5f)
        )
        CoreText(
            text = stringResource(R.string.lb_or_continue_with),
            color = Color.Gray,
            style = CoreTypographyMedium.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        HorizontalDivider(
            Modifier
                .weight(1f)
                .height(1.dp),
            DividerDefaults.Thickness, Color.Gray.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(MyDimen.p44),
        shape = RoundedCornerShape(MyDimen.p8),
        border = BorderStroke(MyDimen.p1, MyColor.Gray666),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Icon Google
            CoreIcon(
                resDrawable = R.drawable.ic_google_50dp,
                iconModifier = Modifier.size(MyDimen.p24),
                tintColor = Color.Unspecified
            )
            SpaceWidth8()
            CoreText(
                text = stringResource(R.string.lb_continue_with_google),
                color = MyColor.TextColorPrimary,
                style = CoreTypographyMedium.labelMedium,
                modifier = Modifier.padding(horizontal = MyDimen.p8)
            )
        }
    }
}