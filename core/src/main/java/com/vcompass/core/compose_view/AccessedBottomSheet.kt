package com.vcompass.core.compose_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vcompass.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessedBottomSheet(
    bottomSheetState: MutableState<Boolean>,
    onDismiss: () -> Unit = {},
    isShowDragHandle: Boolean = true,
    sheetContent: @Composable () -> Unit
) {
    if (!bottomSheetState.value) return
    ModalBottomSheet(
        onDismissRequest = {
            bottomSheetState.value = false
            onDismiss()
        },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
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
                        .background(colorResource(R.color.backgroundDark))
                        .align(Alignment.Center)
                )
            }
        },
        scrimColor = Color.Black.copy(alpha = 0.5f)
    ) {
        sheetContent()
    }
}

@Preview(showSystemUi = true)
@Composable
fun AccessedBottomSheetPreview() {
    val bottomSheetState = remember { mutableStateOf(true) }
    AccessedBottomSheet(
        bottomSheetState = bottomSheetState,
        sheetContent = {
            Column {
                repeat(5) {
                    SettingItem(
                        title = "Setting",
                        onClick = {}
                    )
                }
            }
        }
    )
}