package com.example.vcompass.screen.accommodation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.button.OutlineButton
import com.example.vcompass.ui.core.divider.ItemDivider
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceHeight8
import com.example.vcompass.ui.core.text.CoreText

@Composable
fun ReviewOverviewCard() {
    Column(
        modifier = Modifier
            .padding(MyDimen.p16)
            .fillMaxWidth()
            .background(
                color = MyColor.White,
                shape = RoundedCornerShape(MyDimen.p12)
            )
            .border(
                width = MyDimen.p1,
                color = MyColor.GrayEEE,
                shape = RoundedCornerShape(MyDimen.p12)
            )
            .padding(MyDimen.p16)
    ) {
        Row {
            Column(
                modifier = Modifier.weight(0.4f),
                horizontalAlignment = Alignment.Start
            ) {
                CoreText(
                    text = "Rất Tốt",
                    style = CoreTypography.displayMedium,
                    color = MyColor.Primary
                )

                Spacer(Modifier.height(MyDimen.p8))

                Row(verticalAlignment = Alignment.Bottom) {
                    CoreText(
                        text = "8,3",
                        style = CoreTypography.bodyLarge,
                        color = MyColor.Primary
                    )
                    CoreText(
                        text = "/10",
                        style = CoreTypography.labelSmall,
                        color = MyColor.Gray666,
                        modifier = Modifier.padding(bottom = MyDimen.p4)
                    )
                }

                Spacer(Modifier.height(MyDimen.p4))

                CoreText(
                    text = "Đánh giá đã xác minh",
                    style = CoreTypography.labelSmall,
                    color = MyColor.Gray666
                )
            }

            Spacer(Modifier.width(MyDimen.p16))

            // RIGHT
            Column(modifier = Modifier.weight(0.6f)) {
                RatingRow("Vệ sinh", 8.6f)
                RatingRow("Dịch vụ", 8.3f)
                RatingRow("Trang thiết bị", 8.7f)
                RatingRow("Vị trí", 7.6f)
            }
        }
        ItemDivider()
        SpaceHeight()
        ReviewItem()
    }
}

@Composable
private fun RatingRow(
    label: String,
    score: Float
) {
    Column(modifier = Modifier.padding(vertical = MyDimen.p6)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoreText(
                text = label,
                style = CoreTypography.labelSmall,
                modifier = Modifier.weight(1f)
            )
            CoreText(
                text = score.toString().replace('.', ','),
                style = CoreTypography.labelSmall,
                color = MyColor.Gray666
            )
        }

        Spacer(Modifier.height(MyDimen.p4))

        LinearProgressIndicator(
            progress = score / 10f,
            modifier = Modifier
                .fillMaxWidth()
                .height(MyDimen.p6),
            color = MyColor.Primary,
            trackColor = MyColor.GrayEEE
        )
    }
}

@Composable
fun ReviewItem() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CoreImage(
                source = CoreImageSource.Drawable(R.drawable.img_attraction),
                modifier = Modifier
                    .size(MyDimen.p40)
                    .clip(CircleShape),
            )

            Spacer(Modifier.width(MyDimen.p12))

            Column(modifier = Modifier.weight(1f)) {
                CoreText(
                    text = "Trung Tin Do",
                    style = CoreTypography.labelLarge
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    CoreImage(
                        source = CoreImageSource.Drawable(R.drawable.img_other_activity),
                        modifier = Modifier.size(MyDimen.p16)
                    )
                    Spacer(Modifier.width(MyDimen.p4))
                    CoreText(
                        text = "Việt Nam",
                        style = CoreTypography.labelSmall,
                        color = MyColor.Gray666
                    )
                }
            }

            RatingBadge("10,0/10", "Nổi Bật")
        }

        Spacer(Modifier.height(MyDimen.p12))

        // CONTENT
        OutlineButton(
            text = "Nội dung gốc",
            fullWidth = false,
            modifier = Modifier.height(MyDimen.p36)
        )

        Spacer(Modifier.height(MyDimen.p8))

        CoreText(
            text = "Chúc bạn có kỳ nghỉ vui vẻ tại đây. Khách sạn tốt và sạch sẽ. Nhân viên thân thiện và sẵn sàng giúp đỡ.",
            style = CoreTypography.labelMedium,
            color = MyColor.TextColorPrimary
        )

        Spacer(Modifier.height(MyDimen.p12))

        // PHOTOS
        Row(horizontalArrangement = Arrangement.spacedBy(MyDimen.p8)) {
            ReviewPhoto(R.drawable.img_other_activity)
            ReviewPhoto(R.drawable.img_other_activity)
        }

        Spacer(Modifier.height(MyDimen.p8))

        CoreText(
            text = "Bản dịch do Google cung cấp",
            style = CoreTypography.labelSmall,
            color = MyColor.Gray666
        )
        SpaceHeight8()
        OutlineButton(
            text = "Hiển thị toàn bộ",
            fullWidth = false,
            modifier = Modifier.height(MyDimen.p36)
        )
    }
}

@Composable
private fun RatingBadge(score: String, label: String) {
    Row(
        modifier = Modifier
            .background(
                MyColor.Primary,
                RoundedCornerShape(MyDimen.p6)
            )
            .padding(horizontal = MyDimen.p8, vertical = MyDimen.p4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoreText(
            text = score,
            style = CoreTypography.labelSmall,
            color = MyColor.White
        )
        Spacer(Modifier.width(MyDimen.p6))
        CoreText(
            text = label,
            style = CoreTypography.labelSmall,
            color = MyColor.White
        )
    }
}

@Composable
private fun ReviewPhoto(resId: Int) {
    CoreImage(
        source = CoreImageSource.Drawable(resId),
        contentDescription = null,
        modifier = Modifier
            .size(MyDimen.p80)
            .clip(RoundedCornerShape(MyDimen.p8)),
    )
}






