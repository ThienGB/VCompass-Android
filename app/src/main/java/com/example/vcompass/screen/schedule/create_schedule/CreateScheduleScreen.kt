package com.example.vcompass.screen.schedule.create_schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.button.OutlineButton
import com.example.vcompass.ui.core.button.PrimaryButton
import com.example.vcompass.ui.core.general.BaseView
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.text_field.NormalTextField
import com.example.vcompass.ui.core.title.TitleBarAction
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.add
import com.example.vcompass.util.replace
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.accommodation.AccommodationDetailViewModel
import com.vcompass.presentation.viewmodel.search.MapSearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateScheduleScreen() {
    val viewModel: AccommodationDetailViewModel = koinViewModel()
    val navController = ScreenContext.navController
    val state by viewModel.stateUI.collectAsState()
    BaseView(
        viewModel = viewModel,
        state = state,
        navController = navController,
        topBar = {
            TitleBarAction(text = "Tạo lịch trình")
        }
    ) {
        CreateScheduleForm()
    }
}

@Composable
fun CreateScheduleForm() {
    val navController = ScreenContext.navController
    var name by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }

    val types = listOf("Nghỉ dưỡng", "Khám phá", "Công tác", "Phượt")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MyDimen.p20)
    ) {

        Spacer(Modifier.height(MyDimen.p16))

        CoreText(
            text = "Bắt đầu chuyến đi của bạn",
            style = CoreTypographyBold.titleSmall
        )

        Spacer(Modifier.height(MyDimen.p6))

        CoreText(
            text = "Cùng chúng tôi tạo nên những kỷ niệm đáng nhớ cho hành trình sắp tới.",
            style = CoreTypography.labelLarge,
            color = MyColor.TextColorLight
        )

        Spacer(Modifier.height(MyDimen.p24))

        CoreText(
            text = "Tên lịch trình (Tùy chọn)",
            style = CoreTypographySemiBold.labelMedium
        )

        Spacer(Modifier.height(MyDimen.p8))

        NormalTextField(
            value = name,
            onValueChange = { name = it },
            label = "Ví dụ: Kỳ nghỉ hè tại Đà Nẵng",
            containerColor = MyColor.GrayEEE,
            leadingIcon = Icons.Rounded.EditNote,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(MyDimen.p20))

        Row(
            horizontalArrangement = Arrangement.spacedBy(MyDimen.p12)
        ) {

            DateBox(
                title = "Ngày đi",
                date = startDate,
                onClick = {},
                modifier = Modifier.weight(1f)
            )

            DateBox(
                title = "Ngày về",
                date = endDate,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(MyDimen.p24))

        CoreText(
            text = "Loại hình du lịch",
            style = CoreTypographySemiBold.labelMedium
        )

        Spacer(Modifier.height(MyDimen.p12))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(MyDimen.p12),
            verticalArrangement = Arrangement.spacedBy(MyDimen.p12)
        ) {

            types.forEach { type ->

                val selected = type == selectedType

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(MyDimen.p100))
                        .background(
                            if (selected) MyColor.Primary else MyColor.GrayEEE
                        )
                        .clickable { selectedType = type }
                        .padding(
                            horizontal = MyDimen.p16,
                            vertical = MyDimen.p10
                        )
                ) {

                    CoreIcon(
                        resDrawable = R.drawable.ic_food,
                        tintColor = if (selected) MyColor.White else MyColor.TextColorPrimary
                    )

                    SpaceWidth8()

                    CoreText(
                        text = type,
                        style = CoreTypographySemiBold.bodySmall,
                        color = if (selected) MyColor.White else MyColor.TextColorPrimary
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))

        OutlineButton(
            onClick = { navController.replace(CoreRoute.Schedule.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            CoreIcon(
                imageVector = Icons.Rounded.Edit,
                tintColor = MyColor.Primary
            )
            SpaceWidth8()
            CoreText(
                text = "Tạo thủ công",
                style = CoreTypographySemiBold.labelLarge,
                color = MyColor.Primary
            )
        }

        Spacer(Modifier.height(MyDimen.p12))

        PrimaryButton(
            onClick = { navController.replace(CoreRoute.Schedule.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            CoreIcon(imageVector = Icons.Rounded.AutoAwesome, tintColor = MyColor.White)
            SpaceWidth8()
            CoreText(
                text = "Tạo bằng AI",
                style = CoreTypographySemiBold.labelLarge,
                color = MyColor.White
            )
        }

        Spacer(Modifier.height(MyDimen.p20))
    }
}





