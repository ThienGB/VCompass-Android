package com.example.vcompass.ui.core.title

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vcompass.ui.core.text.SearchTextField
import com.example.vcompass.ui.core.icon.IconBack
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen

@Composable
fun TitleSearchBarAction(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isEnabled: Boolean = true,
    onTextChange: (String) -> Unit = {},
    leftItem: (@Composable () -> Unit)? = null,
    rightItem: (@Composable () -> Unit)? = null,
    onBack: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(MyColor.White)
            .height(MyDimen.p56)
            .padding(
                end = if (rightItem != null) MyDimen.zero else MyDimen.p10
            )
    ) {
        if (leftItem != null) {
            leftItem()
        } else {
            IconBack(onClick = onBack)
        }
        SearchTextField(
            modifier = Modifier.weight(1f),
            isAUtoFocus = false,
            placeholder = placeholder,
            initialQuery = "",
            isEnabled = isEnabled,
            onImmediateQuery = onTextChange
        )
        if (rightItem != null) {
            rightItem()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TitleSearchBarActionPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TitleSearchBarAction(
            placeholder = "Search any thing...",
            isEnabled = true
        )
    }
}
