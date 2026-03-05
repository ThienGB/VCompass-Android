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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.general.BaseView
import com.example.vcompass.util.ScreenContext
import com.vcompass.presentation.model.location.AppLocation
import com.vcompass.presentation.viewmodel.search.MapSearchViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchScreen() {
    val navController = ScreenContext.navController
    val viewModel: MapSearchViewModel = koinViewModel()

    val state by viewModel.stateUI.collectAsState()
    val location by viewModel.location.collectAsState()

    LaunchedEffect(location) {
        if (location != null) {
            viewModel.getAccommodations(city = location?.city)
            viewModel.getFoodPlaces(city = location?.city)
            viewModel.getAttractions(city = location?.city)
        }
    }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = true
        )
    )

    BaseView(
        state = state,
        viewModel = viewModel
    ) {
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
            MapSearchSection(location ?: AppLocation())
        }
    }
}