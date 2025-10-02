package com.example.vcompass.ui.module.user.home

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vcompass.R
import com.example.vcompass.ui.module.user.schedule.ScheduleActivity
import com.example.vcompass.util.rippleClickable
import com.example.vcompass.util.scaleOnClick
import kotlinx.coroutines.delay

@Preview(showSystemUi = true)
@Composable
fun HomeScreen() {
    val scrollState = rememberLazyListState()
    val listItems = remember { (1..10).toList() }
    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFAFAFA),
                        Color(0xFFF5F5F5)
                    )
                )
            )
            .fillMaxSize()
    ) {
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(
                top = 72.dp,
                bottom = 56.dp
            ),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = listItems,
                key = { it }
            ) { index ->
                TravelPost(
                    postId = index,
                    modifier = Modifier
                        .shadow(elevation = 4.dp)
                        .background(color = Color.White)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .zIndex(1f)
        ) {
            HomeHeader()
        }
    }
}

@Composable
fun HomeHeader() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .statusBarsPadding()
                .padding(bottom = 6.dp, end = 16.dp)
        )
        {
            // Logo with better scaling
            Image(
                painter = painterResource(R.drawable.logo_vcompass),
                contentDescription = "VCompass Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(40.dp)
                    .width(160.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Action buttons with ripple effects
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .rippleClickable {}
                        .size(36.dp)
                        .background(
                            color = Color(0xFFF0F0F0),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_noti),
                        contentDescription = "Notifications",
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF333333)
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .rippleClickable {}
                        .size(36.dp)
                        .background(
                            color = Color(0xFFF0F0F0),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_chat),
                        contentDescription = "Messages",
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF333333)
                    )
                }
            }
        }
    }
}

@Composable
fun TravelPost(
    postId: Int,
    modifier: Modifier = Modifier
) {
    var isFavorited by rememberSaveable { mutableStateOf(false) }
    var isLiked by rememberSaveable { mutableStateOf(false) }
    var likeCount by rememberSaveable { mutableIntStateOf(19500) }
    val context = LocalContext.current
    val scheduleId = "123"
    val animatedLikeCount by animateIntAsState(
        targetValue = likeCount,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    Column(modifier = modifier.padding(vertical = 12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 12.dp)
        ) {
            // Profile picture with online indicator
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.img_hue)
                        .crossfade(400)
                        .build(),
                    contentDescription = "Profile picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = Color(0xFFE0E0E0),
                            shape = CircleShape
                        )
                )
                // Online indicator
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = Color(0xFF4CAF50),
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = CircleShape
                        )
                        .align(Alignment.BottomEnd)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Thiện",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1A1A1A)
                )
                EnhancedTextSwitcher("Gợi ý cho bạn", "Hà Nội")
            }

            // Action button with modern styling
            Button(
                onClick = {
                    val intent = Intent(context, ScheduleActivity::class.java)
                    intent.putExtra("scheduleId", scheduleId)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 6.dp
                ),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = "Xem chi tiết",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { /* Handle more options */ },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_vertical_dot),
                    contentDescription = "More options",
                    tint = Color(0xFF666666)
                )
            }
        }

        // Enhanced image with loading state
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.img_food_service)
                    .memoryCacheKey("img_food_service_$postId")
                    .crossfade(400)
                    .build(),
                contentDescription = "Travel destination",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )
        }

        // Enhanced interaction row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Like button with animation
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(
                        if (isLiked) R.drawable.ic_heart_solid else R.drawable.ic_heart
                    ),
                    contentDescription = "Like",
                    tint = if (isLiked) Color(0xFFE91E63) else Color(0xFF666666),
                    modifier = Modifier
                        .scaleOnClick() {
                            isLiked = !isLiked
                            likeCount += if (isLiked) 1 else -1
                        }
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = formatCount(animatedLikeCount),
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Comment button
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ic_comment_dots),
                    contentDescription = "Comments",
                    tint = Color(0xFF666666),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "19",
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Share button
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ic_share),
                    contentDescription = "Share",
                    tint = Color(0xFF666666),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "12",
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Favorite button
            IconButton(
                onClick = { isFavorited = !isFavorited },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(
                        if (isFavorited) R.drawable.ic_favorite_star_solid
                        else R.drawable.ic_favorite_star
                    ),
                    contentDescription = "Favorite",
                    tint = if (isFavorited) Color(0xFFFFC107) else Color(0xFF666666),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        EnhancedPostContent()
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
            Text(
                text = firstLabel,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = Color(0xFF666666)
            )
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ic_aim),
                    contentDescription = "Location",
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = secondLabel,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = Color(0xFF666666)
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

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1A1A1A)
        )

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedContent(
            targetState = isExpanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)).togetherWith(
                    fadeOut(animationSpec = tween(300))
                )
            }
        ) { expanded ->
            Text(
                text = if (expanded) description
                else description.take(maxPreviewLength) + if (description.length > maxPreviewLength) "..." else "",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFF333333),
                lineHeight = 20.sp
            )
        }

        if (description.length > maxPreviewLength) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = if (isExpanded) "Thu gọn" else "Xem thêm",
                fontSize = 13.sp,
                color = Color(0xFF1976D2),
                fontWeight = FontWeight.Medium,
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
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Điểm tham quan nổi bật",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF1A1A1A)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 0.dp)
                ) {
                    items(4) {
                        EnhancedHotLocation()
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Tổng chi phí:",
                            fontSize = 14.sp,
                            color = Color(0xFF666666)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "2.500.000 đ",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1976D2)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "24 tháng 12, 2024",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color(0xFF999999)
        )
    }
}

@Composable
fun EnhancedHotLocation(locationName: String = "Hồ Hoàn Kiếm") {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.img_hue)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = "Location image",
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = locationName,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// Utility function to format large numbers
private fun formatCount(count: Int): String {
    return when {
        count < 1000 -> count.toString()
        count < 1000000 -> String.format("%.1fK", count / 1000.0)
        else -> String.format("%.1fM", count / 1000000.0)
    }
}
