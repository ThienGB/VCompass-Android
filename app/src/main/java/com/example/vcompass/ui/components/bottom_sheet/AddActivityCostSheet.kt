package com.example.vcompass.ui.components.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.Money
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vcompass.R
import com.example.vcompass.enum.ActivityTypeEnum
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.bottom_sheet.BaseBottomSheet
import com.example.vcompass.ui.core.divider.ItemDivider
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceHeight8
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.text_field.NoBorderTextField
import com.vcompass.presentation.viewmodel.schedule.ScheduleViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddActivityCostSheet(
    sheetState: MutableState<Boolean>,
    onDismiss: () -> Unit = {}
) {
    val viewModel: ScheduleViewModel = koinViewModel()
    val activity = viewModel.currentActivity
    val type = ActivityTypeEnum.getType(activity.activityType)
    var cost by rememberSaveable { mutableStateOf(activity.cost) }
    var costName by rememberSaveable { mutableStateOf(activity.name) }
    var costDescription by rememberSaveable { mutableStateOf(activity.costDescription) }
    BaseBottomSheet(
        bottomSheetState = sheetState,
        onDismiss = onDismiss,
        isShowDragHandle = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColor.White)
                .padding(horizontal = MyDimen.p16)
        ) {
            SpaceHeight8()
            Row(modifier = Modifier.fillMaxWidth()) {
                CoreText(
                    text = "Chi phí",
                    style = CoreTypographyBold.labelLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 35.dp)
                )
                CoreText(
                    text = stringResource(R.string.btn_done),
                    color = MyColor.TextColorGray,
                    style = CoreTypography.labelLarge,
                    modifier = Modifier.clickable {
                        viewModel.addActivityCost(costName, cost, costDescription)
                        onDismiss()
                    }
                )
            }
            SpaceHeight()
            ItemDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                CoreIcon(
                    imageVector = Icons.AutoMirrored.Rounded.ReceiptLong,
                    iconModifier = Modifier.size(MyDimen.p22)
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    NoBorderTextField(
                        value = costName,
                        onValueChange = { costName = it },
                        modifier = Modifier.width(IntrinsicSize.Min).widthIn(min = MyDimen.p150),
                        textStyle = CoreTypography.labelLarge.copy(
                            textAlign = TextAlign.End
                        ),
                        maxLines = 1
                    )
                }
            }
            ItemDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                CoreIcon(
                    imageVector = Icons.Rounded.Money,
                    iconModifier = Modifier.size(MyDimen.p22)
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    NoBorderTextField(
                        value = cost.toString(),
                        onValueChange = { it.toIntOrNull()?.let { newCost -> cost = newCost } },
                        keyboardType = KeyboardType.Number,
                        textStyle = CoreTypography.labelLarge.copy(textAlign = TextAlign.End),
                        modifier = Modifier.width(IntrinsicSize.Min).widthIn(min = MyDimen.p150),
                    )
                }
            }
            ItemDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                CoreIcon(
                    imageVector = Icons.AutoMirrored.Rounded.Notes,
                    iconModifier = Modifier.size(MyDimen.p22)
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    NoBorderTextField(
                        value = costDescription,
                        onValueChange = { costDescription = it },
                        textStyle = CoreTypography.labelLarge.copy(textAlign = TextAlign.End),
                        imeAction = ImeAction.Done,
                        modifier = Modifier.width(IntrinsicSize.Min).widthIn(min = MyDimen.p150),
                    )
                }
            }
            ItemDivider()
            SpaceHeight(MyDimen.p36)
        }
    }
}