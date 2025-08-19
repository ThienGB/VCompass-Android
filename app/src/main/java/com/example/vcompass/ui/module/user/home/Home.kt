package com.example.vcompass.ui.module.user.home

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.vcompass.R
import com.example.vcompass.ui.custom_property.clickableNoEffect
import com.example.vcompass.ui.custom_property.clickableWithScale
import com.example.vcompass.ui.module.user.schedule.ScheduleActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Preview(showSystemUi = true)
@Composable
fun HomeScreen() {
    val scrollState = rememberLazyListState()
    var isHeaderVisible by remember { mutableStateOf(true) }
    val listItem = remember { listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(top = 40.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                listItem.size,
                key = { listItem.get(index = it) },
                contentType = { "post" }) { index ->
                Poster(index)
            }
        }
        AnimatedVisibility(
            visible = isHeaderVisible,
            enter = slideInVertically { -it },
            exit = slideOutVertically { -it },
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.White)
            .padding(end = 10.dp, top = 5.dp, bottom = 5.dp)
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
    item: Int
) {
    var isFavorited by rememberSaveable { mutableStateOf(false) }
    var isLiked by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val scheduleId = "123"
    Column(
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp)
                .height(35.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.img_hue)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
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
            Box(
                modifier = Modifier
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
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.img_food_service)
                .memoryCacheKey("img_food_service")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(if (isLiked) R.drawable.ic_heart_solid else R.drawable.ic_heart),
                contentDescription = null,
                colorFilter = if (isLiked) ColorFilter.tint(Color.Red) else ColorFilter.tint(Color.Black),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(20.dp)
                    .clickableWithScale(0.8f) { isLiked = !isLiked }
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "19.5K", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(R.drawable.ic_comment_dots),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "19", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(R.drawable.ic_share),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "12", fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(if (isFavorited) R.drawable.ic_favorite_star_solid else R.drawable.ic_favorite_star),
                contentDescription = null,
                colorFilter = if (isFavorited) ColorFilter.tint(colorResource(R.color.first_ranking)) else ColorFilter.tint(
                    Color.Black
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(20.dp)
                    .clickableNoEffect { isFavorited = !isFavorited }
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

//    LaunchedEffect(Unit) {
//        while (true) {
//            delay(3000)
//            isFirstVisible = !isFirstVisible
//        }
//    }
    AnimatedContent(
        targetState = isFirstVisible,
        transitionSpec = {
            (fadeIn(animationSpec = tween(400)) + slideInVertically(animationSpec = tween(400))).togetherWith(
                fadeOut(animationSpec = tween(400)) + slideOutVertically(
                    animationSpec = tween(400)
                )
            )
        }, label = "TextSwitcherAnimation"
    ) { targetState ->
        if (targetState) {
            Text(
                text = firstLabel,
                fontWeight = FontWeight.Light,
                fontSize = 13.sp
            )
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
    var isExpanded by remember { mutableStateOf(false) }

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
        Text(
            text = if (isExpanded) "Thu gọn" else "Xem thêm",
            fontSize = 13.sp,
            color = colorResource(id = R.color.primary),
            modifier = Modifier.clickable {
                isExpanded = !isExpanded
            }
        )
        if (isExpanded) {
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
fun HotLocation() {
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
        Row(verticalAlignment = Alignment.CenterVertically) {
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
