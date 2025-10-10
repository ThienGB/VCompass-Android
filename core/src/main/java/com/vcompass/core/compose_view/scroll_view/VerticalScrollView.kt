package com.vcompass.core.compose_view.scroll_view

import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerticalScrollView(
    modifier: Modifier = Modifier,
    state: ScrollState = rememberScrollState(),
    reverse: Boolean = false,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    overscrollEffect: OverscrollEffect? = rememberOverscrollEffect(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    CoreScrollView(
        modifier = modifier,
        orientation = Orientation.Vertical,
        state = state,
        reverse = reverse,
        flingBehavior = flingBehavior,
        overscrollEffect = overscrollEffect,
        contentPadding = contentPadding,
        content = content
    )
}