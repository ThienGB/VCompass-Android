package com.example.vcompass.ui.module.user.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vcompass.helper.BottomSheetType

@Preview(showSystemUi = true)
@Composable
fun Explore(
    navController: NavController = rememberNavController(),
){
    val bottomSheetState = remember { mutableStateOf(BottomSheetType.NONE) }
    fun showBottomSheet(sheet: BottomSheetType) {
        if (bottomSheetState.value != sheet) {
            bottomSheetState.value = sheet
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        Column(modifier = Modifier.background(Color.White)){
            ExploreContent()
        }
        if (bottomSheetState.value != BottomSheetType.NONE) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.4f))
                .zIndex(1f))
        }
        Box(modifier = Modifier
            .zIndex(2f)
            .then(
                if (bottomSheetState.value == BottomSheetType.COMMENT
                    || bottomSheetState.value == BottomSheetType.ADD_ACTIVITY
                )
                    Modifier.fillMaxHeight(0.94f) else Modifier
            )
            .align(Alignment.BottomEnd)
        ) {

        }
    }
}
@Composable
fun ExploreContent(
){

}