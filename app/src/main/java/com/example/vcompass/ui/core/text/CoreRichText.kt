package com.example.vcompass.ui.core.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle

@Composable
fun CoreRichText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    items: List<RichTextItem>,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
) {
    val richText = buildAnnotatedString {
        items.forEach {
            if (length != 0)
                append(" ")
            when (it) {
                is RichTextItem.NormalTextItem -> {
                    withStyle(it.style) {
                        append(it.content)
                    }
                }

                is RichTextItem.ClickableTextItem -> {
                    withLink(
                        link = LinkAnnotation.Clickable(
                            tag = it.content,
                            styles = TextLinkStyles(
                                style = it.style
                            ),
                            linkInteractionListener = { link ->
                                it.onClick()
                            }
                        )
                    ) {
                        withStyle(it.style) {
                            append(it.content)
                        }
                    }
                }

                is RichTextItem.LinkTextItem -> {
                    val style = it.style.copy(color = Color(0xFF2196F3))
                    withLink(
                        link = LinkAnnotation.Clickable(
                            tag = it.content,
                            styles = TextLinkStyles(
                                style = style
                            ),
                            linkInteractionListener = { link ->
                                it.onClick()
                            }
                        )
                    ) {
                        withStyle(style) {
                            append(it.content)
                        }
                    }
                }
            }
        }
    }

    BasicText(
        richText,
        modifier = modifier,
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        style = TextStyle.Default.copy(textAlign = textAlign),
    )
}

sealed class RichTextItem() {
    data class NormalTextItem(
        val content: String,
        val style: SpanStyle,
    ) : RichTextItem()

    data class ClickableTextItem(
        val content: String,
        val style: SpanStyle,
        val onClick: () -> Unit,
    ) : RichTextItem()

    data class LinkTextItem(
        val content: String,
        val style: SpanStyle,
        val onClick: () -> Unit,
    ) : RichTextItem()
}