package com.example.vcompass.ui.core.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vcompass.core.resource.MyDimen
import com.vcompass.core.resource.MyColor
import com.example.vcompass.util.AppConstants

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFFFF5555)
@Composable
fun CustomBottomBar(
    onClickItemBottomBar: (Int) -> Unit = {},
    onClickMenu: () -> Unit = {}
) {
    val heightBottomBar = 48.dp
    var selectedIndex by remember { mutableIntStateOf(0) }

    Box(Modifier.fillMaxSize()) {
        CurvedBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            barHeight = heightBottomBar,
            containerColor = MyColor.White,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(horizontal = MyDimen.p8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomDestinations.forEachIndexed { index, item ->
                    if (index == AppConstants.HOME_FLOAT_BUTTON_INDEX) {
                        Spacer(Modifier.weight(4f))
                        return@forEachIndexed
                    }
                    NavButton(
                        modifier = Modifier.weight(3f),
                        item = item,
                        height = heightBottomBar,
                        selected = selectedIndex == index,
                        onClick = {
                            onClickItemBottomBar.invoke(index)
                            if (index != AppConstants.HOME_FLOAT_BUTTON_INDEX)
                                selectedIndex = index
                        },
                        selectedColor = MyColor.Primary,
                        contentColor = MyColor.Gray666
                    )
                }
            }
        }

        CenterDockedFab(
            modifier = Modifier.align(Alignment.BottomCenter),
            barHeight = heightBottomBar,
            cutoutRadius = MyDimen.p24,
            onClick = {
                onClickMenu.invoke()
            }
        )
    }
}