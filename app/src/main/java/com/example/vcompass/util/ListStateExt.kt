package com.example.vcompass.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.example.vcompass.ui.core.list.ScrollDirection
import com.example.vcompass.ui.core.list.rememberVerticalScrollDirection

@Composable
fun LazyListState.HandleBottomBarOnScroll(
    onScrollUp: () -> Unit,
    onScrollDown: () -> Unit
) {
    val scrollDirection by rememberVerticalScrollDirection(this)

    LaunchedEffect(scrollDirection) {
        when (scrollDirection) {
            ScrollDirection.Up -> onScrollUp()
            ScrollDirection.Down -> onScrollDown()
            ScrollDirection.Idle -> Unit
        }
    }
}
