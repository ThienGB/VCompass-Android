package com.example.vcompass.screen.explore.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.R
import com.example.vcompass.util.clickNoRipple
import com.example.vcompass.ui.core.title.TitleSearchBarAction
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.vcompass.core.compose_view.list.GridList
import com.vcompass.core.compose_view.list.VerticalList
import com.example.vcompass.ui.core.scroll_view.VerticalScrollView
import com.example.vcompass.ui.core.space.SpaceWidth
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.resource.CoreTypographySemiBold

@Preview(showSystemUi = true)
@Composable
fun ExploreSearchScreen(
    navController: NavController = rememberNavController(),
) {
    VerticalScrollView(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
            .statusBarsPadding()
    ) {
        TitleSearchBarAction(
            placeholder = "Search any things...",
            onBack = { navController.popBackStack() }
        )
        SearchHistorySection()
        CategorySection()
    }
}

@Composable
fun CategorySection(
    items: List<String> = listOf("NailCare", "HairCare", "SkinCare", "BodyCare")
) {
    GridList(
        items = items,
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(10.dp)
            .heightIn(max = MyDimen.maxScrollHeight),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        CategoryItem(title = it)
    }
}

@Composable
fun CategoryItem(
    title: String = "NailCare",
    image: String = ""
) {
    Box {
        CoreImage(
            source = CoreImageSource.Url(image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
        )
        CoreText(
            text = title,
            style = CoreTypographySemiBold.displayMedium,
            modifier = Modifier
                .padding(10.dp)
                .width(90.dp)
        )
    }
}

@Composable
fun SearchHistorySection(
    items: List<String> = listOf(
        "NailCare",
        "HairCare",
        "SkinCare",
        "BodyCare",
        "NailCare",
        "HairCare",
        "SkinCare",
        "BodyCare"
    ),
) {
    var history by remember { mutableStateOf(items) }
    var expanded by remember { mutableStateOf(false) }
    val noOfItems = if (expanded) history.size else 4
    if (history.isNotEmpty()) {
        VerticalList(
            items = history.take(noOfItems),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = MyDimen.maxScrollHeight)
                .padding(16.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(MyDimen.p8),
            contentPadding = PaddingValues(end = MyDimen.p12)
        ) { item ->
            SearchHistoryItem(
                text = item,
                onClearClick = {
                    history = history - item
                }
            )
        }
        if (history.size > 4) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickNoRipple { expanded = true }
            ) {
                CoreText(
                    text = "Xem thêm",
                    style = CoreTypographySemiBold.displayMedium,
                    color = MyColor.TextColorLight
                )
                CoreIcon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    iconModifier = Modifier.size(MyDimen.p20)
                )
            }
        }
    }
}

@Composable
fun SearchHistoryItem(
    text: String = "NailCare",
    onClick: () -> Unit = {},
    onClearClick: () -> Unit = {}
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CoreIcon(
            resDrawable = R.drawable.ic_history_search_24dp,
            onClick = onClick
        )
        SpaceWidth()
        CoreText(
            text = text,
            style = CoreTypographySemiBold.displayMedium,
            maxLines = 2,
            modifier = Modifier.weight(1f)
        )
        CoreIcon(
            imageVector = Icons.Default.Clear,
            onClick = onClearClick
        )
    }
}