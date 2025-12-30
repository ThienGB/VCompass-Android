package com.example.vcompass.ui.core.general

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    statusBarPadding: Boolean = true,
    bottomBarVisible: Boolean = false,
    durationVisible: Int = 500,
    topAppBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val customModifier = if (statusBarPadding)
        modifier
            .windowInsetsPadding(
                WindowInsets.statusBars.union(WindowInsets.displayCutout)
            )
    else modifier

    Scaffold(
        modifier = customModifier,
        topBar = topAppBar,
        contentWindowInsets = WindowInsets(0),
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.Center,
    ) { inner ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            content()
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                AnimatedVisibility(
                    visible = bottomBarVisible,
                    enter = slideInVertically(
                        tween(
                            durationMillis = durationVisible,
                            delayMillis = 0,
                            easing = FastOutSlowInEasing
                        )
                    ) { it } + fadeIn(
                        tween(durationVisible)
                    ),
                    exit = slideOutVertically(
                        tween(
                            durationMillis = durationVisible,
                            delayMillis = 0,
                            easing = FastOutSlowInEasing
                        )
                    ) { it } + fadeOut(
                        tween(durationVisible)
                    )
                ) { bottomBar() }
            }
        }
    }
}