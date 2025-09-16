package com.vcompass.core.compose_view

import android.R.attr.contentDescription
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vcompass.core.R
import com.vcompass.core.typography.CoreTypography

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String = "",
    textColor: Int = R.color.textColorDark,
    isHaveRightAction: Boolean = true,
    isHaveSwitch: Boolean = false,
    isChecked: Boolean = true,
    fontWeight: FontWeight = W600,
    icon: Int = R.drawable.ic_arrow_right_grey_24dp,
    onCheckedChange: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(enabled = !isHaveSwitch, onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontWeight = fontWeight,
            fontSize = 15.sp,
            style = CoreTypography.displayMedium,
            color = colorResource(textColor),
            modifier = modifier
                .weight(1f)
                .padding(end = 16.dp)
        )
        if (isHaveRightAction){
            if (isHaveSwitch) {
                SwitchButton(isChecked = isChecked, onCheckedChange = onCheckedChange)
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(icon)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Inside,
                    contentDescription = contentDescription.toString(),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(dimensionResource(R.dimen.btn_border_circle_size))
                        .padding(4.dp)
                )
            }
        }

    }
    HorizontalDivider(
        thickness = 1.dp,
        color = colorResource(R.color.listItemSeparate),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Preview(showSystemUi = true)
@Composable
fun SettingItemPreview() {
    Column {
        var isChecked by remember { mutableStateOf(true) }
        SettingItem(
            title = "Notification",
            isHaveSwitch = true,
            isChecked = isChecked,
            onCheckedChange = {
                isChecked = it
            }
        )

        SettingItem(
            title = "Setting",
            onClick = {}
        )

        SettingItem(
            title = "Category",
            fontWeight = FontWeight.Normal,
            icon = R.drawable.ic_category,
            onClick = {}
        )
    }
}
