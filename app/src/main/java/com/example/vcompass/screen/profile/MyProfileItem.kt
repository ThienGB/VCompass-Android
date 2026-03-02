package com.example.vcompass.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Transgender
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.vcompass.ui.core.space.SpaceHeight8
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.list.GridList
import com.vcompass.core.compose_view.list.HorizontalList


@Composable
fun UserProfileInformationTab() {
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

@Composable
fun UserProfileImageTab() {
    val items = listOf(0, 1, 2, 3, 4)
    GridList(
        items = items,
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .heightIn(max = MyDimen.maxScrollHeight)
            .padding(horizontal = MyDimen.p8),
        verticalArrangement = Arrangement.spacedBy(MyDimen.p8),
        horizontalArrangement = Arrangement.spacedBy(MyDimen.p8),
        userScrollEnabled = false
    ) {
        CoreImage(
            source = CoreImageSource.Url("https://picsum.photos/200/300"),
            modifier = Modifier
                .fillMaxWidth()
                .height(MyDimen.p160)
                .clip(RoundedCornerShape(MyDimen.p2))
        )
    }
}

@Composable
fun UserProfileVideoTab() {
    val items = listOf(0, 1, 2, 3, 4, 6, 7, 8)
    GridList(
        items = items,
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .heightIn(max = MyDimen.maxScrollHeight)
            .padding(horizontal = MyDimen.p8),
        verticalArrangement = Arrangement.spacedBy(MyDimen.p8),
        horizontalArrangement = Arrangement.spacedBy(MyDimen.p8),
        userScrollEnabled = false
    ) {
        Box(contentAlignment = Alignment.Center) {
            CoreImage(
                source = CoreImageSource.Url("https://picsum.photos/200/300"),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MyDimen.p160)
                    .clip(RoundedCornerShape(MyDimen.p2))
            )
            Box(
                modifier = Modifier
                    .size(MyDimen.p56)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CoreIcon(
                    imageVector = Icons.Rounded.PlayArrow,
                    tintColor = MyColor.White,
                    iconModifier = Modifier.size(MyDimen.p40)
                )
            }
        }
    }
}



