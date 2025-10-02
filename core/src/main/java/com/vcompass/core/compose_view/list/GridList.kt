package com.vcompass.core.compose_view.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vcompass.core.dimen.MyDimen

@Composable
fun <T> GridList(
    modifier: Modifier = Modifier,
    items: List<T>,
    key: (T) -> Any? = { it.hashCode() },
    contentType: (T) -> Any? = { null },
    columns: GridCells,
    contentPadding: PaddingValues = PaddingValues(MyDimen.zero),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    itemContent: @Composable (T) -> Unit
) {
    LazyVerticalGrid(
        columns = columns,
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        items(
            items = items,
            key = { key(it) ?: it.hashCode() },
            contentType = contentType
        ) {
            itemContent(it)
        }
    }
}
