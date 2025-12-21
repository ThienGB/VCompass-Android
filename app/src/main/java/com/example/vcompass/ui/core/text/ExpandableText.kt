package com.example.vcompass.ui.core.text

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.MyDimen

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    style : TextStyle? = null,
    maxLength: Int? = null,
    maxLines: Int? = null,
    seeMoreText: String = stringResource(R.string.lb_btn_core_see_more),
    seeLessText: String = stringResource(R.string.lb_btn_core_see_less)
) {
    maxLength?.let {
        ExpandableTextByMaxLength(
            modifier = modifier,
            text = text,
            maxLength = it,
            style = style,
            seeMoreText = seeMoreText,
            seeLessText = seeLessText
        )
    } ?: maxLines?.let {
        ExpandableTextByMaxLines(
            modifier = modifier,
            text = text,
            maxLines = it,
            style = style,
            seeMoreText = seeMoreText,
            seeLessText = seeLessText
        )
    }
}

@Composable
fun ExpandableTextByMaxLength(
    modifier: Modifier = Modifier,
    text: String,
    maxLength: Int = 300,
    style: TextStyle? = null,
    seeMoreText: String,
    seeLessText: String
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    val displayText = if (!isExpanded && text.length > maxLength) {
        text.take(maxLength) + "..."
    } else {
        text
    }

    Column(
        modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {

        CoreText(
            text = displayText,
            style = style ?: CoreTypography.labelMedium
        )

        if (text.length > maxLength) {
            CoreText(
                text = if (isExpanded) seeLessText else seeMoreText,
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .padding(top = MyDimen.p8),
                color = MaterialTheme.colorScheme.primary,
                style = style ?: CoreTypography.labelMedium,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun ExpandableTextByMaxLines(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = 3,
    style: TextStyle? = null,
    seeMoreText: String,
    seeLessText: String
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var isOverflowing by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        CoreText(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            style = style ?: CoreTypography.labelMedium,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                isOverflowing = textLayoutResult.hasVisualOverflow
            }
        )
        if (isOverflowing || isExpanded) {
            CoreText(
                text = if (isExpanded) seeLessText else seeMoreText,
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .padding(top = MyDimen.p8),
                color = MaterialTheme.colorScheme.primary,
                style = style ?: CoreTypography.labelMedium,
                textAlign = TextAlign.End
            )
        }
    }
}
