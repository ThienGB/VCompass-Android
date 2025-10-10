package com.vcompass.core.compose_view.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vcompass.core.resource.MyDimen

@Composable
fun <T> VerticalList(
    modifier: Modifier = Modifier,
    items: List<T>,
    key: (T) -> Any? = { it.hashCode() },
    contentType: (T) -> Any? = { null },
    contentPadding: PaddingValues = PaddingValues(MyDimen.zero),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    itemContent: @Composable (T) -> Unit
) {
    CoreList(
        modifier = modifier,
        items = items,
        key = key,
        contentType = contentType,
        orientation = ListOrientation.Vertical,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        itemContent = itemContent
    )
}

