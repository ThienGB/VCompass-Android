package com.example.vcompass.ui.core.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vcompass.resource.MyDimen

@Composable
fun <T> GridList(
    modifier: Modifier = Modifier,
    items: List<T>,
    key: ((T) -> Any?)? = null,
    contentType: (T) -> Any? = { null },
    columns: GridCells,
    contentPadding: PaddingValues = PaddingValues(MyDimen.zero),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    userScrollEnabled: Boolean = true,
    itemContent: @Composable (T) -> Unit
) {
    LazyVerticalGrid(
        columns = columns,
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        userScrollEnabled = userScrollEnabled
    ) {
        items(
            count = items.size,
            key = if (key != null) {
                { index ->
                    val itemKey = key(items[index])
                    if (itemKey == null || (itemKey is String && itemKey.isBlank())) {
                        index
                    } else {
                        "$itemKey-$index"
                    }
                }
            } else {
                { index -> index }
            },
            contentType = { index -> contentType(items[index]) }
        ) { index ->
            itemContent(items[index])
        }
    }
}
