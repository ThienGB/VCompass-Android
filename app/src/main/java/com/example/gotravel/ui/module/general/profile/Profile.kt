import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotravel.R

@Preview(showSystemUi = true)
@Composable
fun Profile(){
    Column(modifier = Modifier.background(Color.White)){
        AvatarInforSection()
        UserMoreInforSection()
    }


}
@Composable
fun AvatarInforSection(){
    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = CircleShape
                    )
                    .padding(4.dp)
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
            Spacer(modifier = Modifier.weight(1f))
            UserInforText("3", "Lịch trình")
            Spacer(modifier = Modifier.width(10.dp))
            UserInforText("10", "Người theo dõi")
            Spacer(modifier = Modifier.width(10.dp))
            UserInforText("30", "Theo dõi")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Công Thiện",
            fontWeight = FontWeight.W600,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = "I am a love traveling person!!!",
            fontWeight = FontWeight.W400,
            color = Color.DarkGray,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row () {
            Box(modifier = Modifier
                .height(28.dp)
                .weight(1f)
                .background(
                    color = colorResource(id = R.color.colorSeparator80),
                    shape = RoundedCornerShape(7.dp)
                )
                .padding(horizontal = 13.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Chỉnh sửa thông tin", fontSize = 15.sp, fontWeight = FontWeight.W500,)
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box(modifier = Modifier
                .height(28.dp)
                .weight(1f)
                .background(
                    color = colorResource(id = R.color.colorSeparator80),
                    shape = RoundedCornerShape(7.dp)
                )
                .padding(horizontal = 13.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Chia sẻ trang cá nhân", fontSize = 15.sp, fontWeight = FontWeight.W500,)
            }
        }
    }
}
@Composable
fun UserInforText(
    firstLabel: String = "",
    secondLabel: String = "",
    onClick: () -> Unit = {}
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = firstLabel,
            fontWeight = FontWeight.W800,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = secondLabel,
            color = Color.DarkGray,
            fontSize = 15.sp
        )
    }
}
@Composable
fun UserMoreInforSection(){
    val tabsName = listOf("Lịch trình", "Đặt phòng", "Yêu thích")
    val tabsIcon = listOf(R.drawable.ic_schedule, R.drawable.ic_booking_list, R.drawable.ic_saved_list)
    val tabsSelectedIcon = listOf(
        R.drawable.ic_schedule_selected,
        R.drawable.ic_booking_list_selected,
        R.drawable.ic_saved_list_selected
    )
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabsName.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Column (verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter =if (selectedTabIndex == index) {
                                    painterResource(tabsSelectedIcon[index])
                                } else {
                                    painterResource(tabsIcon[index])
                                },
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                                colorFilter = if (selectedTabIndex == index) {
                                    ColorFilter.tint(Color.Black)
                                } else {
                                    ColorFilter.tint(Color.Gray)
                                },
                                modifier = Modifier.size(30.dp)
                            )
                            Text(text = title, fontSize = 14.sp,
                                color = if (selectedTabIndex == index) Color.Black else Color.DarkGray)
                        }

                    }
                )
            }
        }

        when (selectedTabIndex) {
            1 -> MySchedule()
            2 -> MyBooking()
            0 -> MyFavorite()
        }
    }
}
@Composable
fun MySchedule(){
    val isHaveVideo = true
    val items = List(13) { "Item $it" }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 2.dp, start = 2.dp, end = 2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(items.size) { index ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(R.drawable.img_vung_tau),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter =if (isHaveVideo) {
                            painterResource(R.drawable.ic_video)
                        } else {
                            painterResource(R.drawable.ic_image)
                        },
                        colorFilter = ColorFilter.tint(Color.White),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(3.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MyBooking() {
    val items = remember { mutableStateListOf(*List(20) { "Item $it" }.toTypedArray()) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(items.size) { index ->
            val item = items[index]
            var offsetX by remember { mutableStateOf(0f) }
            val maxOffsetX = 380f // Maximum width of options
            val dismissThreshold = -maxOffsetX / 2 // Start showing options

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                // Reset or keep options revealed based on swipe
                                offsetX = if (offsetX < dismissThreshold) -maxOffsetX else 0f
                            }
                        ) { change, dragAmount ->
                            change.consume() // Consume event
                            offsetX = (offsetX + dragAmount).coerceIn(-maxOffsetX, 0f)
                        }
                    }
            ) {
                // Background options
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .background(Color.White),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column (horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth()) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .width(130.dp)
                            .background(
                                color = colorResource(id = R.color.primary),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Viết đánh giá ", fontSize = 15.sp,
                                fontWeight = FontWeight.W500, color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(modifier = Modifier
                            .weight(1f)
                            .width(130.dp)
                            .background(
                                color = colorResource(id = R.color.btn_fuel_check_text),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Hủy đặt phòng", fontSize = 15.sp,
                                fontWeight = FontWeight.W500, color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(modifier = Modifier
                            .weight(1f)
                            .width(130.dp)
                            .background(
                                color = colorResource(id = R.color.secondBlack),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Xóa", fontSize = 15.sp,
                                fontWeight = FontWeight.W500, color = Color.White)
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .offset { IntOffset(offsetX.toInt(), 0) }
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            colorResource(id = R.color.colorLightSeparator),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_ha_long),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(170.dp)
                            .padding(vertical = 15.dp, horizontal = 10.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = "The Song Yuki Apartment",
                            fontWeight = FontWeight.W600,
                            fontSize = 17.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        BookingInforText("Tên phòng:", "Căn Hộ 2 Phòng Ngủ")
                        BookingInforText("Ngày đặt:", "9/12/2024")
                        BookingInforText("Ngày nhận phòng:", "9/12/2024")
                        BookingInforText("Ngày trả phòng:", "13/12/2024")
                        BookingInforText("Số đêm:", "3")
                        BookingInforText("Tổng giá: ", "2799360 VND")
                        BookingInforText("Trạng thái: ", "Đã hết hạn")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}




@Composable
fun MyFavorite() {
    val tabs = listOf("Lịch trình" , "Khách sạn" , "Tham quan" , "Ăn uống")
    val tabsIcon = listOf(
        R.drawable.ic_favorite_schedule,
        R.drawable.ic_accommodation,
        R.drawable.ic_attraction,
        R.drawable.ic_foodservice)
    val tabsSelectedIcon = listOf(
        R.drawable.ic_favorite_schedule_selected,
        R.drawable.ic_accommodation_selected,
        R.drawable.ic_attraction_selected,
        R.drawable.ic_foodservice_selected
    )
    var selectedTabIndex by remember { mutableStateOf(0) }

    Row(modifier = Modifier.fillMaxSize()) {
        // Tab Column
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(70.dp)
                .background(colorResource(id = R.color.colorSeparator)),
            verticalArrangement = Arrangement.spacedBy(7.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(1.dp))
            tabs.forEachIndexed {index, title ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .clickable { selectedTabIndex = index }
                        .background(
                            if (selectedTabIndex == index) Color.White else Color.Transparent,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(vertical = 8.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = if (selectedTabIndex == index) {
                            painterResource(tabsSelectedIcon[index])
                        } else {
                            painterResource(tabsIcon[index])
                        },
                        contentDescription = null,
                        tint = if (selectedTabIndex == index) Color.Black else Color.DarkGray,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.W500,
                        color = if (selectedTabIndex == index) Color.Black else Color.DarkGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Content area
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(top = 5.dp, start = 5.dp, end = 5.dp)
        ) {
            when (selectedTabIndex) {
                0 -> MyFavoriteContent()
                1 -> Text("Nội dung của Tab 2", fontSize = 16.sp)
                2 -> Text("Nội dung của Tab 3", fontSize = 16.sp)
                3 -> Text("Nội dung của Tab 4", fontSize = 16.sp)
            }
        }
    }
}
@Composable
fun MyFavoriteContent(){
    val isFavorite = true
    val items = List(13) { "Item $it" }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 2.dp, start = 2.dp, end = 2.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(items.size) { index ->
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(id = R.color.colorSeparator)),
                contentAlignment = Alignment.TopCenter
            ) {
                Column {
                    Image(
                        painter = painterResource(R.drawable.img_vung_tau),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.padding(horizontal = 5.dp)) {
                        Text(
                            text = "Tour Hà Nội chi phí thấp",
                            fontWeight = FontWeight.W600,
                            fontSize = 17.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        BookingInforText("Địa điểm:", "Hà Nội")
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter =if (isFavorite) {
                            painterResource(R.drawable.ic_video)
                        } else {
                            painterResource(R.drawable.ic_image)
                        },
                        colorFilter = ColorFilter.tint(Color.White),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(3.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun BookingInforText(
    firstLabel: String = "",
    secondLabel: String = "",
){
    Row (verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = firstLabel,
            fontWeight = FontWeight.W500,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = secondLabel,
            fontWeight = FontWeight.W400,
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}
