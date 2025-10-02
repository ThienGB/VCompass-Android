package com.vcompass.core.compose_view.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vcompass.core.dimen.MyDimen

enum class ListOrientation { Vertical, Horizontal }

@Composable
fun <T> CoreList(
    modifier: Modifier = Modifier,
    items: List<T>,
    key: (T) -> Any? = { it.hashCode() },
    contentType: (T) -> Any? = { null },
    orientation: ListOrientation = ListOrientation.Vertical,
    contentPadding: PaddingValues = PaddingValues(MyDimen.zero),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    itemContent: @Composable (T) -> Unit
) {
    fun safeKey(item: T, index: Int): Any {
        return key(item) ?: index
    }
    when (orientation) {
        ListOrientation.Vertical -> {
            LazyColumn(
                modifier = modifier,
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment
            ) {
                items.forEachIndexed { index, item ->
                    item(
                        key = safeKey(item, index),
                        contentType = contentType(item)
                    ) {
                        itemContent(item)
                    }
                }
            }
        }
        ListOrientation.Horizontal -> {
            LazyRow(
                modifier = modifier,
                contentPadding = contentPadding,
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = verticalAlignment
            ) {
                items.forEachIndexed { index, item ->
                    item(
                        key = safeKey(item, index),
                        contentType = contentType(item)
                    ) {
                        itemContent(item)
                    }
                }
            }
        }
    }
}
