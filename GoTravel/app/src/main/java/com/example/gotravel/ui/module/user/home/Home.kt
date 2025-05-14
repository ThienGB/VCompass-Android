package com.example.gotravel.ui.module.user.home

import android.content.Intent
import android.graphics.Typeface.BOLD
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.gotravel.R
import com.example.gotravel.ui.module.user.schedule.ScheduleActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
) {
    val scrollState = rememberLazyListState()
    var isHeaderVisible by remember { mutableStateOf(true) }

    var previousOffset by remember { mutableStateOf(0) }

    // Detect scroll direction
    LaunchedEffect(scrollState.firstVisibleItemScrollOffset) {
        val currentOffset = scrollState.firstVisibleItemScrollOffset
        isHeaderVisible = currentOffset <= previousOffset
        previousOffset = currentOffset
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        // Content Scrollable area
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize() // Add padding to prevent content overlap with the header
        ) {
            items(5) { index ->
                Poster(navController)
            }
        }

        // Header - Fixed on top with animation
        AnimatedVisibility(
            visible = isHeaderVisible,
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it })
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .align(Alignment.TopCenter)
                    .zIndex(1f)
            ) {
                HomeHeader()
            }
        }
    }
}

@Composable
fun HomeHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 10.dp, top = 5.dp, bottom = 5.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo_vcompass),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(190.dp)
                .height(40.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_noti),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = painterResource(R.drawable.ic_chat),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun Poster(
    navController: NavController = rememberNavController()
){
    var iconFavorite by remember { mutableStateOf(R.drawable.ic_mark) }
    var isLiked by remember { mutableStateOf(false) }
    val scale = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()
    fun onFavoriteClick() {
        iconFavorite = if (iconFavorite == R.drawable.ic_mark) {
            R.drawable.ic_marked
        } else {
            R.drawable.ic_mark
        }
    }
    val context = LocalContext.current
    val scheduleId = "123"
    var iconLike by remember { mutableStateOf(R.drawable.ic_favorite) }
    fun onLikeClick() {
        iconLike = if (iconLike == R.drawable.ic_favorited) {
            R.drawable.ic_favorite
        } else {
            R.drawable.ic_favorited
        }
        coroutineScope.launch {
            scale.animateTo(
                targetValue = 1.2f, // Nở ra
                animationSpec = tween(durationMillis = 150)
            )
            scale.animateTo(
                targetValue = 1f, // Co lại
                animationSpec = tween(durationMillis = 150)
            )
        }
    }
    Column(
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp)
                .height(35.dp)
        ){
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.img_hue),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = "Thiện",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                TextSwitcher("Gợi ý cho bạn", "Hà Nội")
            }

            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier
                .height(28.dp)
                .wrapContentWidth()
                .background(
                    color = colorResource(id = R.color.colorSeparator80),
                    shape = RoundedCornerShape(7.dp)
                )
                .padding(horizontal = 13.dp)
                .clickable {
                    val intent = Intent(context, ScheduleActivity::class.java)
                    intent.putExtra("scheduleId", scheduleId)
                    context.startActivity(intent)
                },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Xem chi tiết", fontSize = 15.sp)
            }
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                painter = painterResource(R.drawable.ic_vertical_dot),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(25.dp)
                    .width(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Image(
            painter = painterResource(R.drawable.img_ha_noi),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(iconLike),
                contentDescription = null,
                colorFilter = if (iconLike == R.drawable.ic_favorited) {
                    ColorFilter.tint(Color.Red)
                } else {
                    ColorFilter.tint(Color.Black)
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(30.dp)
                    .graphicsLayer(
                        scaleX = scale.value,
                        scaleY = scale.value
                    )
                    .clickable { onLikeClick() }
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "19.5K", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(R.drawable.ic_comment),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "19", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(R.drawable.ic_share),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "12", fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(iconFavorite),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onFavoriteClick() }
            )
        }
        PostContent()
    }
}
@Composable
fun TextSwitcher(
    firstLabel: String = "",
    secondLabel: String = ""
) {
    var isFirstVisible by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            isFirstVisible = !isFirstVisible
        }
    }
    AnimatedContent(
        targetState = isFirstVisible,
        transitionSpec = {
            (fadeIn(animationSpec = tween(400)) + slideInVertically(animationSpec = tween(400))).togetherWith(
                fadeOut(animationSpec = tween(400)) + slideOutVertically(
                    animationSpec = tween(400)
                )
            )
        }, label = ""
    ) { targetState ->
        if (targetState) {
            Text(
                text = firstLabel,
                fontWeight = FontWeight.Light,
                fontSize = 13.sp
            )
        } else {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.ic_aim),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = secondLabel,
                    fontWeight = FontWeight.Light,
                    fontSize = 13.sp
                )
            }
        }
    }
}
@Composable
fun PostContent(
    title: String = "Tour Ha Noi",
    description: String = "Day la tour Ha Noi 2 ngay mot dem. Tour này rất thú vị với nhiều điểm tham quan hấp dẫn. Hãy cùng khám phá những địa danh nổi tiếng tại Hà Nội cùng chúng tôi trong hành trình này."
) {
    var isExpanded by remember { mutableStateOf(false) } // Trạng thái hiển thị toàn bộ nội dung hay không

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = if (isExpanded) description else description.take(100) + "...",
            fontWeight = FontWeight.Light,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Nút "Xem thêm" hoặc "Thu gọn"
        Text(
            text = if (isExpanded) "Thu gọn" else "Xem thêm",
            fontSize = 13.sp,
            color = colorResource(id = R.color.primary),
            modifier = Modifier.clickable { isExpanded = !isExpanded } // Thay đổi trạng thái khi nhấn
        )
        if (isExpanded){
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Điểm tham quan nổi bật ",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                HotLocation()
                HotLocation()
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Tổng chi phí:",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "2.500.000 đ",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.primary)
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "24 tháng 12, 2024",
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun HotLocation(){
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.img_hue),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Hồ Hoàn Kiếm",
                fontWeight = FontWeight.W400,
                color = Color.DarkGray,
                fontSize = 13.sp
            )
        }
    }
}
