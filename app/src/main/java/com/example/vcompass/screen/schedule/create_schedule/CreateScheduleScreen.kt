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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.CoreTypographyMedium
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.components.bottom_sheet.DateRangePicker
import com.example.vcompass.ui.core.bottom_sheet.BaseBottomSheet
import com.example.vcompass.ui.core.button.OutlineButton
import com.example.vcompass.ui.core.button.PrimaryButton
import com.example.vcompass.ui.core.general.BaseView
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.text_field.NormalTextField
import com.example.vcompass.ui.core.title.TitleBarAction
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.replace
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.accommodation.AccommodationDetailViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
            TitleBarAction(text = stringResource(R.string.lb_create_schedule))
        }
    ) {
        CreateScheduleForm()
    }
}

@Composable
fun CreateScheduleForm() {
    val navController = ScreenContext.navController
    var name by remember { mutableStateOf("") }
    val formatter = remember { DateTimeFormatter.ofPattern("dd-MM-yyyy") }
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTypes by remember { mutableStateOf(listOf<String>()) }
    val showDatePicker = remember { mutableStateOf(false) }

    val types = listOf(
        stringResource(R.string.lb_type_resort),
        stringResource(R.string.lb_type_explore),
        stringResource(R.string.lb_type_business),
        stringResource(R.string.lb_type_backpacking)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MyDimen.p20)
    ) {

        Spacer(Modifier.height(MyDimen.p16))

        CoreText(
            text = stringResource(R.string.lb_start_your_trip),
            style = CoreTypographyBold.titleSmall
        )

        Spacer(Modifier.height(MyDimen.p6))

        CoreText(
            text = stringResource(R.string.lb_create_memories),
            style = CoreTypography.labelLarge,
            color = MyColor.TextColorLight
        )

        Spacer(Modifier.height(MyDimen.p24))

        CoreText(
            text = stringResource(R.string.lb_schedule_name_optional),
            style = CoreTypographySemiBold.labelMedium
        )

        Spacer(Modifier.height(MyDimen.p8))

        NormalTextField(
            value = name,
            onValueChange = { name = it },
            label = stringResource(R.string.hint_schedule_name),
            containerColor = MyColor.GrayEEE,
            leadingIcon = Icons.Rounded.EditNote,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(MyDimen.p20))

        Row(
            horizontalArrangement = Arrangement.spacedBy(MyDimen.p12)
        ) {
            DateBox(
                title = stringResource(R.string.lb_date_go),
                date = startDate?.format(formatter) ?: "--/--",
                onClick = { showDatePicker.value = true },
                modifier = Modifier.weight(1f)
            )

            DateBox(
                title = stringResource(R.string.lb_date_back),
                date = endDate?.format(formatter) ?: "--/--",
                onClick = { showDatePicker.value = true },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(MyDimen.p24))

        CoreText(
            text = stringResource(R.string.lb_travel_type),
            style = CoreTypographySemiBold.labelMedium
        )

        Spacer(Modifier.height(MyDimen.p12))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(MyDimen.p12),
            verticalArrangement = Arrangement.spacedBy(MyDimen.p12)
        ) {

            types.forEach { type ->

                val selected = selectedTypes.contains(type)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(MyDimen.p100))
                        .background(
                            if (selected) MyColor.Primary else MyColor.GrayEEE
                        )
                        .clickable {
                            selectedTypes = if (selected) {
                                selectedTypes - type
                            } else {
                                selectedTypes + type
                            }
                        }
                        .padding(
                            horizontal = MyDimen.p16,
                            vertical = MyDimen.p10
                        )
                ) {
                    CoreText(
                        text = type,
                        style = CoreTypographyMedium.labelMedium,
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
                text = stringResource(R.string.lb_create_manually),
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
                text = stringResource(R.string.lb_create_with_ai),
                style = CoreTypographySemiBold.labelLarge,
                color = MyColor.White
            )
        }

        Spacer(Modifier.height(MyDimen.p20))
        BaseBottomSheet(
            bottomSheetState = showDatePicker,
            onDismiss = { showDatePicker.value = false }
        ) {
            DateRangePicker(
                selectedStartDate = startDate,
                selectedEndDate = endDate,
                onStartDateSelected = { startDate = it },
                onEndDateSelected = { endDate = it }
            )
        }
    }
}

