package com.example.vcompass.ui.core.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vcompass.resource.MyColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheet(
    bottomSheetState: MutableState<Boolean>,
    onDismiss: () -> Unit = {},
    isShowDragHandle: Boolean = true,
    skipPartiallyExpanded: Boolean = true,
    isAllowHidden: Boolean = true,
    sheetContent: @Composable () -> Unit
) {
    if (!bottomSheetState.value) return
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded,
        confirmValueChange = { newValue ->
            if (!isAllowHidden) {
                newValue != SheetValue.Hidden
            } else {
                true
            }
        }
    )
    ModalBottomSheet(
        onDismissRequest = {
            bottomSheetState.value = false
            onDismiss()
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        containerColor = Color.White,
        dragHandle = {
            val height = if (isShowDragHandle) 25.dp else 0.dp
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(99.dp))
                        .height(4.dp)
                        .width(28.dp)
                        .background(MyColor.Gray666)
                        .align(Alignment.Center)
                )
            }
        },
        scrimColor = Color.Black.copy(alpha = 0.5f)
    ) {
        sheetContent()
    }
}
