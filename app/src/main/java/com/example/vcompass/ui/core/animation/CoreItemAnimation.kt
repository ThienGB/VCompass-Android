package com.example.vcompass.ui.core.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CoreItemAnimation(
    modifier: Modifier = Modifier,
    mainItem: @Composable () -> Unit,
    switchItem: @Composable () -> Unit,
    isShowSwitchItem: Boolean,
    duration: Int = 400
) {
    AnimatedContent(
        modifier = modifier,
        targetState = isShowSwitchItem,
        transitionSpec = {
            fadeIn(animationSpec = tween(duration / 2)) togetherWith
                    fadeOut(animationSpec = tween(duration / 2))
        },
        label = "CoreItemAnimation"
    ) { state ->
        if (state) switchItem() else mainItem()
    }
}