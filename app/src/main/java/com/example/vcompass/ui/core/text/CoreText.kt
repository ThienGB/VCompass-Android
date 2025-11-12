package com.example.vcompass.ui.core.text

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.vcompass.core.extensions.conditional
import com.vcompass.core.extensions.optional
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.CoreTypography

@Composable
fun CoreText(
    modifier: Modifier = Modifier,
    text: String? = null,
    @StringRes resString: Int? = null,
    isFullWidth: Boolean = false,
    style: TextStyle = CoreTypography.labelMedium,
    color: Color = MyColor.TextColorPrimary,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
) {
    val finalText = resString?.let {
        stringResource(it)
    } ?: text ?: ""

    val newModifier = modifier
        .conditional(isFullWidth) { fillMaxWidth() }
        .optional(onClick) { clickable { it.invoke() } }

    Text(
        text = finalText,
        style = style,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        minLines = minLines,
        overflow = overflow,
        modifier = newModifier,
        lineHeight = lineHeight,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        onTextLayout = onTextLayout
    )
}
