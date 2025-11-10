package com.example.vcompass.ui.core.row

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vcompass.core.compose_view.space.ExpandableSpacer

@Composable
fun RowBetween(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    leftItem: @Composable RowScope.() -> Unit,
    rightItem: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = verticalAlignment
    ) {
        leftItem()
        ExpandableSpacer()
        rightItem()
    }
}
