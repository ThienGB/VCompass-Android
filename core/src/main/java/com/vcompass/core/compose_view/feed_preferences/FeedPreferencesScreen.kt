package com.vcompass.core.compose_view.feed_preferences

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vcompass.core.R
import com.vcompass.core.utils.extension.randomColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class FeedCategory(
    val id: String? = null,
    val name: String? = null,
    val image: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedPreferencesScreen(
    categories: List<FeedCategory> = listOf(),
    selectedCategories: List<FeedCategory> = listOf(),
    onCategoryClick: (FeedCategory) -> Unit = {},
    onUpdateClick: () -> Unit = {},
    onBack: () -> Unit = {},
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        _root_ide_package_.com.vcompass.core.compose_view.TitleBarAction(
            text = stringResource(R.string.lb_feed_my_category),
            onBack = onBack,
            actionRightText = stringResource(R.string.btn_update),
            isHaveIcon = false,
            onActionRightClick = { onUpdateClick() },
            isEnabled = selectedCategories.size >= 5
        )
        Text(
            text = stringResource(R.string.lb_onboarding_interest_header).uppercase(),
            fontSize = 15.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.content_padding),
                bottom = dimensionResource(R.dimen.vertical_space_medium),
                top = dimensionResource(R.dimen.vertical_space_medium)
            ),
            textAlign = TextAlign.Center
        )

        _root_ide_package_.com.vcompass.core.compose_view.PullToRefreshContainer(
            modifier = Modifier
                .weight(1f),
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            content = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.content_padding)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.content_padding)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.content_padding)),
                ) {
                    items(categories, key = { it.id.toString() }) { category ->
                        var isSelected = selectedCategories.any { it.id == category.id }
                        FeedCategoryItem(
                            category = category,
                            isSelected = isSelected,
                            onCategoryClick = {
                                isSelected = !isSelected
                                onCategoryClick(it)
                            }
                        )
                    }
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.navigation_bar_height)))
                    }
                }
            }
        )
    }
}

@Composable
fun FeedCategoryItem(
    category: FeedCategory,
    isSelected: Boolean,
    onCategoryClick: (FeedCategory) -> Unit
) {
    var color by remember { mutableStateOf(randomColor()) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable() {
                onCategoryClick(category)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.content_padding)))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(category.image)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            placeholder = painterResource(R.drawable.img_placeholder),
            onError = { state ->
                Log.e("AsyncImageError", "Error loading image: ${state.result.throwable}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(
                    width = if (isSelected) 1.5.dp else 0.5.dp,
                    color = if (isSelected) colorResource(R.color.colorSecondary)
                    else colorResource(R.color.stroke_color_grey),
                    shape = CircleShape
                )
                .background(color)
        )

        Text(
            text = category.name.toString(),
            color = if (isSelected) colorResource(R.color.colorSecondary) else
                colorResource(R.color.textColorDark),
            fontWeight = FontWeight.W500,
            maxLines = 2,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.content_padding)),
            fontSize = 15.sp
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun FeedPreferencesPreview() {
    val image =
        "https://t4.ftcdn.net/jpg/04/84/87/61/360_F_484876187_u6HIlCgA2iZdfkoOamuQa43OJH2zaDVR.jpg"
    val categories = listOf(
        FeedCategory(id = "1", name = "Technology"),
        FeedCategory(id = "2", name = "Sports"),
        FeedCategory(id = "3", name = "Music"),
        FeedCategory(id = "4", name = "Travel", image = image),
        FeedCategory(id = "5", name = "Fashion", image = image),
        FeedCategory(id = "6", name = "Food", image = image),
        FeedCategory(id = "7", name = "Art", image = image),
        FeedCategory(id = "8", name = "Health", image = image),
        FeedCategory(id = "9", name = "Fitness", image = image),
        FeedCategory(id = "10", name = "Photography", image = image),
        FeedCategory(id = "11", name = "Music", image = image),
        FeedCategory(id = "12", name = "Travel", image = image),
        FeedCategory(id = "13", name = "Fashion", image = image),
        FeedCategory(id = "14", name = "Food", image = image),
        FeedCategory(id = "15", name = "Art", image = image),
        FeedCategory(id = "16", name = "Health", image = image),
        FeedCategory(id = "17", name = "Fitness", image = image),
        FeedCategory(id = "18", name = "Photography", image = image),
        FeedCategory(id = "19", name = "Music", image = image),
        FeedCategory(id = "20", name = "Travel", image = image),
        FeedCategory(id = "21", name = "Fashion", image = image),
        FeedCategory(id = "22", name = "Food", image = image),
        FeedCategory(id = "23", name = "Art", image = image),
        FeedCategory(id = "24", name = "Health", image = image),
        FeedCategory(id = "25", name = "Fitness", image = image),
    )
    var selectedCategories by remember {
        mutableStateOf(
            listOf(
                FeedCategory(id = "7", name = "Sports", image = image),
                FeedCategory(id = "9", name = "Music", image = image),
                FeedCategory(id = "12", name = "Travel", image = image),
            )
        )
    }
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    FeedPreferencesScreen(
        categories = categories,
        selectedCategories = selectedCategories,
        onCategoryClick = { category ->
            val isSelected = selectedCategories.any { it.id == category.id }
            selectedCategories = if (isSelected) {
                selectedCategories.filter { it.id != category.id }
            } else {
                selectedCategories + category
            }
        },
        onUpdateClick = {},
        onBack = {},
        isRefreshing = isRefreshing,
        onRefresh = {
            coroutine.launch {
                isRefreshing = true
                delay(2000)
                selectedCategories = listOf(
                    FeedCategory(id = "7", name = "Sports", image = image),
                    FeedCategory(id = "9", name = "Music", image = image),
                    FeedCategory(id = "12", name = "Travel", image = image),
                )
                isRefreshing = false
            }
        }
    )
}
