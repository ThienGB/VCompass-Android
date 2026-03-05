package com.example.vcompass.screen.feed

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.components.avatar.UserAvatar
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.space.ExpandableSpacer
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceWidth
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.navigate.NavigateKeyArg
import com.example.vcompass.util.AppConstants.SCHEDULE_TEMP_IMAGE_URL
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.add
import com.example.vcompass.util.clickNoRipple
import com.vcompass.presentation.util.formatThousandK
import com.example.vcompass.util.scaleOnClick
import com.example.vcompass.util.setArg
import com.vcompass.core.compose_view.list.HorizontalList
import com.vcompass.presentation.model.schedule.Schedule
import com.vcompass.presentation.util.CoreRoute
import kotlinx.coroutines.delay

@Composable
fun SchedulePost(
    schedule: Schedule = Schedule(),
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    val navController = ScreenContext.navController
    var hasFavorite by rememberSaveable { mutableStateOf(schedule.hasFavorite ?: false) }
    var hasLiked by rememberSaveable { mutableStateOf(schedule.hasLiked ?: false) }
    var likeCount by rememberSaveable { mutableIntStateOf(schedule.likesCount ?: 0) }
    Column(
        modifier = Modifier
            .shadow(elevation = MyDimen.p4)
            .background(color = Color.White)
            .padding(vertical = MyDimen.p12)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MyDimen.p16,
                    bottom = MyDimen.p12,
                    end = MyDimen.p4
                )
        ) {
            UserAvatar(
                pathUrl = schedule.user?.avatar,
                isOnline = true
            )

            Spacer(modifier = Modifier.width(MyDimen.p12))

            Column(modifier = Modifier.weight(1f)) {
                CoreText(
                    text = schedule.user?.name,
                    style = CoreTypographyBold.labelLarge,
                    color = MyColor.TextColorPrimary
                )
                EnhancedTextSwitcher(
                    stringResource(R.string.lb_hint_for_you),
                    schedule.destinations?.firstOrNull()?.city ?: ""
                )
            }
            Button(
                onClick = {
                    navController.setArg(NavigateKeyArg.SCHEDULE_ID, schedule.id)
                    navController.add(CoreRoute.Schedule.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MyColor.SecondPrimary,
                    contentColor = MyColor.White
                ),
                shape = RoundedCornerShape(MyDimen.p20),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = MyDimen.p2,
                    pressedElevation = MyDimen.p6
                ),
                modifier = Modifier.height(MyDimen.p36)
            ) {
                CoreText(
                    text = stringResource(R.string.lb_see_detail),
                    style = CoreTypography.labelSmall,
                    color = MyColor.White
                )
            }

            Spacer(modifier = Modifier.width(MyDimen.p8))

            IconButton(
                onClick = { /* Handle more options */ },
                modifier = Modifier.size(MyDimen.p36)
            ) {
                CoreIcon(
                    imageVector = Icons.Default.MoreVert,
                    iconModifier = Modifier.size(MyDimen.p24)
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape
        ) {
            CoreImage(
                source = CoreImageSource.Url(
                    schedule.images?.firstOrNull() ?: SCHEDULE_TEMP_IMAGE_URL
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MyDimen.p280)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MyDimen.p16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CoreIcon(
                    resDrawable = if (hasLiked) R.drawable.ic_heart_solid else R.drawable.ic_heart,
                    tintColor = if (hasLiked) MyColor.Favorite else MyColor.Gray666,
                    iconModifier = Modifier
                        .scaleOnClick {
                            onLikeClick()
                            hasLiked = !hasLiked
                            likeCount += if (hasLiked) 1 else -1
                        }
                        .size(MyDimen.p24)
                )
                Spacer(modifier = Modifier.width(MyDimen.p6))
                if (likeCount != 0) {
                    CoreText(
                        text = likeCount.formatThousandK(),
                        style = CoreTypography.labelMedium,
                        color = MyColor.TextColorLight
                    )
                }
            }

            Spacer(modifier = Modifier.width(MyDimen.p12))

            // Comment button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickNoRipple { onCommentClick() }
            ) {
                CoreIcon(
                    resDrawable = R.drawable.ic_comment_dots,
                    iconModifier = Modifier.size(MyDimen.p24),
                )
                Spacer(modifier = Modifier.width(MyDimen.p6))
                if (schedule.commentsCount != 0) {
                    CoreText(
                        text = schedule.commentsCount.toString(),
                        style = CoreTypography.labelMedium,
                        color = MyColor.TextColorLight,
                    )
                }
            }

            SpaceWidth(MyDimen.p12)

            // Share button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickNoRipple { onShareClick() }
            ) {
                CoreIcon(
                    resDrawable = R.drawable.ic_share,
                    iconModifier = Modifier.size(MyDimen.p24),
                )
                SpaceWidth(MyDimen.p6)
                if (schedule.sharesCount != 0) {
                    CoreText(
                        text = schedule.sharesCount.toString(),
                        style = CoreTypography.labelMedium,
                        color = MyColor.TextColorLight,
                    )
                }
            }
            ExpandableSpacer()

            // Favorite button
            IconButton(
                onClick = {
                    onFavoriteClick()
                    hasFavorite = !hasFavorite
                },
                modifier = Modifier.size(MyDimen.p40)
            ) {
                CoreIcon(
                    resDrawable = if (hasFavorite) R.drawable.ic_favorite_star_solid
                    else R.drawable.ic_favorite_star,
                    tintColor = if (hasFavorite) MyColor.Rating else MyColor.Gray666,
                    iconModifier = Modifier.size(MyDimen.p24)
                )
            }
        }
        EnhancedPostContent(
            title = schedule.name.orEmpty(),
            description = schedule.description.orEmpty()
        )
    }
}

@Composable
fun EnhancedTextSwitcher(
    firstLabel: String = "",
    secondLabel: String = ""
) {
    var isFirstVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            isFirstVisible = !isFirstVisible
        }
    }

    AnimatedContent(
        targetState = isFirstVisible,
        transitionSpec = {
            (fadeIn(animationSpec = tween(500)) +
                    slideInVertically(animationSpec = tween(500)) { it / 2 }).togetherWith(
                fadeOut(animationSpec = tween(500)) +
                        slideOutVertically(animationSpec = tween(500)) { -it / 2 }
            )
        },
        label = "TextSwitcherAnimation"
    ) { targetState ->
        if (targetState) {
            CoreText(
                text = firstLabel,
                style = CoreTypography.labelSmall,
                color = MyColor.TextColorLight
            )
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ic_location_outline_24dp),
                    contentDescription = "Location",
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(MyDimen.p14)
                )
                SpaceWidth(MyDimen.p4)
                CoreText(
                    text = secondLabel,
                    style = CoreTypography.labelSmall,
                    color = MyColor.TextColorLight
                )
            }
        }
    }
}

@Composable
fun EnhancedPostContent(
    title: String = "Tour Hà Nội",
    description: String = "Đây là tour Hà Nội 2 ngày một đêm. Tour này rất thú vị với nhiều điểm tham quan hấp dẫn. Hãy cùng khám phá những địa danh nổi tiếng tại Hà Nội cùng chúng tôi trong hành trình này. Chúng ta sẽ đi qua nhiều địa điểm văn hóa lịch sử độc đáo."
) {
    var isExpanded by remember { mutableStateOf(false) }
    val maxPreviewLength = 120

    Column(modifier = Modifier.padding(horizontal = MyDimen.p16)) {
        CoreText(
            text = title,
            style = CoreTypographyBold.displayLarge,
        )

        SpaceHeight(MyDimen.p8)

        AnimatedContent(
            targetState = isExpanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)).togetherWith(
                    fadeOut(animationSpec = tween(300))
                )
            }
        ) { expanded ->
            CoreText(
                text = if (expanded) description
                else description.take(maxPreviewLength) + if (description.length > maxPreviewLength) "..." else "",
                style = CoreTypography.labelMedium,
                lineHeight = 20.sp
            )
        }

        if (description.length > maxPreviewLength) {
            SpaceHeight(MyDimen.p6)
            CoreText(
                text = if (isExpanded) stringResource(R.string.lb_btn_core_see_less)
                else stringResource(R.string.lb_btn_core_see_more),
                style = CoreTypography.labelSmall,
                color = MyColor.Primary,
                modifier = Modifier.clickable {
                    isExpanded = !isExpanded
                }
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(animationSpec = spring()) + fadeIn(),
            exit = shrinkVertically(animationSpec = spring()) + fadeOut()
        ) {
            Column {
                Spacer(modifier = Modifier.height(MyDimen.p16))

                CoreText(
                    text = stringResource(R.string.lb_hot_destination),
                    style = CoreTypographySemiBold.displayMedium,
                )

                SpaceHeight(MyDimen.p8)

                HorizontalList(
                    items = listOf(1, 2, 3, 4),
                    horizontalArrangement = Arrangement.spacedBy(MyDimen.p8),
                ) {
                    EnhancedHotLocation()
                }

                SpaceHeight()

                Card(
                    colors = CardDefaults.cardColors(containerColor = MyColor.GrayF5),
                    shape = RoundedCornerShape(MyDimen.p8)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MyDimen.p12)
                    ) {
                        CoreText(
                            text = stringResource(R.string.lb_total_cost),
                            style = CoreTypography.labelMedium,
                            color = MyColor.TextColorLight
                        )
                        Spacer(modifier = Modifier.width(MyDimen.p8))
                        CoreText(
                            text = "2.500.000 đ",
                            style = CoreTypographyBold.displayLarge,
                            color = MyColor.Primary
                        )
                    }
                }
            }
        }

        SpaceHeight(MyDimen.p12)

        CoreText(
            text = "24 tháng 12, 2024",
            style = CoreTypography.displaySmall,
            color = MyColor.TextColorLight
        )
    }
}

@Composable
fun EnhancedHotLocation(locationName: String = "Hồ Hoàn Kiếm") {
    Card(
        colors = CardDefaults.cardColors(containerColor = MyColor.White),
        shape = RoundedCornerShape(MyDimen.p20),
        border = BorderStroke(MyDimen.p1, MyColor.GrayF5),
        elevation = CardDefaults.cardElevation(defaultElevation = MyDimen.p1)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = MyDimen.p12, vertical = MyDimen.p8)
        ) {
            CoreImage(
                source = CoreImageSource.Url("https://tourhue.vn/wp-content/uploads/2024/07/di-hue-mac-gi-chup-anh-dep-2.jpg"),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(MyDimen.p28)
                    .clip(CircleShape)
            )
            SpaceWidth8()
            CoreText(
                text = locationName,
                style = CoreTypography.displaySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}