package com.example.vcompass.ui.core.scroll_view

import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CoreScrollView(
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Vertical,
    state: ScrollState = rememberScrollState(),
    reverse: Boolean = false,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    overscrollEffect: OverscrollEffect? = rememberOverscrollEffect(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    val scrollModifier = when (orientation) {
        Orientation.Vertical -> Modifier.verticalScroll(state, reverseScrolling = reverse)
        Orientation.Horizontal -> Modifier.horizontalScroll(state, reverseScrolling = reverse)
    }

    val padded = Modifier.padding(contentPadding)

    val coreScrollable = Modifier
        .scrollable(
            state = rememberScrollableState { delta -> delta },
            orientation = orientation,
            flingBehavior = flingBehavior
        )
        .overscroll(overscrollEffect)

    if (orientation == Orientation.Vertical) {
        Column(
            modifier = modifier
                .then(coreScrollable)
                .then(scrollModifier)
                .then(padded),
            content = content
        )
    } else {
        Row(
            modifier = modifier
                .then(coreScrollable)
                .then(scrollModifier)
                .then(padded),
            content = { Column { content() } }
        )
    }
}
