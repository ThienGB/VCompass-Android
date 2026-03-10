package com.example.vcompass.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.R
import com.example.vcompass.ui.core.rating.RatingBar
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.space.ExpandableSpacer
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceHeight4
import com.example.vcompass.ui.core.space.SpaceWidth4
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.screen.search.components.FavoriteSelector
import com.example.vcompass.ui.core.button.BackButton
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.back
import com.google.android.gms.maps.CameraUpdateFactory
import com.vcompass.presentation.model.business.Business
import com.vcompass.presentation.model.business.getFirstImage
import com.vcompass.presentation.model.business.getLatitude
import com.vcompass.presentation.model.business.getLongitude
import com.vcompass.presentation.model.location.AppLocation
import com.vcompass.presentation.viewmodel.search.MapSearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MapSearchSection(
    location: AppLocation
) {
    val navController = ScreenContext.navController
    val viewModel: MapSearchViewModel = koinViewModel()
    val accommodations = viewModel.accommodations.collectAsLazyPagingItems()
    val attractions = viewModel.attractions.collectAsLazyPagingItems()
    val foodPlaces = viewModel.foodPlaces.collectAsLazyPagingItems()
    var selectedBusiness by remember { mutableStateOf<Business?>(null) }
    val cameraPositionState = rememberCameraPositionState()
    LaunchedEffect(location.latitude, location.longitude) {
        if (location.latitude != 0.0 && location.longitude != 0.0) {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    LatLng(location.latitude, location.longitude),
                    14f
                )
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.NORMAL
            ),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = true,
                myLocationButtonEnabled = true
            )
        ) {
            repeat(accommodations.itemCount) { index ->
                val item = accommodations[index]
                item?.let { business ->
                    BusinessMarker(business){
                        selectedBusiness = business
                    }
                }
            }
            repeat(attractions.itemCount) { index ->
                val item = attractions[index]
                item?.let { business ->
                    BusinessMarker(business){
                        selectedBusiness = business
                    }
                }
            }
            repeat(foodPlaces.itemCount) { index ->
                val item = foodPlaces[index]
                item?.let { business ->
                    BusinessMarker(business){
                        selectedBusiness = business
                    }
                }
            }
        }
        if (selectedBusiness != null) {
            DestinationInfoItem(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = MyDimen.p128),
                business = selectedBusiness ?: Business(),
                onDismiss = {
                    selectedBusiness = null
                }
            )
        }
        BackButton(
            modifier = Modifier
                .zIndex(5f)
                .statusBarsPadding()
                .align(Alignment.TopStart)
        ) {
            navController.back()
        }
    }
}

@Composable
fun BusinessMarker(business: Business, onSelect: () -> Unit) {
    MarkerComposable(
        state = MarkerState(
            position = LatLng(business.getLatitude(), business.getLongitude())
        ),
        onClick = {
            onSelect()
            true
        }
    ) {
        CustomMarkerView(rating = business.averageRating ?: 0f, business.getFirstImage())
    }
}

@Composable
fun CustomMarkerView(rating: Float, image: String) {
    val (backgroundColor, textColor) = when {
        rating >= 4.5f -> Color(0xFF00796B) to Color.White // Xanh đậm
        rating >= 4.0f -> Color(0xFF388E3C) to Color.White // Xanh lá
        rating >= 3.5f -> Color(0xFFFFA726) to Color.White // Cam
        else -> Color(0xFFE53935) to Color.White // Đỏ
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.size(60.dp, 70.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .shadow(4.dp, CircleShape)
                .border(2.dp, backgroundColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            CoreImage(
                source = CoreImageSource.Url(image),
                modifier = Modifier.fillMaxSize()
                    .clip(CircleShape),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = rating.toString(),
                    color = textColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Composable
fun DestinationInfoItem(
    modifier: Modifier = Modifier,
    business: Business,
    onDismiss: () -> Unit
) {
    var isFavorite by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier
            .padding(horizontal = MyDimen.p16)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(MyDimen.p8)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MyDimen.p180)
            ) {
                if (business.images == null) {
                    CoreImage(
                        source = CoreImageSource.Url("https://d2s2rtcxxwjegp.cloudfront.net/images/hotels/hotel_placeholder.png"),
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    ImageSliderWithIndicator(imageUrls = business.images.orEmpty())
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(MyDimen.p4)
                ) {
                    FavoriteSelector(
                        modifier = Modifier
                            .background(MyColor.White.copy(0.7f), CircleShape),
                        isFavorite = isFavorite,
                        onFavoriteChange = { isFavorite = it }
                    )
                    SpaceWidth4()
                    IconButton(
                        modifier = Modifier
                            .background(MyColor.White.copy(0.5f), CircleShape),
                        onClick = onDismiss
                    ) {
                        CoreIcon(imageVector = Icons.Default.Close)
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MyDimen.p16, vertical = MyDimen.p8)
            ) {
                CoreText(
                    text = business.name,
                    style = CoreTypographySemiBold.displayMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(MyDimen.p8))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CoreText(
                        text = business.averageRating.toString(),
                        style = CoreTypography.labelMedium,
                    )
                    SpaceWidth4()
                    RatingBar(
                        rating = business.averageRating ?: 0f,
                        readOnly = true,
                        starSize = MyDimen.p14
                    )
                    SpaceWidth4()
                    CoreText(
                        text = "(" + business.totalRatings + ")",
                        style = CoreTypography.labelMedium,
                        color = MyColor.TextColorLight
                    )
                    CoreText(
                        text = " • Cach 0.8 km",
                        style = CoreTypographySemiBold.labelMedium,
                    )
                }
                SpaceHeight4()
                CoreText(
                    text = "Công viên giải trí & Công viên chủ đề • Công viên văn hóa",
                    style = CoreTypography.labelSmall,
                    color = MyColor.TextColorLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                SpaceHeight()
                Row {
                    CoreIcon(
                        resDrawable = R.drawable.ic_clock_24dp,
                        iconModifier = Modifier.size(MyDimen.p16)
                    )
                    SpaceWidth4()
                    CoreText(
                        text = "Hiện đang đóng cửa",
                        style = CoreTypographySemiBold.labelSmall,
                        color = MyColor.RedE1
                    )
                    CoreText(
                        text = " • Mở cửa 8:00",
                        style = CoreTypography.labelSmall,
                    )
                }
                SpaceHeight()
                Row {
                    Column {
                        CoreText(
                            text = "Vé vào cửa",
                            style = CoreTypographySemiBold.labelSmall,
                        )
                        CoreText(
                            text = "Từ " + "999999" + "/người lớn",
                            style = CoreTypographySemiBold.displayMedium,
                            color = MyColor.Primary
                        )
                    }
                    ExpandableSpacer()
                    CoreIcon(
                        boxModifier = Modifier
                            .clip(CircleShape)
                            .padding(MyDimen.p4),
                        resDrawable = R.drawable.ic_director_24dp,
                        tintColor = MyColor.Primary,
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun ImageSliderWithIndicator(
    imageUrls: List<String>,
    showIndicator: Boolean = true,
    showDots: Boolean = true
) {
    val pagerState = rememberPagerState(pageCount = { imageUrls.size })

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            CoreImage(
                source = CoreImageSource.Url(imageUrls[page]),
                modifier = Modifier.fillMaxSize(),
                error = painterResource(R.drawable.img_food_place)
            )
        }
        if (showIndicator) {
            Box(
                modifier = Modifier
                    .padding(end = MyDimen.p8, bottom = MyDimen.p40)
                    .align(Alignment.BottomEnd)
                    .padding(MyDimen.p4)
                    .zIndex(1f)
                    .clip(CircleShape)
                    .background(MyColor.Black.copy(0.2f)),
                contentAlignment = Alignment.Center
            ) {
                CoreText(
                    text = "${pagerState.currentPage + 1}/${imageUrls.size}",
                    style = CoreTypography.labelSmall,
                    color = MyColor.White
                )
            }
        }
        if (showDots) {
            if (imageUrls.size > 1) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = MyDimen.p8),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val totalPages = imageUrls.size
                    val currentPage = pagerState.currentPage

                    val indicatorCount = minOf(totalPages, 3)

                    repeat(indicatorCount) { index ->
                        val isSelected = when {
                            totalPages <= 3 -> currentPage == index
                            currentPage == 0 -> index == 0
                            currentPage == totalPages - 1 -> index == 2
                            else -> index == 1
                        }

                        Box(
                            modifier = Modifier
                                .padding(horizontal = MyDimen.p4)
                                .size(if (isSelected) MyDimen.p8 else MyDimen.p6)
                                .background(
                                    color = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f),
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}