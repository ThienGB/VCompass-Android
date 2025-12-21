package com.example.vcompass.screen.accommodation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.SmokeFree
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.Window
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.ConfirmationNumber
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Wifi
import androidx.compose.material3.AssistChip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyMedium
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.button.PrimaryButton
import com.example.vcompass.ui.core.divider.ItemDivider
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.text.ExpandableText

@Composable
fun RatingSection() {
    Column(
        modifier = Modifier
            .padding(horizontal = MyDimen.p16)
            .fillMaxWidth()
    ) {
        CoreText(
            text = "11 Ng. 343 An Duong Vuong, Phu Thuong, Tay Ho, Ha Noi 100000, Vietnam, Tây Hồ, Hà Nội",
            style = CoreTypography.labelMedium,
            color = MyColor.TextColorGray
        )

        Spacer(Modifier.height(MyDimen.p6))

        CoreText(
            text = "Xem Trên Bản Đồ",
            style = CoreTypography.labelMedium,
            color = MyColor.Primary,
            textDecoration = TextDecoration.Underline
        )
        SpaceHeight()
        ExpandableText(
            text = "11 Ng. 343 An Duong Vuong, Phu Thuong, Tay Ho, Ha Noi 100000, Vietnam, Tây Hồ, Hà Nội" +
                    "11 Ng. 343 An Duong Vuong, Phu Thuong, Tay Ho, Ha Noi 100000, Vietnam, Tây Hồ, Hà Nội" +
                    "11 Ng. 343 An Duong Vuong, Phu Thuong, Tay Ho, Ha Noi 100000, Vietnam, Tây Hồ, Hà Nội" +
                    "11 Ng. 343 An Duong Vuong, Phu Thuong, Tay Ho, Ha Noi 100000, Vietnam, Tây Hồ, Hà Nội",
            style = CoreTypography.labelSmall.copy(color = MyColor.TextColorGray),
            maxLength = 200
        )
        SpaceHeight()
        ItemDivider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = MyDimen.p12)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        MyColor.Primary,
                        shape = RoundedCornerShape(MyDimen.p6)
                    )
                    .padding(horizontal = MyDimen.p8, vertical = MyDimen.p4)
            ) {
                CoreText(
                    text = "8,3/10",
                    color = MyColor.White,
                    style = CoreTypography.labelMedium
                )
            }

            Spacer(Modifier.width(MyDimen.p8))

            CoreText(
                text = "Rất Tốt",
                style = CoreTypography.labelMedium,
                color = MyColor.TextColorPrimary
            )

            Spacer(Modifier.width(MyDimen.p8))

            CoreText(
                text = "26 đánh giá",
                style = CoreTypography.labelMedium,
                color = MyColor.Primary,
                textDecoration = TextDecoration.Underline
            )
        }
        ItemDivider()
    }
}

@Composable
fun LocationSection() {
    Column(modifier = Modifier.padding(MyDimen.p16)) {

        CoreText(
            text = "Vị Trí",
            style = CoreTypography.displayLarge
        )

        Spacer(Modifier.height(MyDimen.p12))

        LocationItem(
            icon = Icons.Rounded.Place,
            text = "Điểm tham quan: tiNiWorld Lotte Mall ...",
            distance = "1,8 km"
        )

        LocationItem(
            icon = Icons.Default.Attractions,
            text = "Hoạt động: Lotte World Aquarium Hà ...",
            distance = "1,8 km"
        )

        LocationItem(
            icon = Icons.Rounded.Place,
            text = "Điểm tham quan: Hồ Tây",
            distance = "4,7 km"
        )
    }
}

@Composable
fun LocationItem(
    icon: ImageVector,
    text: String,
    distance: String
) {
    Row(
        modifier = Modifier.padding(vertical = MyDimen.p6),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoreIcon(
            imageVector = icon,
            tintColor = MyColor.Gray666,
            iconModifier = Modifier.size(MyDimen.p20)
        )

        Spacer(Modifier.width(MyDimen.p8))

        CoreText(
            text = text,
            modifier = Modifier.weight(1f),
            style = CoreTypography.labelMedium
        )

        CoreText(
            text = distance,
            style = CoreTypography.labelMedium,
            color = MyColor.TextColorLight
        )
    }
}

@Composable
fun PopularAmenities() {
    Column(modifier = Modifier.padding(MyDimen.p16)) {

        CoreText(
            text = "Tiện Nghi Phổ Biến",
            style = CoreTypography.displayLarge
        )

        Spacer(Modifier.height(MyDimen.p12))

        AmenityItem(Icons.Rounded.AccessTime, "Dịch vụ lễ tân 24 giờ")
        AmenityItem(Icons.Rounded.Wifi, "Wi-Fi ở khu vực công cộng")
    }
}

@Composable
fun AmenityItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = MyDimen.p6)
    ) {
        CoreIcon(
            imageVector = icon,
            iconModifier = Modifier.size(MyDimen.p20)
        )
        Spacer(Modifier.width(MyDimen.p8))
        CoreText(text = text, style = CoreTypography.labelMedium)
    }
}

@Composable
fun BottomPriceBar(
    price: String,
    oldPrice: String,
    onChooseRoom: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MyColor.White)
            .padding(MyDimen.p16),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(1f)) {
            CoreText(
                text = price,
                style = CoreTypography.bodyLarge,
                color = MyColor.Primary
            )
            CoreText(
                text = oldPrice,
                style = CoreTypography.labelSmall,
                color = MyColor.Gray999,
                textDecoration = TextDecoration.LineThrough
            )
        }

        PrimaryButton(
            onClick = onChooseRoom,
            text = "Chọn Phòng",
            modifier = Modifier.height(MyDimen.p48)
        )
    }
}

@Composable
fun ChooseRoomHeader() {
    Column(modifier = Modifier.padding(MyDimen.p16)) {
        CoreText(
            text = "Chọn Phòng",
            style = CoreTypography.displayLarge,
            color = MyColor.TextColorPrimary
        )

        Spacer(Modifier.height(MyDimen.p8))

        Row(verticalAlignment = Alignment.CenterVertically) {
            PolicyItem("Chúng tôi khớp giá")
            Spacer(Modifier.width(MyDimen.p12))
            PolicyItem("Bảo Đảm Đổi & Hủy Phòng")
        }
    }
}

@Composable
private fun PolicyItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CoreIcon(
            imageVector = Icons.Default.CheckCircle,
            tintColor = MyColor.Active,
            iconModifier = Modifier.size(MyDimen.p16)
        )
        Spacer(Modifier.width(MyDimen.p4))
        CoreText(
            text = text,
            style = CoreTypography.displaySmall,
            color = MyColor.TextColorGray
        )
    }
}

@Composable
fun ConfirmDateCard() {
    Column(
        modifier = Modifier
            .padding(horizontal = MyDimen.p16)
            .background(
                color = MyColor.White,
                shape = RoundedCornerShape(MyDimen.p12)
            )
            .border(
                width = MyDimen.p1,
                color = MyColor.GrayEEE,
                shape = RoundedCornerShape(MyDimen.p12)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MyColor.GrayF5,
                    RoundedCornerShape(
                        topStart = MyDimen.p12,
                        topEnd = MyDimen.p12
                    )
                )
                .padding(MyDimen.p12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoreIcon(
                imageVector = Icons.Default.Info,
                tintColor = MyColor.DarkOrange,
                iconModifier = Modifier.size(MyDimen.p18)
            )

            Spacer(Modifier.width(MyDimen.p8))

            CoreText(
                text = "Vui lòng xác nhận ngày của bạn",
                style = CoreTypography.labelMedium,
                color = MyColor.DarkOrange
            )
        }

        // Content
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MyDimen.p16),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                CoreText(
                    text = "Nhận phòng và Trả phòng",
                    style = CoreTypography.labelSmall,
                    color = MyColor.Gray666
                )

                Spacer(Modifier.height(MyDimen.p4))

                CoreText(
                    text = "18/12 – 19/12",
                    style = CoreTypography.displayMedium
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                CoreText(
                    text = "1 đêm",
                    style = CoreTypography.labelMedium
                )

                Spacer(Modifier.height(MyDimen.p6))

                Row {
                    GuestIcon(Icons.Default.MeetingRoom, "1")
                    GuestIcon(Icons.Default.Person, "2")
                    GuestIcon(Icons.Default.ChildCare, "0")
                }
            }
        }
        ItemDivider()
        // Actions
        FlowRow(
            modifier = Modifier.padding(MyDimen.p12),
            horizontalArrangement = Arrangement.spacedBy(MyDimen.p8)
        ) {
            AssistChip(label = { CoreText(text = "Xác nhận ngay") }, onClick = {})
            AssistChip(label = { CoreText(text = "1 giường queen") }, onClick = {})
            AssistChip(label = { CoreText(text = "2 giường đơn") }, onClick = {})
        }
    }
}

@Composable
private fun GuestIcon(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = MyDimen.p8)
    ) {
        CoreIcon(imageVector = icon, iconModifier = Modifier.size(MyDimen.p16))
        Spacer(Modifier.width(MyDimen.p2))
        CoreText(text = text, style = CoreTypography.labelSmall)
    }
}

@Composable
fun PromotionBanner() {
    Row(
        modifier = Modifier
            .padding(MyDimen.p16)
            .fillMaxWidth()
            .background(
                MyColor.White,
                RoundedCornerShape(MyDimen.p12)
            )
            .border(
                MyDimen.p1,
                MyColor.GrayEEE,
                RoundedCornerShape(MyDimen.p12)
            )
            .padding(MyDimen.p12),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoreIcon(
            imageVector = Icons.Rounded.ConfirmationNumber,
            tintColor = MyColor.Favorite
        )
        SpaceWidth8()
        Column(modifier = Modifier.weight(1f)) {
            CoreText(
                text = "Giảm đến 612.500đ",
                style = CoreTypography.labelLarge,
                color = MyColor.Favorite
            )
            CoreText(
                text = "Ưu đãi cho nơi nghỉ dưỡng cho thuê nhà",
                style = CoreTypography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MyColor.TextColorGray
            )
        }

        PrimaryButton(
            text = "Nhận Tất Cả",
            modifier = Modifier.height(MyDimen.p32),
            textStyle = CoreTypographyMedium.labelSmall,
            fullWidth = false
        )
    }
}

@Composable
fun RoomCard() {
    Column(
        modifier = Modifier
            .padding(MyDimen.p16)
            .background(
                MyColor.White,
                RoundedCornerShape(MyDimen.p12)
            )
            .border(
                MyDimen.p1,
                MyColor.GrayEEE,
                RoundedCornerShape(MyDimen.p12)
            )
    ) {
        Box {
            CoreImage(
                source = CoreImageSource.Drawable(R.drawable.img_attraction),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MyDimen.p140)
                    .clip(RoundedCornerShape(topStart = MyDimen.p12, topEnd = MyDimen.p12))
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(MyDimen.p8)
                    .background(
                        MyColor.Black.copy(alpha = 0.6f),
                        RoundedCornerShape(MyDimen.p6)
                    )
                    .padding(horizontal = MyDimen.p6, vertical = MyDimen.p2)
            ) {
                CoreIcon(
                    imageVector = Icons.Default.Photo,
                    tintColor = MyColor.White,
                    iconModifier = Modifier.size(MyDimen.p14)
                )
                Spacer(Modifier.width(MyDimen.p4))
                CoreText(
                    text = "18",
                    style = CoreTypography.labelSmall,
                    color = MyColor.White
                )
            }
        }

        // Content
        Column(modifier = Modifier.padding(MyDimen.p16)) {
            CoreText(
                text = "Quadruple Room",
                style = CoreTypography.displayMedium
            )
            Spacer(Modifier.height(MyDimen.p8))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(MyDimen.p8)) {
                RoomFeature(Icons.Default.Bed, "1 giường tầng")
                RoomFeature(Icons.Default.Window, "Cửa sổ nhỏ")
                RoomFeature(Icons.Default.SmokeFree, "Không hút thuốc")
                RoomFeature(Icons.Default.Wifi, "Wi-Fi miễn phí")
            }
            Spacer(Modifier.height(MyDimen.p12))

            CoreText(
                text = "Giá thấp nhất hôm nay",
                style = CoreTypography.labelLarge,
                color = MyColor.Active
            )

            Spacer(Modifier.height(MyDimen.p6))

            RoomPolicy("Phí hủy 176.478đ")
            RoomPolicy("Xác nhận ngay")
            RoomPolicy("Thanh Toán Trước Trực Tuyến")
            RoomPolicy("Giá cho 2 người lớn")

            Spacer(Modifier.height(MyDimen.p8))

            CoreText(
                text = "Xem Chi Tiết",
                style = CoreTypography.labelMedium,
                color = MyColor.Primary
            )
        }
    }
}

@Composable
private fun RoomFeature(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = MyDimen.p4)
    ) {
        CoreIcon(imageVector = icon, iconModifier = Modifier.size(MyDimen.p18))
        Spacer(Modifier.width(MyDimen.p8))
        CoreText(text = text, style = CoreTypography.labelMedium)
    }
}

@Composable
private fun RoomPolicy(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = MyDimen.p2)
    ) {
        CoreIcon(
            imageVector = Icons.Default.Check,
            tintColor = MyColor.Active,
            iconModifier = Modifier.size(MyDimen.p14)
        )
        Spacer(Modifier.width(MyDimen.p6))
        CoreText(text = text, style = CoreTypography.labelSmall)
    }
}




