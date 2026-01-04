package com.example.vcompass.ui.core.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypographyMedium
import com.example.vcompass.ui.core.button.SecondaryButton
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.text.CoreText

@Composable
fun CoreErrorItem(
    modifier: Modifier = Modifier,
    errorText: String? = null,
    retryText: String? = null,
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CoreText(
            text = errorText ?: stringResource(R.string.lb_core_retry),
            style = CoreTypographyMedium.labelMedium
        )
        SpaceHeight()
        SecondaryButton(
            text = retryText ?: stringResource(R.string.lb_btn_core_retry),
            fullWidth = false,
            onClick = {
                onRefresh.invoke()
            }
        )
    }
}