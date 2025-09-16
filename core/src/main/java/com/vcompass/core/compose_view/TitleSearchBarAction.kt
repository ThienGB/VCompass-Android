package com.vcompass.core.compose_view

import android.R.attr.contentDescription
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vcompass.core.R
import com.vcompass.core.typography.CoreTypography

@Composable
fun TitleSearchBarAction(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isHaveActionRight: Boolean = true,
    isHaveIcon: Boolean = true,
    actionRightText: String = "",
    icon: Int = R.drawable.ic_category,
    isEnabled: Boolean = true,
    onTextChange: (String) -> Unit = {},
    onBack: () -> Unit = {},
    onActionRightClick: () -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    val padding = if (isHaveActionRight) 0.dp else dimensionResource(R.dimen.shadow_top_height)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(dimensionResource(R.dimen.actionbar_height))
            .padding(end = padding)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.ic_arrow_left_grey)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Inside,
            contentDescription = contentDescription.toString(),
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onBack() }
                .size(dimensionResource(R.dimen.actionbar_height))
                .padding(16.dp)
        )
        BasicTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onTextChange(it)
            },
            enabled = isEnabled,
            textStyle = CoreTypography.displayMedium,
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(R.color.bg_search_box))
                .height(40.dp),
            cursorBrush = SolidColor(Color.Black),
            decorationBox = { innerTextField ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.ic_search_hint_24dp)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Inside,
                        contentDescription = contentDescription.toString(),
                        modifier = Modifier.padding(5.dp)
                    )
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (searchText.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color.Gray,
                                style = CoreTypography.displayMedium,
                                fontSize = 15.sp
                            )
                        }
                        innerTextField()
                    }
                    if (searchText.isNotEmpty()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(R.drawable.ic_clear_gray_24dp)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Inside,
                            contentDescription = contentDescription.toString(),
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable {
                                    searchText = ""
                                    onTextChange("")
                                }
                        )
                    }
                }
            }
        )
        if (isHaveActionRight) {
            if (isHaveIcon)
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(icon)
                        .crossfade(true)
                        .build(),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(
                            enabled = isEnabled,
                            onClick = { onActionRightClick() }
                        )
                        .size(dimensionResource(R.dimen.actionbar_height))
                        .padding(16.dp),
                    contentScale = ContentScale.Inside,
                    contentDescription = contentDescription.toString()
                ) else {
                val textColorResource =
                    if (!isEnabled) R.color.stroke_color_grey else R.color.textColorDark
                Text(
                    text = actionRightText,
                    fontSize = 14.sp,
                    color = colorResource(textColorResource),
                    maxLines = 1,
                    style = CoreTypography.displayMedium,
                    fontWeight = W500,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(
                            enabled = isEnabled,
                            onClick = { onActionRightClick() }
                        )
                        .padding(horizontal = 2.dp)
                        .width(dimensionResource(R.dimen._64))
                        .height(dimensionResource(R.dimen.actionbar_height))
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TitleSearchBarActionPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TitleSearchBarAction(
            onBack = { },
            placeholder = "Search any thing...",
            isHaveIcon = false,
            actionRightText = "Next",
            isEnabled = true
        )

        TitleSearchBarAction(
            onBack = { },
            placeholder = "Search any thing...",
            isHaveIcon = false,
            actionRightText = "Next",
            isEnabled = false
        )

        TitleSearchBarAction(
            onBack = { },
            placeholder = "Search any thing...",
            isHaveIcon = true,
            icon = R.drawable.ic_category,
            isEnabled = true
        )

        TitleSearchBarAction(
            onBack = { },
            placeholder = "Search any thing...",
            isHaveActionRight = false
        )

    }
}
