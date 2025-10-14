package com.vcompass.core.compose_view.list


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vcompass.core.resource.MyDimen
import java.util.concurrent.atomic.AtomicLong

enum class ListOrientation { Vertical, Horizontal }

@Composable
fun <T> CoreList(
    modifier: Modifier = Modifier,
    items: List<T>,
    contentType: (T) -> Any? = { null },
    state: LazyListState = rememberLazyListState(),
    orientation: ListOrientation = ListOrientation.Vertical,
    contentPadding: PaddingValues = PaddingValues(MyDimen.zero),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    userScrollEnabled: Boolean = true,
    itemContent: @Composable (T) -> Unit
) {
    val counter = AtomicLong(1L)
    fun nextId(): Long = counter.incrementAndGet()
    when (orientation) {
        ListOrientation.Vertical -> {
            LazyColumn(
                modifier = modifier,
                state = state,
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment,
                userScrollEnabled = userScrollEnabled,
            ) {
                items(
                    count = items.size,
                    key = { nextId() },
                    contentType = { index -> contentType(items[index]) }
                ) { index ->
                    itemContent(items[index])
                }
            }
        }
        ListOrientation.Horizontal -> {
            LazyRow(
                modifier = modifier,
                state = state,
                contentPadding = contentPadding,
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = verticalAlignment,
                userScrollEnabled = userScrollEnabled,
            ) {
                items(
                    count = items.size,
                    key = { nextId() },
                    contentType = { index -> contentType(items[index]) }
                ) { index ->
                    itemContent(items[index])
                }
            }
        }
    }
}
