package com.vcompass.core.compose_view.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> WrapList(
    modifier: Modifier = Modifier,
    items: List<T>,
    horizontalSpacing: Int = 8,
    verticalSpacing: Int = 8,
    maxItemsInEachRow: Int = Int.MAX_VALUE,
    itemContent: @Composable (T) -> Unit
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing.dp),
        verticalArrangement = Arrangement.spacedBy(verticalSpacing.dp),
        itemVerticalAlignment = Alignment.CenterVertically,
        maxItemsInEachRow = maxItemsInEachRow
    ) {
        items.forEach { item ->
            itemContent(item)
        }
    }
}

