package com.example.vcompass.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.accessed.core.compose_view.text.CoreText
import com.example.vcompass.R
import com.example.vcompass.enum.SearchHomeTab
import com.example.vcompass.screen.feed.TravelPost
import com.example.vcompass.ui.core.list.ListItemTab
import com.vcompass.core.compose_view.TabView
import com.vcompass.core.compose_view.TitleSearchBarAction
import com.vcompass.core.compose_view.image.CoreIcon
import com.vcompass.core.compose_view.image.CoreImage
import com.vcompass.core.compose_view.image.CoreImageSource
import com.vcompass.core.compose_view.space.ExpandableSpacer
import com.vcompass.core.compose_view.space.SpaceWidth
import com.vcompass.core.resource.MyColor
import com.vcompass.core.resource.MyDimen
import com.vcompass.core.typography.CoreTypography
import com.vcompass.core.typography.CoreTypographyBold
import com.vcompass.core.typography.CoreTypographySemiBold

@Composable
fun SearchTabScreen(
    navController: NavController = rememberNavController(),
) {
    val tabs = SearchHomeTab.entries
    val selectedTabIndex = remember { mutableIntStateOf(0) }
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
                        TravelPost()
                    }

                    SearchHomeTab.ACCOMMODATION -> ListItemTab(
                        items = listOf(1, 2, 3, 4, 5, 6, 7)
                    ) { person ->
                        AccommodationHorizontalItem()
                    }

                    SearchHomeTab.FOODPLACE -> ListItemTab(
                        items = listOf(1, 2, 3, 4, 5)
                    ) { person ->
                        TravelPost()
                    }

                    SearchHomeTab.ATTRACTION -> ListItemTab(
                        items = listOf(1)
                    ) { person ->
                        TravelPost()
                    }
                }
            }
        )
    }
}

@Composable
fun AccommodationHorizontalItem(
    property: Property = Property(
        1,
        "Pasteur Luxury Home",
        4.8f,
        "25km away",
        "$152",
        "https://xaydungancu.com.vn/wp-content/uploads/2023/03/anh-nha-dep-phong-cach-hien-dai-11.jpg"
    )
) {
    var isFavorite by remember { mutableStateOf(property.isFavorite) }
    Card(
        shape = RoundedCornerShape(MyDimen.p12),
        modifier = Modifier
            .fillMaxWidth()
            .height(MyDimen.p160)
            .padding(horizontal = MyDimen.p16, vertical = MyDimen.p8),
        colors = CardDefaults.cardColors(MyColor.White),
        elevation = CardDefaults.cardElevation(defaultElevation = MyDimen.p2)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {}) {
            Row {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.3f)
                ) {
                    CoreImage(
                        source = CoreImageSource.Url(property.image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(
                    modifier = Modifier.padding(MyDimen.p8)
                ) {
                    CoreText(
                        text = property.name,
                        style = CoreTypographySemiBold.labelLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(MyDimen.p2))
                    CoreText(
                        text = "Ho Chi Minh",
                        style = CoreTypography.displaySmall,
                        color = MyColor.TextColorLight
                    )
                    Spacer(modifier = Modifier.height(MyDimen.p4))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(android.R.drawable.btn_star_big_on),
                            contentDescription = null,
                            tint = MyColor.Rating,
                            modifier = Modifier.size(MyDimen.p12)
                        )
                        Spacer(modifier = Modifier.width(MyDimen.p2))
                        CoreText(
                            text = property.rating.toString(),
                            style = CoreTypography.displaySmall,
                        )
                        Spacer(modifier = Modifier.width(MyDimen.p4))
                        CoreText(
                            text = "(21 Review)",
                            style = CoreTypography.displaySmall,
                            color = MyColor.TextColorLight
                        )
                    }
                    Spacer(modifier = Modifier.height(MyDimen.p4))
                    FlowRow(
                        maxLines = 2
                    ) {
                        IconTextHorizontal(
                            icon = R.drawable.ic_king_bed_24dp,
                            text = "2 giuong"
                        )
                        SpaceWidth(MyDimen.p6)
                        IconTextHorizontal(
                            icon = R.drawable.ic_bathtub_24dp,
                            text = "2 phong tam"
                        )
                        SpaceWidth(MyDimen.p6)
                        IconTextHorizontal(
                            icon = R.drawable.ic_home_fill,
                            text = "42 m",
                            iconSize = MyDimen.p12
                        )
                    }
                    Spacer(modifier = Modifier.height(MyDimen.p4))
                    ExpandableSpacer()
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = MyDimen.p4)
                    ) {
                        CoreText(
                            text = property.price,
                            style = CoreTypographyBold.displayMedium,
                            color = MyColor.Black,
                            lineHeight = MyDimen.s16,
                        )
                        CoreText(
                            text = "/night",
                            style = CoreTypography.displaySmall,
                            color = MyColor.TextColorLight,
                        )
                    }
                }
            }
            FavoriteSelector(
                modifier = Modifier.align(Alignment.TopEnd),
                isFavorite = isFavorite,
                onFavoriteChange = { isFavorite = it }
            )
        }
    }
}

@Composable
fun IconTextHorizontal(
    icon: Int,
    text: String,
    style: TextStyle = CoreTypography.displaySmall,
    iconSize: Dp = MyDimen.p16,
) {
    Row {
        CoreIcon(
            resDrawable = icon,
            tintColor = MyColor.Gray999,
            iconModifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.width(MyDimen.p2))
        CoreText(
            text = text,
            style = style,
            color = MyColor.TextColorLight
        )
    }
}

@Composable
fun FavoriteSelector(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavoriteChange: (Boolean) -> Unit
) {
    IconButton(
        onClick = { onFavoriteChange(!isFavorite) },
        modifier = modifier
    ) {
        CoreIcon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            tintColor = if (isFavorite) MyColor.Favorite else MyColor.Gray999
        )
    }
}