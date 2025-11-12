package com.vcompass.core.compose_view.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.vcompass.resource.MyDimen


@Composable
fun <T> HorizontalList(
    modifier: Modifier = Modifier,
    items: List<T>,
    contentType: (T) -> Any? = { null },
    contentPadding: PaddingValues = PaddingValues(MyDimen.zero),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    userScrollEnabled: Boolean = true,
    itemContent: @Composable (T) -> Unit
) {
    CoreList(
        modifier = modifier,
        items = items,
        contentType = contentType,
        orientation = ListOrientation.Horizontal,
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        userScrollEnabled = userScrollEnabled,
        itemContent = itemContent
    )
}