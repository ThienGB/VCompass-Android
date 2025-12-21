package com.example.vcompass.screen.accommodation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.rounded.OpenInFull
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.text.CoreText

@Composable
fun LocationCard() {
    var selectedLocationTab by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .padding(MyDimen.p16)
            .fillMaxWidth()
            .background(MyColor.White, RoundedCornerShape(MyDimen.p12))
            .border(MyDimen.p1, MyColor.GrayEEE, RoundedCornerShape(MyDimen.p12))
    ) {
        CoreText(
            text = "Vị Trí",
            style = CoreTypography.displayLarge,
            modifier = Modifier.padding(MyDimen.p16)
        )
        Box {
            CoreImage(
                source = CoreImageSource.Drawable(R.drawable.img_other_activity),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MyDimen.p120)
            )
            CoreIcon(
                imageVector = Icons.Rounded.OpenInFull,
                iconModifier = Modifier.size(MyDimen.p20),
                boxModifier = Modifier
                    .padding(MyDimen.p8)
                    .align(Alignment.BottomEnd)
                    .background(MyColor.White, CircleShape)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MyDimen.p16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoreText(
                text = "11 Ng. 343 An Duong Vuong, Phu Thuong, Tay Ho, Ha Noi 100000, Vietnam, Tây Hồ, Hà Nội",
                style = CoreTypography.labelSmall,
                modifier = Modifier.weight(1f)
            )
            CoreIcon(
                imageVector = Icons.Default.ContentCopy,
                tintColor = MyColor.Primary,
                iconModifier = Modifier.size(MyDimen.p20)
            )
        }

        Column(Modifier.padding(horizontal = MyDimen.p16)) {
            LocationTabRow(selectedLocationTab) { selectedLocationTab = it }
            if (selectedLocationTab == 0) {
                TransportList()
            } else {

            }
        }
    }
}

@Composable
fun LocationTabRow(
    selected: Int,
    onChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.padding(bottom = MyDimen.p8),
        horizontalArrangement = Arrangement.spacedBy(MyDimen.p8)
    ) {
        LocationTab("Giao thông", selected == 0) { onChange(0) }
        LocationTab("Điểm nổi bật", selected == 1) { onChange(1) }
    }
}

@Composable
private fun LocationTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    CoreText(
        text = text,
        modifier = Modifier
            .background(
                if (selected) MyColor.Black else MyColor.GrayF5,
                RoundedCornerShape(MyDimen.p8)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = MyDimen.p16, vertical = MyDimen.p8),
        style = CoreTypography.labelMedium,
        color = if (selected) MyColor.White else MyColor.TextColorPrimary
    )
}

@Composable
fun TransportList() {
    Column(
        modifier = Modifier.padding(bottom = MyDimen.p16)
    ) {
        TransportItem(
            icon = Icons.Default.Flight,
            name = "Sân bay quốc tế Nội Bài",
            type = "Sân bay",
            distance = "20,4 km"
        )

        TransportItem(
            icon = Icons.Default.Train,
            name = "ga Long Biên",
            type = "Nhà ga",
            distance = "8,3 km"
        )

        TransportItem(
            icon = Icons.Default.Train,
            name = "Sapa Train",
            type = "Nhà ga",
            distance = "9,8 km"
        )
    }
}

@Composable
private fun TransportItem(
    icon: ImageVector,
    name: String,
    type: String,
    distance: String
) {
    Row(
        modifier = Modifier.padding(vertical = MyDimen.p8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoreIcon(
            imageVector = icon,
            iconModifier = Modifier.size(MyDimen.p24)
        )

        Spacer(Modifier.width(MyDimen.p12))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CoreText(text = name, style = CoreTypography.labelMedium)
                Spacer(Modifier.width(MyDimen.p6))
                Box(
                    modifier = Modifier
                        .background(MyColor.GrayEEE, RoundedCornerShape(MyDimen.p4))
                        .padding(horizontal = MyDimen.p6, vertical = MyDimen.p2)
                ) {
                    CoreText(
                        text = type,
                        style = CoreTypography.labelSmall,
                        color = MyColor.Gray666
                    )
                }
            }
        }

        CoreText(
            text = distance,
            style = CoreTypography.labelMedium,
            color = MyColor.Gray666
        )
    }
}
