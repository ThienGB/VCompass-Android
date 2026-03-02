package com.example.vcompass.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.CoreTypographyMedium
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.general.BaseView
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.space.ExpandableSpacer
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.permission.rememberLocationPermissionHandler
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.add
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.search.SearchLandingViewModel
import org.koin.androidx.compose.koinViewModel

data class Property(
    val id: Int,
    val name: String,
    val rating: Float,
    val distance: String,
    val price: String,
    val image: String,
    val isFavorite: Boolean = false
)

@Composable
fun SearchLandingScreen() {
    val navController = ScreenContext.navController
    val viewModel: SearchLandingViewModel = koinViewModel()

    var hasLocationPermission by remember { mutableStateOf(false) }
    val address by viewModel.address.collectAsState()
    val state by viewModel.stateUI.collectAsState()

    val requestLocationPermission = rememberLocationPermissionHandler { granted ->
        hasLocationPermission = granted
    }

    LaunchedEffect(Unit) {
        requestLocationPermission()
    }

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            viewModel.loadCurrentLocation()
        }
    }
    val nearbyProperties = remember {
        listOf(
            Property(
                1,
                "Pasteur Luxury Home",
                4.8f,
                "25km away",
                "$152",
                "https://xaydungancu.com.vn/wp-content/uploads/2023/03/anh-nha-dep-phong-cach-hien-dai-11.jpg"
            ),
            Property(
                2,
                "Abah Villa with a privat...",
                4.8f,
                "25km away",
                "$152",
                "https://xaydungancu.com.vn/wp-content/uploads/2023/03/anh-nha-dep-phong-cach-hien-dai-11.jpg",
                true
            )
        )
    }
    BaseView(
        viewModel = viewModel,
        state = state,
        topBar = {
            SearchLandingHeader(address?.shortAddress, navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MyColor.White)
                .verticalScroll(rememberScrollState())
        ) {
            NearbySection(nearbyProperties)
            Spacer(modifier = Modifier.height(MyDimen.p24))
            FeaturedDestinationSection(nearbyProperties)
            Spacer(modifier = Modifier.height(MyDimen.p24))
        }
    }
}

@Composable
fun SearchLandingHeader(
    address: String?,
    navController: NavController
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CoreIcon(
                imageVector = Icons.Default.LocationOn,
                tintColor = MyColor.Primary,
                iconModifier = Modifier.size(MyDimen.p20),
                boxModifier = Modifier.padding(horizontal = MyDimen.p4)
            )
            CoreText(
                text = address ?: stringResource(R.string.lb_finding_your_place),
                style = CoreTypographyMedium.labelLarge,
                color = MyColor.TextColorPrimary
            )
        }
        ExpandableSpacer()
        IconButton(onClick = { navController.add(CoreRoute.MapSearch.route) }) {
            CoreIcon(resDrawable = R.drawable.ic_map_search_24dp)
        }
    }
}

@Composable
fun NearbySection(nearbyProperties: List<Property>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MyDimen.p16),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Nearby",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = MyDimen.s18,
            color = MyColor.TextColorPrimary
        )
        TextButton(onClick = { }) {
            Text(
                "Show all",
                color = MyColor.Primary,
                fontSize = MyDimen.s14
            )
        }
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = MyDimen.p16),
        horizontalArrangement = Arrangement.spacedBy(MyDimen.p12)
    ) {
        items(nearbyProperties) { property ->
            AccommodationVerticalItem(property = property)
        }
    }
}

@Composable
fun FeaturedDestinationSection(nearbyProperties: List<Property>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MyDimen.p16),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Featured Destination",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = MyDimen.s18,
            color = MyColor.TextColorPrimary
        )
        TextButton(onClick = { }) {
            Text(
                "Show all",
                color = MyColor.Primary,
                fontSize = MyDimen.s14
            )
        }
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = MyDimen.p16),
        horizontalArrangement = Arrangement.spacedBy(MyDimen.p12)
    ) {
        items(nearbyProperties) { destination ->
            DestinationCard(destination = destination)
        }
    }
}

@Composable
fun AccommodationVerticalItem(property: Property) {
    val navController = ScreenContext.navController
    Card(
        modifier = Modifier
            .width(MyDimen.p170)
            .height(MyDimen.p235)
            .clickable {
                navController.add(CoreRoute.AccommodationDetail.route)
            },
        shape = RoundedCornerShape(MyDimen.p8),
        colors = CardDefaults.cardColors(MyColor.White),
        elevation = CardDefaults.cardElevation(defaultElevation = MyDimen.p2)
    ) {
        Box {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MyDimen.p140)
                ) {
                    CoreImage(
                        source = CoreImageSource.Url(property.image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(
                        onClick = { },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        CoreIcon(
                            imageVector = if (property.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            tintColor = if (property.isFavorite) MyColor.Favorite else MyColor.White
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(MyDimen.p8)
                ) {
                    CoreText(
                        text = property.name,
                        style = CoreTypographySemiBold.labelLarge,
                        maxLines = 1,
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
                        Spacer(modifier = Modifier.width(MyDimen.p8))
                        CoreText(
                            text = property.distance,
                            style = CoreTypography.displaySmall,
                        )
                    }
                    Spacer(modifier = Modifier.height(MyDimen.p4))
                    Row(verticalAlignment = Alignment.CenterVertically) {
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
        }
    }
}

@Composable
fun DestinationCard(destination: Property) {
    Card(
        modifier = Modifier
            .width(MyDimen.p170)
            .height(MyDimen.p250),
        shape = RoundedCornerShape(MyDimen.p8),
        colors = CardDefaults.cardColors(MyColor.White),
        elevation = CardDefaults.cardElevation(defaultElevation = MyDimen.p2)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CoreImage(
                source = CoreImageSource.Url(destination.image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.8f),
                                Color.Transparent
                            ),
                            startY = Float.POSITIVE_INFINITY,
                            endY = 0f
                        )
                    )
                    .padding(MyDimen.p8)
                    .padding(top = MyDimen.p56),
                verticalArrangement = Arrangement.Bottom
            ) {
                CoreText(
                    text = destination.name,
                    color = MyColor.White,
                    style = CoreTypographyBold.displayMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                SpaceHeight(MyDimen.p2)
                CoreText(
                    text = "185 recommendation",
                    style = CoreTypography.displaySmall,
                    color = MyColor.GrayF5,
                )
            }

        }
    }
}
