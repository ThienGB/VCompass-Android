package com.example.vcompass.ui.components.bottom_sheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.TravelExplore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vcompass.R
import com.example.vcompass.enum.ActivityTypeEnum
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.screen.search.components.AccommodationHorizontalItem
import com.example.vcompass.ui.core.bottom_sheet.BaseBottomSheet
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.title.ActionIcon
import com.example.vcompass.ui.core.title.TitleSearchBarAction
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.add
import com.example.vcompass.util.scaleOnClick
import com.vcompass.presentation.model.schedule.Activity
import com.vcompass.presentation.util.CoreRoute

@Composable
fun AddScheduleActivityPopup(
    sheetState: MutableState<Boolean> = remember { mutableStateOf(true) },
    onDismiss: () -> Unit = {},
    onActivitySelected: (Activity) -> Unit = {}
) {
    var type by remember { mutableStateOf<ActivityTypeEnum?>(null) }
    val navController = ScreenContext.navController
    BaseBottomSheet(
        bottomSheetState = sheetState,
        onDismiss = onDismiss,
        isAllowHidden = type == null,
        isShowDragHandle = type == null,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (type == null) {
                CoreText(
                    text = "Thêm hoạt động",
                    style = CoreTypographySemiBold.labelLarge,
                    textAlign = TextAlign.Center,
                    isFullWidth = true
                )
            }
            AnimatedContent(
                targetState = type,
                transitionSpec = {
                    if (targetState != null) {
                        (slideInHorizontally { it } + fadeIn()) togetherWith
                                (slideOutHorizontally { -it } + fadeOut())
                    } else {
                        (slideInHorizontally { -it } + fadeIn()) togetherWith
                                (slideOutHorizontally { it } + fadeOut())
                    }
                }
            ) { state ->
                if (state == null) {
                    Column(modifier = Modifier.padding(horizontal = MyDimen.p16)) {
                        SpaceHeight()
                        AddActivityItem(
                            text = stringResource(R.string.lb_accommodation),
                            resIcon = R.drawable.ic_accommodation_24dp,
                            resImg = R.drawable.img_accommodation
                        ) { type = ActivityTypeEnum.ACCOMMODATION }
                        AddActivityItem(
                            text = stringResource(R.string.lb_food_place),
                            resIcon = R.drawable.ic_food,
                            resImg = R.drawable.img_food_place
                        ) { type = ActivityTypeEnum.FOODPLACE }
                        AddActivityItem(
                            text = stringResource(R.string.lb_attraction),
                            resIcon = R.drawable.ic_beach_24dp,
                            resImg = R.drawable.img_attraction
                        ) { type = ActivityTypeEnum.ATTRACTION }
                        AddActivityItem(
                            text = stringResource(R.string.lb_other),
                            resIcon = R.drawable.ic_notification,
                            resImg = R.drawable.img_other_activity
                        ) { type = ActivityTypeEnum.OTHER }
                    }
                } else {
                    Column {
                        Spacer(modifier = Modifier.height(15.dp))
                        TitleSearchBarAction(
                            rightItem = {
                                ActionIcon(imageVector = Icons.Rounded.TravelExplore) {
                                    navController.add(CoreRoute.MapSearch.route)
                                }
                            }
                        ) { type = null }
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(vertical = 10.dp)
                        ) {
                            repeat(9) {
                                AccommodationHorizontalItem {
                                    //  onActivitySelected(mockActivityDay2)
                                    onDismiss()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddActivityItem(
    text: String = stringResource(R.string.lb_accommodation),
    resIcon: Int = R.drawable.ic_accommodation_24dp,
    resImg: Int = R.drawable.img_accommodation,
    onClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.clip(RoundedCornerShape(12.dp))
    ) {
        CoreImage(
            source = CoreImageSource.Drawable(resImg),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MyColor.White.copy(alpha = 0.1f)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .shadow(
                        MyDimen.p6,
                        shape = CircleShape,
                        ambientColor = MyColor.Black.copy(alpha = 0.5f)
                    )
                    .scaleOnClick { onClick() }
                    .clip(CircleShape)
                    .background(MyColor.GrayEEE)
                    .padding(vertical = MyDimen.p8, horizontal = MyDimen.p22),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CoreIcon(
                    resDrawable = resIcon,
                    iconModifier = Modifier.size(22.dp)
                )
                SpaceWidth8()
                CoreText(
                    text = text,
                    style = CoreTypographySemiBold.labelLarge
                )
            }
        }
    }
    SpaceHeight()
}
