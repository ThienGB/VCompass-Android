package com.example.vcompass.ui.pagination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.animation.CoreItemAnimation
import com.example.vcompass.ui.core.list.EmptyListItem
import com.example.vcompass.ui.core.list.ScrollDirection
import com.example.vcompass.ui.core.list.rememberVerticalScrollDirection
import com.example.vcompass.ui.core.loading.CoreErrorItem
import com.example.vcompass.ui.core.loading.CoreLoadMoreItem
import com.example.vcompass.ui.core.space.SpaceHeight
import kotlinx.coroutines.flow.distinctUntilChangedBy

@Composable
fun <T : Any> BasePaginationItem(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<T>,
    userScrollEnabled: Boolean = true,
    maxScrollHeight: Dp? = null,
    columns: Int? = null,
    verticalSpace: Dp? = null,
    horizontalSpace: Dp? = null,
    onScrollDirection: ((ScrollDirection) -> Unit)? = null,
    headerContent: @Composable (() -> Unit)? = null,
    footerContent: @Composable (() -> Unit)? = null,
    itemContent: @Composable (T) -> Unit = {}
) {
    var isLoading by remember { mutableStateOf(false) }
    var statePaging by remember { mutableStateOf(lazyPagingItems.loadState.refresh) }

    val stateList = rememberLazyListState()
    val gridState = rememberLazyGridState()
    val scrollDirection by rememberVerticalScrollDirection(
        listState = if (columns == null) stateList else null,
        gridState = if (columns != null) gridState else null
    )

    LaunchedEffect(scrollDirection) {
        onScrollDirection?.invoke(scrollDirection)
    }

    LaunchedEffect(lazyPagingItems) {
        snapshotFlow { lazyPagingItems.loadState.refresh }
            .distinctUntilChangedBy { it::class }
            .collect { state ->
                isLoading = state is LoadState.Loading
                statePaging = state
            }
    }

    val lastModifier = if (maxScrollHeight != null) {
        Modifier.heightIn(max = maxScrollHeight)
    } else {
        Modifier.fillMaxSize()
    }

    if (statePaging is LoadState.Error) {
        CoreErrorItem {
            lazyPagingItems.retry()
        }
        return
    }

    CoreItemAnimation(
        isShowSwitchItem = isLoading,
        mainItem = {
            PullToRefreshBox(
                modifier = modifier,
                isRefreshing = false,
                onRefresh = { lazyPagingItems.refresh() },
                content = {
                    if (lazyPagingItems.itemSnapshotList.isEmpty()) {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            SpaceHeight()
                            EmptyListItem()
                        }
                        return@PullToRefreshBox
                    }

                    Column {
                        headerContent?.let {
                            it()
                        }

                        if (columns != null) {
                            Column {
                                LazyVerticalGrid(
                                    state = gridState,
                                    columns = GridCells.Fixed(columns),
                                    userScrollEnabled = userScrollEnabled,
                                    verticalArrangement = Arrangement.spacedBy(
                                        verticalSpace ?: MyDimen.zero
                                    ),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        horizontalSpace ?: MyDimen.zero
                                    ),
                                    modifier = lastModifier
                                ) {
                                    items(lazyPagingItems.itemCount) { index ->
                                        lazyPagingItems[index]?.let { company ->
                                            itemContent(company)
                                        }
                                    }

                                    item(span = { GridItemSpan(columns) }) {
                                        when (lazyPagingItems.loadState.append) {
                                            is LoadState.Loading -> {
                                                footerContent?.invoke() ?: CoreLoadMoreItem()
                                            }

                                            else -> {
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            LazyColumn(
                                state = stateList,
                                modifier = lastModifier,
                                userScrollEnabled = userScrollEnabled
                            ) {
                                items(lazyPagingItems.itemCount) { index ->
                                    lazyPagingItems[index]?.let { company ->
                                        itemContent(company)
                                    }
                                }

                                item {
                                    when (lazyPagingItems.loadState.append) {
                                        is LoadState.Loading -> {
                                            footerContent?.invoke() ?: CoreLoadMoreItem()
                                        }

                                        else -> {
                                        }
                                    }
                                }
                            }
                        }
                    }

                    val refreshState = lazyPagingItems.loadState.refresh
                    val isListEmpty = lazyPagingItems.itemCount == 0

                    when {
                        isListEmpty && refreshState is LoadState.Loading -> {
                            CoreLoadMoreItem()
                        }

                        isListEmpty && refreshState is LoadState.Error -> {
                            CoreErrorItem(
                                errorText = refreshState.error.message
                            ) {
                                lazyPagingItems.retry()
                            }
                        }
                    }
                }
            )
        },
        switchItem = {
            Box(modifier = Modifier.fillMaxSize()) {
                CoreLoadMoreItem()
            }
        }
    )
}