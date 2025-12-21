package com.example.vcompass.screen.accommodation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.screen.search.ImageSliderWithIndicator
import com.example.vcompass.ui.components.bottom_sheet.AddScheduleActivityPopup
import com.example.vcompass.ui.core.animation.StickyHeaderAnimationLayout
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.rating.RatingBar
import com.example.vcompass.ui.core.space.ExpandableSpacer
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.back
import com.example.vcompass.util.glassmorphism
import com.vcompass.presentation.enums.BottomSheetType
import com.vcompass.presentation.viewmodel.schedule.ScheduleViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccommodationDetailScreen(
    viewModel: ScheduleViewModel = koinViewModel()
) {
    val navController = ScreenContext.navController
    val schedule by viewModel.schedule.collectAsState()
    val sheetType by viewModel.sheetType.collectAsState()
    val sheetState = rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(sheetType) {
        sheetState.value = sheetType != BottomSheetType.NONE
    }

    StickyHeaderAnimationLayout(
        coverUrl = schedule.images?.firstOrNull(),
        maxProgressRatio = 0.25f,
        imageSection = {
            Box(Modifier.height(it)) {
                ImageSliderWithIndicator(listOf("1", "2", "3", "4", "5"))
            }
        },
        headerSection = { AccommodationHeaderSection(it, navController) },
        infoSection = { autoProgress, offsetX, offsetY ->
            AccommodationInformationSection(
                autoProgress,
                offsetX,
                offsetY
            )
        },
        contentSection = { autoProgress, containerSpace, nestedScrollConnection, contentScrollState ->
            val tabOffsetY = lerp(
                containerSpace * (1 - autoProgress) + MyDimen.p8,
                MyDimen.zero,
                autoProgress
            )
            AccommodationContentSection(
                nestedScrollConnection = nestedScrollConnection,
                contentScrollState = contentScrollState,
                autoProgress = autoProgress,
                tabOffsetY = tabOffsetY
            )
        }
    )
    when (sheetType) {
        BottomSheetType.ADD_ACTIVITY -> AddScheduleActivityPopup(
            sheetState = sheetState,
            onDismiss = { viewModel.hideSheet() }
        ) { activity -> viewModel.addActivity(activity) }

        else -> {}
    }
}

@Composable
fun AccommodationHeaderSection(
    autoProgress: Float,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MyColor.White.copy(autoProgress))
            .statusBarsPadding()
            .padding(horizontal = MyDimen.p4)
            .zIndex(2f),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CoreIcon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            tintColor = MyColor.TextColorPrimary,
            iconModifier = Modifier.size(MyDimen.p24),
            boxModifier = Modifier
                .size(MyDimen.p48)
                .glassmorphism()
                .clickable(onClick = { navController.back() })
        )
        ExpandableSpacer()
        CoreIcon(
            imageVector = Icons.Rounded.FavoriteBorder,
            tintColor = MyColor.Gray666,
            iconModifier = Modifier.size(MyDimen.p24),
            boxModifier = Modifier
                .size(MyDimen.p48)
                .glassmorphism()
                .clickable { }
        )
        Spacer(modifier = Modifier.width(MyDimen.p8))
        CoreIcon(
            imageVector = Icons.Rounded.Share,
            tintColor = MyColor.Gray666,
            iconModifier = Modifier.size(MyDimen.p24),
            boxModifier = Modifier
                .size(MyDimen.p48)
                .glassmorphism()
                .clickable { }
        )
    }
}

@Composable
fun AccommodationInformationSection(
    autoProgress: Float,
    offsetX: Dp,
    offsetY: Dp
) {
    val fontSize = lerp(
        start = 22.sp,
        stop = 15.sp,
        fraction = autoProgress
    )
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .padding(start = MyDimen.p16, top = MyDimen.p20 * (1 - autoProgress))
            .offset(x = offsetX, y = offsetY)
            .zIndex(3f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            CoreText(
                text = "Accommodation Name",
                style = CoreTypographySemiBold.displayMedium.copy(fontSize = fontSize),
                color = MyColor.TextColorPrimary,
                modifier = Modifier.padding(top = MyDimen.p8)
            )
            RatingBar(starSize = MyDimen.p16, rating = 4.5f)
        }
    }
}

@Composable
fun AccommodationContentSection(
    nestedScrollConnection: NestedScrollConnection,
    contentScrollState: ScrollState,
    autoProgress: Float,
    tabOffsetY: Dp
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = tabOffsetY)
            .zIndex(1f)
            .clip(RoundedCornerShape(topStart = MyDimen.p12, topEnd = MyDimen.p12))
            .background(MyColor.White)
            .statusBarsPadding()
            .nestedScroll(nestedScrollConnection)
            .verticalScroll(contentScrollState),
    ) {
        SpaceHeight(MyDimen.p36 + autoProgress * MyDimen.p24)
        RatingSection()
        LocationSection()
        PopularAmenities()
        ChooseRoomHeader()
        ConfirmDateCard()
        PromotionBanner()
        RoomCard()
        ReviewOverviewCard()
        LocationCard()
    }
}




