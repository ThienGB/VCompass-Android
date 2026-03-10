package com.example.vcompass.screen.schedule.create_schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.ui.core.text.CoreText

@Composable
fun DateBox(
    title: String,
    date: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {

        CoreText(
            text = title,
            style = CoreTypographySemiBold.labelSmall
        )

        Spacer(Modifier.height(MyDimen.p6))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(MyDimen.p12))
                .background(MyColor.GrayEEE)
                .clickable { onClick() }
                .padding(
                    horizontal = MyDimen.p12,
                    vertical = MyDimen.p12
                )
                .fillMaxWidth()
        ) {

            CoreIcon(
                resDrawable = R.drawable.ic_calendar
            )

            SpaceWidth8()

            CoreText(
                text = date,
                style = CoreTypographySemiBold.bodySmall
            )
        }
    }
}