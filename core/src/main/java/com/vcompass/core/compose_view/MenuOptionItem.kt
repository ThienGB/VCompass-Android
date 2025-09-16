package com.vcompass.core.compose_view

import android.R.attr.contentDescription
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vcompass.core.R
import com.vcompass.core.typography.CoreTypography

@Composable
fun MenuOptionItem(
    modifier: Modifier = Modifier,
    title: String = "",
    isHaveIcon: Boolean = true,
    icon: Int = R.drawable.ic_category,
    isHaveIndicator: Boolean = false,
    indicatorColor: Color = colorResource(R.color.red),
    indicatorText: String = "",
    onClick: () -> Unit = {},
) {
    val padding = if (isHaveIcon) 16.dp else 0.dp
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        if (isHaveIcon) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(icon)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Inside,
                contentDescription = contentDescription.toString(),
                modifier = Modifier.size(dimensionResource(R.dimen.horizontal_space_xlarge))
            )
        }
        Text(
            text = title,
            fontWeight = W500,
            style = CoreTypography.displayMedium,
            fontSize = 14.sp,
            color = colorResource(R.color.textColorDark),
            modifier = modifier.padding(start = padding).run {
                if (!isHaveIndicator) weight(1f) else this
            }
        )
        if (isHaveIndicator) {
            Text(
                text = indicatorText,
                fontWeight = W400,
                fontSize = 12.sp,
                style = CoreTypography.displayMedium,
                color = indicatorColor,
                modifier = modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MenuOptionItemPreview() {
    Column {
        MenuOptionItem(
            title = "Category",
            icon = R.drawable.ic_category
        )

        MenuOptionItem(
            title = "Notification",
            isHaveIcon = false,
            isHaveIndicator = true,
            indicatorText = "Popular"
        )
        MenuOptionItem(
            title = "Page",
            isHaveIcon = false,
        )
    }
}
