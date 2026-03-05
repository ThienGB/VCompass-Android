package com.example.vcompass.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.vcompass.enum.tab.SearchHomeTab
import com.example.vcompass.resource.MyColor
import com.example.vcompass.screen.feed.SchedulePost
import com.example.vcompass.screen.search.components.AccommodationHorizontalItem
import com.example.vcompass.screen.search.components.AttractionHorizontalItem
import com.example.vcompass.screen.search.components.FoodPlaceHorizontalItem
import com.example.vcompass.ui.core.list.ListItemTab
import com.example.vcompass.ui.core.tab.TabView
import com.example.vcompass.ui.core.title.TitleSearchBarAction
import com.example.vcompass.ui.pagination.BasePaginationItem
import com.example.vcompass.util.add
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.search.MapSearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchTabScreen(
    navController: NavController = rememberNavController(),
) {
    val viewModel: MapSearchViewModel = koinViewModel()
    val tabs = SearchHomeTab.entries
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val accommodations = viewModel.accommodations.collectAsLazyPagingItems()
    val attractions = viewModel.attractions.collectAsLazyPagingItems()
    val foodPlaces = viewModel.foodPlaces.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .background(MyColor.White)
            .fillMaxSize()
    ) {
        TitleSearchBarAction(
            placeholder = "Search any things...",
            onBack = { navController.popBackStack() }
        )
        TabView(
            isScrollable = true,
            tabs = tabs,
            selectedTabIndex = selectedTabIndex.intValue,
            onTabSelected = { index -> selectedTabIndex.intValue = index },
            tabTitle = { tab -> stringResource(tab.titleRes) },
            content = { tab ->
                when (tab) {
                    SearchHomeTab.SCHEDULE -> ListItemTab(
                        items = listOf("", "12", "123")
                    ) { item ->
                        SchedulePost()
                    }

                    SearchHomeTab.ACCOMMODATION ->
                        BasePaginationItem(
                            lazyPagingItems = accommodations,
                        ) { accommodation ->
                            AccommodationHorizontalItem(accommodation) {
                                navController.add(CoreRoute.AccommodationDetail.route)
                            }
                        }

                    SearchHomeTab.FOODPLACE ->  BasePaginationItem(
                        lazyPagingItems = foodPlaces,
                    ) { foodPlace ->
                        FoodPlaceHorizontalItem(foodPlace) {
                            navController.add(CoreRoute.AccommodationDetail.route)
                        }
                    }

                    SearchHomeTab.ATTRACTION ->  BasePaginationItem(
                        lazyPagingItems = attractions,
                    ) { attraction ->
                        AttractionHorizontalItem(attraction) {
                            navController.add(CoreRoute.AccommodationDetail.route)
                        }
                    }
                }
            }
        )
    }
}