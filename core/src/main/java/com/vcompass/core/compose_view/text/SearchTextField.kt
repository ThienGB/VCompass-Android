package com.vcompass.core.compose_view.text

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.accessed.core.compose_view.text.CoreText
import com.vcompass.core.compose_view.image.CoreIcon
import com.vcompass.core.resource.MyColor
import com.vcompass.core.resource.MyDimen
import com.vcompass.core.typography.CoreTypography

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    isAUtoFocus: Boolean = false,
    placeholder: String = "...",
    initialQuery: String = "",
    isEnabled: Boolean = true,
    onImmediateQuery: (String) -> Unit = {}
) {
    var query by rememberSaveable { mutableStateOf(initialQuery) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        if (isAUtoFocus)
            focusRequester.requestFocus()
    }

    LaunchedEffect(query) {
        onImmediateQuery(query)
    }

    BasicTextField(
        value = query,
        onValueChange = { query = it },
        enabled = isEnabled,
        textStyle = CoreTypography.displayMedium,
        singleLine = true,
        modifier = modifier
            .clip(RoundedCornerShape(MyDimen.p12))
            .background(MyColor.GrayF5)
            .height(MyDimen.p40)
            .focusRequester(focusRequester),
        cursorBrush = SolidColor(Color.Black),
        decorationBox = { innerTextField ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                CoreIcon(
                    imageVector = Icons.Default.Search,
                    iconModifier = Modifier.padding(MyDimen.p8),
                )
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (query.isEmpty()) {
                        CoreText(
                            text = placeholder,
                            color = Color.Gray,
                            style = CoreTypography.bodySmall,
                        )
                    }
                    innerTextField()
                }
                if (query.isNotEmpty()) {
                    CoreIcon(
                        imageVector = Icons.Default.Close,
                        iconModifier = Modifier.padding(MyDimen.p10),
                        onClick = {
                            query = ""
                            focusManager.clearFocus()
                        }
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
            }
        )
    )
}
