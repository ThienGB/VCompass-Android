package com.example.vcompass.ui.core.general

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun LoadingBlockScreen(
    isLoading : Boolean = false,
    isVisibility: Boolean = false
) {
    AnimatedVisibility(
        visible = isVisibility,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
                .pointerInput(Unit) {},
            contentAlignment = Alignment.Center
        ) {
            if (isVisibility && isLoading)
                CircularProgressIndicator()
        }
    }
}