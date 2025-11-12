package com.example.vcompass.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController = rememberNavController(),
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = true
        )
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = MyDimen.p120,
        sheetShape = RoundedCornerShape(topStart = MyDimen.p12, topEnd = MyDimen.p12),
        sheetContainerColor = MyColor.White,
        sheetDragHandle = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MyDimen.p24)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(MyDimen.p100))
                        .height(MyDimen.p4)
                        .width(MyDimen.p40)
                        .background(MyColor.Gray333)
                )
            }
        },
        sheetContent = {
            SearchTabScreen(navController)
        }
    ) {
        MapSearchScreen(navController)
    }
}



