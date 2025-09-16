package com.vcompass.core.compose_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vcompass.core.R
import com.vcompass.core.typography.CoreTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YesNoBottomSheet(
    message: Int = R.string.lb_confirm,
    titleText: Int = R.string.lb_confirm,
    leftButtonText: Int = R.string.btn_no,
    rightButtonText: Int = R.string.btn_yes,
    onYes: () -> Unit = {},
    onNo: () -> Unit = {},
) {
    val bottomSheetState = remember { mutableStateOf(true) }
    AccessedBottomSheet(
        bottomSheetState = bottomSheetState,
        isShowDragHandle = false,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(titleText),
                    fontWeight = FontWeight.SemiBold,
                    style = CoreTypography.displayMedium,
                    fontSize = dimensionResource(id = R.dimen.text_size_small).value.sp,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.content_padding))
                )

                Text(
                    text = stringResource(message),
                    fontWeight = FontWeight.Medium,
                    style = CoreTypography.displayMedium,
                    fontSize = dimensionResource(id = R.dimen.text_size_large).value.sp,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.content_padding))
                        .padding(top = 0.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.content_padding)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.content_padding))
                ) {
                    ButtonNoIcon(
                        modifier = Modifier
                            .weight(1f),
                        text = stringResource(leftButtonText),
                        onClick = {
                            bottomSheetState.value = false
                            onNo()
                        },
                        isFilled = false
                    )
                    ButtonNoIcon(
                        modifier = Modifier
                            .weight(1f),
                        text = stringResource(rightButtonText),
                        onClick = {
                            bottomSheetState.value = false
                            onYes()
                        }
                    )
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun YesNoBottomSheetPreview() {
    YesNoBottomSheet()
}