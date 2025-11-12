package com.example.vcompass.ui.core.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.R
import com.vcompass.core.compose_view.list.VerticalList
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.resource.CoreTypographySemiBold

@Composable
fun <T>  ListItemTab(
    items: List<T> = emptyList(),
    itemContent: @Composable (T) -> Unit = {}
) {
    Column (modifier = Modifier.background(MyColor.White)) {
        CoreText(
            text = "${items.size} ${stringResource(R.string.lb_total_results).uppercase()}",
            style = CoreTypographySemiBold.displayLarge.copy(fontSize = MyDimen.s15),
            modifier = Modifier.padding(start = MyDimen.p16, bottom = MyDimen.p8)
        )
        VerticalList(
            items = items,
            modifier = Modifier.fillMaxSize()
        ) {
            itemContent(it)
        }
    }
}