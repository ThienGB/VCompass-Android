package com.vcompass.core.compose_view.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.vcompass.resource.MyDimen


@Composable
fun <T> VerticalList(
    modifier: Modifier = Modifier,
    items: List<T>,
    state: LazyListState = rememberLazyListState(),
    contentType: (T) -> Any? = { null },
    contentPadding: PaddingValues = PaddingValues(MyDimen.zero),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    userScrollEnabled: Boolean = true,
    itemContent: @Composable (T) -> Unit
) {
    CoreList(
        modifier = modifier,
        items = items,
        contentType = contentType,
        state = state,
        orientation = ListOrientation.Vertical,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        userScrollEnabled = userScrollEnabled,
        itemContent = itemContent
    )
}

