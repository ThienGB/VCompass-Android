package com.example.vcompass.ui.core.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.text.CoreText

@Composable
fun EmptyListItem(
    title: String = stringResource(R.string.lb_no_result_search),
    iconRes: Int? = R.drawable.img_empty_data
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = MyDimen.p100),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoreText(
            text = title,
            style = CoreTypographySemiBold.labelMedium,
            color = MaterialTheme.colorScheme.onSecondary,
        )
        SpaceHeight()
        if (iconRes != null) {
            CoreImage(
                source = CoreImageSource.Drawable(iconRes),
                modifier = Modifier.width(MyDimen.p200)
                    .height(MyDimen.p100),
                contentScale = ContentScale.Inside
            )
        }
    }
}