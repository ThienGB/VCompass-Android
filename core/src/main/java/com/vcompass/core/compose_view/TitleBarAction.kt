package com.vcompass.core.compose_view

import android.R.attr.contentDescription
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
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
fun TitleBarAction(
    modifier: Modifier = Modifier,
    text: String = "",
    isHaveActionRight: Boolean = true,
    isHaveIcon: Boolean = true,
    actionRightText: String = "",
    icon: Int = R.drawable.ic_category,
    isEnabled: Boolean = true,
    onBack: () -> Unit = {},
    onActionRightClick: () -> Unit = {}
) {
    val padding = if (isHaveActionRight) 0.dp else dimensionResource(R.dimen.actionbar_height)
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
        Text(
            text = text,
            fontWeight = W600,
            style = CoreTypography.displayMedium,
            fontSize = 16.sp,
            color = colorResource(R.color.textColorDark),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = modifier
                .weight(1f)
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
                    fontWeight = W500,
                    style = CoreTypography.displayMedium,
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
    HorizontalDivider(thickness = 0.5.dp,color = colorResource(R.color.colorPrimaryDark),
        modifier = Modifier.shadow(elevation = 5.dp ,
            ambientColor = colorResource(R.color.colorSelected)))
}

@Preview(showSystemUi = true)
@Composable
fun TitleBarActionPreview() {
    Column {
        TitleBarAction(
            text = "Cancel",
            onBack = { },
            isHaveIcon = false,
            actionRightText = "Next",
            isEnabled = true
        )
        TitleBarAction(
            text = "Cancel",
            onBack = { },
            isHaveIcon = false,
            actionRightText = "Next",
            isEnabled = false
        )
        TitleBarAction(
            text = "Cancel",
            onBack = { },
            icon = R.drawable.ic_category,
            isHaveIcon = true,
            isEnabled = true
        )
        TitleBarAction(
            text = "Cancel",
            onBack = { },
            isHaveActionRight = false
        )
    }
}
