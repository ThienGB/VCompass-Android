package com.example.vcompass.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Transgender
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypographyMedium
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceHeight4
import com.example.vcompass.ui.core.space.SpaceHeight8
import com.example.vcompass.ui.core.text.CoreText
import com.vcompass.core.compose_view.list.HorizontalList


@Composable
fun UserProfileInformationSection() {
    SpaceHeight()
    CoreText(
        text = stringResource(R.string.lb_information),
        style = CoreTypographySemiBold.labelLarge,
        modifier = Modifier.padding(horizontal = MyDimen.p16)
    )
    SpaceHeight()
    Row(verticalAlignment = Alignment.CenterVertically) {
        CoreIcon(
            imageVector = Icons.Rounded.LocationOn,
            iconModifier = Modifier.padding(horizontal = MyDimen.p14)
        )
        CoreText(
            text = "Sống ở Hồ Chí Minh",
            style = CoreTypographyMedium.labelLarge,
            color = MyColor.TextColorPrimary
        )
    }
    SpaceHeight8()
    Row(verticalAlignment = Alignment.CenterVertically) {
        CoreIcon(
            imageVector = Icons.Rounded.Transgender,
            iconModifier = Modifier.padding(horizontal = MyDimen.p14)
        )
        CoreText(
            text = "Giới tính Nam",
            style = CoreTypographyMedium.labelLarge,
            color = MyColor.TextColorPrimary
        )
    }
    SpaceHeight()
    HorizontalList(
        items = listOf(0, 1, 2, 3, 4),
        horizontalArrangement = Arrangement.spacedBy(MyDimen.p16),
        contentPadding = PaddingValues(horizontal = MyDimen.p16)
    ) { index ->
        if (index == 0) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(MyDimen.p120)
                        .width(MyDimen.p80)
                        .clip(RoundedCornerShape(MyDimen.p8))
                        .background(MyColor.Gray666.copy(0.2f))
                ) {
                    CoreIcon(imageVector = Icons.Rounded.Add)
                }
                SpaceHeight4()
                CoreText(
                    text = "Mới",
                    style = CoreTypographyMedium.labelLarge,
                    color = MyColor.TextColorPrimary
                )
            }
        } else {
            CoreImage(
                source = CoreImageSource.Url("https://picsum.photos/200/300"),
                modifier = Modifier
                    .height(MyDimen.p120)
                    .width(MyDimen.p80)
                    .clip(RoundedCornerShape(MyDimen.p8))
            )
        }
    }
}



