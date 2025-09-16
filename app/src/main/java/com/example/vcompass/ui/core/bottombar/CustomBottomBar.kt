package com.example.vcompass.ui.core.bottombar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vcompass.core.dimen.MyDimen
import com.example.vcompass.ui.theme.MyColor
import com.example.vcompass.util.AppConstants

@Composable
fun CustomBottomBar(
    onClickItemBottomBar: (Int) -> Unit,
    onClickMenu: () -> Unit
) {
    val heightBottomBar = 56.dp
    var selectedIndex by remember { mutableIntStateOf(0) }

    Box(Modifier.fillMaxSize()) {
        CurvedBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            barHeight = heightBottomBar,
            cornerRadius = MyDimen.zero,
            containerColor = MyColor.White,
            contentColor = MyColor.Primary
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightBottomBar)
                    .padding(horizontal = MyDimen.p8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomDestinations.forEachIndexed { index, item ->
                    if (index == AppConstants.HOME_FLOAT_BUTTON_INDEX) {
                        Spacer(Modifier.weight(1f))
                        return@forEachIndexed
                    }
                    NavButton(
                        modifier = Modifier.weight(1f),
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
            cutoutRadius = MyDimen.p36,
            onClick = {
                onClickMenu.invoke()
            }
        )
    }
}