package com.example.vcompass.ui.core.list

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow

enum class ScrollDirection { Up, Down, Idle }

@Composable
fun rememberVerticalScrollDirection(
    listState: LazyListState? = null,
    gridState: LazyGridState? = null,
    thresholdPx: Int = 10
): State<ScrollDirection> {
    val direction = rememberSaveable { mutableStateOf(ScrollDirection.Idle) }

    listState?.let {
        LaunchedEffect(listState) {
            var lastIndex = listState.firstVisibleItemIndex
            var lastOffset = listState.firstVisibleItemScrollOffset

            snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
                .collect { (index, offset) ->
                    val dy = when {
                        index == lastIndex -> offset - lastOffset
                        else -> {
                            val base = (index - lastIndex) * thresholdPx * 2
                            base + (offset - lastOffset).coerceIn(-thresholdPx, thresholdPx)
                        }
                    }

                    direction.value = when {
                        dy > thresholdPx -> ScrollDirection.Down
                        dy < -thresholdPx -> ScrollDirection.Up
                        else -> ScrollDirection.Idle
                    }

                    lastIndex = index
                    lastOffset = offset
                }
        }

        LaunchedEffect(listState.isScrollInProgress) {
            if (!listState.isScrollInProgress) direction.value = ScrollDirection.Idle
        }
    }

    gridState?.let {
        LaunchedEffect(gridState) {
            var lastIndex = gridState.firstVisibleItemIndex
            var lastOffset = gridState.firstVisibleItemScrollOffset

            snapshotFlow { gridState.firstVisibleItemIndex to gridState.firstVisibleItemScrollOffset }
                .collect { (index, offset) ->
                    val dy = when {
                        index == lastIndex -> offset - lastOffset
                        else -> {
                            val base = (index - lastIndex) * thresholdPx * 2
                            base + (offset - lastOffset).coerceIn(-thresholdPx, thresholdPx)
                        }
                    }

                    direction.value = when {
                        dy > thresholdPx -> ScrollDirection.Down
                        dy < -thresholdPx -> ScrollDirection.Up
                        else -> ScrollDirection.Idle
                    }

                    lastIndex = index
                    lastOffset = offset
                }
        }

        LaunchedEffect(gridState.isScrollInProgress) {
            if (!gridState.isScrollInProgress) direction.value = ScrollDirection.Idle
        }
    }

    return direction
}
