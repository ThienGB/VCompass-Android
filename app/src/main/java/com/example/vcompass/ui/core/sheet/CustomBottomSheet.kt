package com.example.vcompass.ui.core.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vcompass.helper.BottomSheetType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    bottomSheetState: MutableState<BottomSheetType>,
    onDismiss: () -> Unit = {},
    bottomSheetContents: Map<BottomSheetType, @Composable () -> Unit>
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(scaffoldState.bottomSheetState.currentValue) {
        if (scaffoldState.bottomSheetState.currentValue == SheetValue.PartiallyExpanded) {
             onDismiss()
        }
        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Hidden) {
            onDismiss()
        }
    }

    LaunchedEffect(bottomSheetState.value) {
        coroutineScope.launch {
            if (bottomSheetState.value == BottomSheetType.NONE) {
                try {
                    scaffoldState.bottomSheetState.partialExpand()
                    onDismiss()
                } catch (_: IllegalStateException) { }
            } else {
                scaffoldState.bottomSheetState.expand()
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            if (bottomSheetState.value != BottomSheetType.NONE) {
                bottomSheetContents[bottomSheetState.value]?.invoke()
            }
        },
        sheetPeekHeight = 0.dp,
        sheetContainerColor = Color.White,
        content = {},
        sheetDragHandle = {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(2.dp) // Chiều cao của thanh ngang
                        .width(32.dp) // Chiều rộng của thanh ngang
                        .background(Color.Gray) // Màu của thanh ngang
                        .align(Alignment.Center) // Đảm bảo thanh ngang nằm giữa
                )
            }
        }
    )
}


