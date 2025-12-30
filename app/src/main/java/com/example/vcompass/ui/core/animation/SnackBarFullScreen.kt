package com.example.vcompass.ui.core.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypographyMedium
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.text.CoreText
import com.vcompass.presentation.model.meta.SnackBarUiModel
import com.vcompass.presentation.model.meta.getBackgroundColor
import com.vcompass.presentation.model.meta.getDurationTime
import com.vcompass.presentation.model.meta.getTextColor
import kotlinx.coroutines.delay

@Composable
fun SnackBarFullScreen(
    isVisible: Boolean,
    snackBarUiModel: SnackBarUiModel,
    onDismiss: () -> Unit
) {
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(snackBarUiModel.getDurationTime())
            onDismiss.invoke()
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.35f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    //Do not something
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(MyDimen.p16)
            ) {
                Surface(
                    color = snackBarUiModel.getBackgroundColor(),
                    shape = RoundedCornerShape(MyDimen.p8),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(MyDimen.p16)
                    ) {
                        CoreText(
                            text = snackBarUiModel.message,
                            modifier = Modifier.fillMaxWidth(),
                            style = CoreTypographyMedium.labelMedium,
                            color = snackBarUiModel.getTextColor(),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )

                        CoreText(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.lb_ok),
                            style = CoreTypographySemiBold.labelMedium,
                            color = snackBarUiModel.getTextColor(),
                            textAlign = TextAlign.End
                        ) {
                            onDismiss.invoke()
                        }
                    }
                }
            }
        }
    }
}