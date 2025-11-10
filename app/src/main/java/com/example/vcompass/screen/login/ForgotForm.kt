package com.example.vcompass.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.vcompass.R
import com.example.vcompass.ui.core.button.PrimaryButton
import com.example.vcompass.ui.core.text.CoreRichText
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.text.RichTextItem
import com.example.vcompass.ui.core.text_field.EmailTextField
import com.example.vcompass.util.add
import com.vcompass.core.compose_view.image.CoreIcon
import com.vcompass.core.compose_view.space.ExpandableSpacer
import com.vcompass.core.compose_view.space.SpaceHeight
import com.vcompass.core.compose_view.space.SpaceHeight8
import com.vcompass.core.compose_view.space.SpaceWidth
import com.vcompass.core.resource.MyColor
import com.vcompass.core.resource.MyDimen
import com.vcompass.core.typography.CoreTypography
import com.vcompass.core.typography.CoreTypographyBold
import com.vcompass.core.typography.CoreTypographySemiBold
import com.vcompass.presentation.util.CoreRoute

@Composable
fun ForgotForm(
    navController: NavController,
    onBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CoreText(
            text = stringResource(R.string.lb_forgot_password),
            style = CoreTypographyBold.headlineMedium,
            color = MyColor.Primary
        )
        SpaceHeight()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(MyDimen.p8))
                .background(MyColor.GrayF5)
                .padding(horizontal = MyDimen.p16, vertical = MyDimen.p10)
        ) {
            CoreIcon(
                imageVector = Icons.Rounded.Info,
                tintColor = MyColor.Gray999
            )
            SpaceWidth()
            CoreText(
                text = stringResource(R.string.lb_forgot_infor),
                style = CoreTypographySemiBold.labelMedium
            )
        }
        SpaceHeight()
        EmailTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(R.string.lb_email)
        )
        SpaceHeight()
        PrimaryButton(
            text = stringResource(R.string.btn_submit),
            enabled = email.isNotBlank(),
            onClick = {
                // viewModel.login(userName, password, hasRemember)
                navController.add(CoreRoute.Home.route)
            }
        )
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