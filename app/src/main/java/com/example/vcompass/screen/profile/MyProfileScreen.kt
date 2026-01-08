package com.example.vcompass.screen.profile

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyMedium
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.screen.feed.SchedulePost
import com.example.vcompass.screen.search.ImageSliderWithIndicator
import com.example.vcompass.ui.components.avatar.UserAvatar
import com.example.vcompass.ui.core.animation.StickyHeaderAnimationLayout
import com.example.vcompass.ui.core.button.OutlineButton
import com.example.vcompass.ui.core.button.PrimaryButton
import com.example.vcompass.ui.core.divider.ItemDivider
import com.example.vcompass.ui.core.general.BaseViewWithPullToRefresh
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.ExpandableSpacer
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceHeight4
import com.example.vcompass.ui.core.space.SpaceHeight8
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.back
import com.example.vcompass.util.clearAllStackAndAdd
import com.example.vcompass.util.glassmorphism
import com.vcompass.presentation.model.user.User
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.profile.UserProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyProfileScreen() {
    val viewModel: UserProfileViewModel = koinViewModel()
    val navController = ScreenContext.navController

    val state by viewModel.stateUI.collectAsState()
    val currentUser = viewModel.currentUser

    BaseViewWithPullToRefresh(
        viewModel = viewModel,
        state = state,
        navController = navController,
        statusBarPadding = false
    ) {
        StickyHeaderAnimationLayout(
            coverUrl = "",
            maxProgressRatio = 0.25f,
            imageSection = {
                Box(Modifier.height(it)) {
                    ImageSliderWithIndicator(listOf("1", "2", "3", "4", "5"))
                }
            },
            headerSection = { UserProfileInformationSection(it, navController) },
            infoSection = { autoProgress, offsetX, offsetY ->
                UserProfileInformationSection(
                    currentUser,
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
    }
}

@Composable
fun UserProfileInformationSection(
    autoProgress: Float,
    navController: NavController,
    viewModel: UserProfileViewModel = koinViewModel()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MyColor.White.copy(autoProgress))
            .statusBarsPadding()
            .padding(start = MyDimen.p4, end = MyDimen.p4, bottom = MyDimen.p2)
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
                .clickable {
                    viewModel.logout()
                    navController.clearAllStackAndAdd(CoreRoute.Login.route)
                }
        )
    }
}

@Composable
fun UserProfileInformationSection(
    user: User,
    autoProgress: Float,
    offsetX: Dp,
    offsetY: Dp
) {
    val infoStartPadding = lerp(MyDimen.zero, MyDimen.p56, autoProgress)
    val infoTopPadding = lerp(MyDimen.p100, MyDimen.p16, autoProgress)
    val fontSize = lerp(
        start = 22.sp,
        stop = 15.sp,
        fraction = autoProgress
    )
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .padding(
                start = MyDimen.p16,
                top = MyDimen.p20 * (1 - autoProgress)
            )
            .offset(x = offsetX, y = offsetY - MyDimen.p64 * (1 - autoProgress))
            .zIndex(3f)
            .fillMaxWidth()
    ) {
        UserAvatar(
            pathUrl = user.avatar,
            size = MyDimen.p48 + MyDimen.p52 * (1 - autoProgress),
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            CoreText(
                text = user.name ?: "Thiện Hoàng",
                style = CoreTypographySemiBold.displayMedium.copy(fontSize = fontSize),
                color = MyColor.TextColorPrimary,
                modifier = Modifier.padding(start = infoStartPadding, top = infoTopPadding)
            )
            SpaceHeight4()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = MyDimen.p16)
                    .alpha(1 - autoProgress * 2)
            ) {
                CoreText(
                    text = "12 Người theo dõi | 15 Đang theo dõi",
                    style = CoreTypography.labelSmall,
                    color = MyColor.TextColorLight,
                )
                SpaceHeight()
                PrimaryButton(
                    text = stringResource(R.string.lb_create_schedule),
                    modifier = Modifier.height(MyDimen.p36)
                )
                SpaceHeight8()
                OutlineButton(
                    text = stringResource(R.string.lb_edit_profile),
                    modifier = Modifier.height(MyDimen.p36)
                )
            }
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
    var selectedTab by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = tabOffsetY)
            .clip(RoundedCornerShape(topStart = MyDimen.p12, topEnd = MyDimen.p12))
            .zIndex(1f)
            .background(MyColor.White)
            .nestedScroll(nestedScrollConnection)
    ) {
        SpaceHeight(MyDimen.p56 + MyDimen.p120 * (1 - autoProgress))
        ItemDivider(modifier = Modifier.statusBarsPadding())
        Row(
            horizontalArrangement = Arrangement.spacedBy(MyDimen.p8),
            modifier = Modifier.padding(vertical = MyDimen.p4, horizontal = MyDimen.p4)
                .zIndex(5f)
        ) {
            TabButtonSection(
                stringResource(R.string.lb_schedule),
                selectedTab == 0
            ) { selectedTab = 0 }
            TabButtonSection(
                stringResource(R.string.lb_image),
                selectedTab == 1
            ) { selectedTab = 1 }
            TabButtonSection(
                stringResource(R.string.lb_video),
                selectedTab == 2
            ) { selectedTab = 2 }
        }
        ItemDivider()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(contentScrollState),
        ) {
            UserProfileInformationSection()
            SpaceHeight()
            repeat(8){
                SchedulePost()
            }
        }
    }
}

@Composable
fun TabButtonSection(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    CoreText(
        text = text,
        style = CoreTypographyMedium.labelLarge,
        modifier = Modifier
            .clip(RoundedCornerShape(MyDimen.p100))
            .clickable(onClick = onClick)
            .background(if (isSelected) MyColor.Primary.copy(0.1f) else MyColor.Transparent)
            .padding(horizontal = MyDimen.p10, vertical = MyDimen.p8),
        color = if (isSelected) MyColor.Primary else MyColor.TextColorPrimary
    )
}



