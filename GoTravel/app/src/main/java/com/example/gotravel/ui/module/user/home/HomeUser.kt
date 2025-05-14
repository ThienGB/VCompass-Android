package com.example.gotravel.ui.module.user.home

import android.content.Context
import android.support.annotation.DrawableRes
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gotravel.R
import com.example.gotravel.data.model.Search
import com.example.gotravel.helper.BottomSheetType
import com.example.gotravel.helper.CommonUtils.formatDate
import com.example.gotravel.ui.module.user.main.MainUserViewModel
import com.example.gotravel.ui.module.user.search.DatePickerBottomSheet
import com.example.gotravel.ui.module.user.search.DestinationSelectionBottomSheet
import com.example.gotravel.ui.module.user.search.PriceRangeBottomSheet
import com.example.gotravel.ui.module.user.search.RoomGuestSelectionBottomSheet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUserScreen(
    navController: NavHostController,
    viewModel: MainUserViewModel,
    context: Context,
) {
    viewModel.setIsShowBottomBar(true)
    var currentIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val currentSheet = remember { mutableStateOf(BottomSheetType.NONE) }
    val searchData by viewModel.searchData.collectAsState()
    var isValid by remember { mutableStateOf(true) }
    fun showBottomSheet(sheet: BottomSheetType) {
        currentSheet.value = sheet
        isValid = true
    }
    fun onDismiss() {
        currentSheet.value = BottomSheetType.NONE
    }
    fun validate() {
        isValid =  !(searchData.destination == ""
                || searchData.guests == 0
                || searchData.maxPrice == 0)
    }
    val imageList = listOf(
        R.drawable.bg_home4,
        R.drawable.bg_home1,
        R.drawable.bg_home2,
        R.drawable.bg_home3
    )
    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            currentIndex = (currentIndex + 1) % imageList.size
        }
    }
    LaunchedEffect(currentSheet.value) {
        if (currentSheet.value == BottomSheetType.NONE) {
            coroutineScope.launch {
                try {
                    bottomSheetScaffoldState.bottomSheetState.hide()
                } catch (_: IllegalStateException) {
                }
            }
        }
        else {
            Log.d("HomeUserScreen", "showBottomSheet: ${currentSheet.value}")
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }
    Column (modifier = Modifier
        .background(Color.White)
        .verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = imageList[currentIndex]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
            )
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .padding(top = 200.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    SearchInfor(
                        title = "Điểm đến",
                        content = searchData.destination,
                        icon = R.drawable.ic_aim,
                        onColumnClick = { showBottomSheet(BottomSheetType.DESTINATION_PICKER) },
                        isValid = searchData.destination != "" || isValid
                    )
                    SearchInfor(
                        title = "Ngày nhận - trả phòng",
                        content = formatDate(searchData.departureDate) +" - "+  formatDate(searchData.returnDate),
                        icon = R.drawable.ic_calendar,
                        onColumnClick = { showBottomSheet(BottomSheetType.DATE_PICKER) }

                    )
                    SearchInfor(
                        title = "Số đêm nghỉ",
                        content = searchData.vacationDays.toString() + " đêm",
                        icon = R.drawable.ic_moon
                    )
                    SearchInfor(
                        title = "Số khách",
                        content = searchData.rooms.toString() +" phòng, "+  searchData.guests+ " người",
                        icon = R.drawable.ic_people,
                        onColumnClick = { showBottomSheet(BottomSheetType.GUEST_PICKER) },
                        isValid = searchData.guests != 0 || isValid
                    )
                    SearchInfor(
                        title = "Giá tiền",
                        content = searchData.minPrice.toString() +"đ - "+  searchData.maxPrice+ "đ",
                        icon = R.drawable.ic_filter,
                        onColumnClick = { showBottomSheet(BottomSheetType.PRICE_PICKER) },
                        isValid = searchData.maxPrice != 0 || isValid
                    )
                    Text(
                        text = "Tìm kiếm",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .clickable {
                                viewModel.fetchData()
                                validate()
                                if (isValid) {
                                    viewModel.setIsShowBottomBar(false)
                                    navController.navigate("search")
                                } else
                                    Toast
                                        .makeText(
                                            context,
                                            "Vui lòng chọn đủ thông tin",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                            }
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .background(colorResource(id = R.color.primary))
                            .padding(10.dp)
                    )
                }
            }
        }
        RecentSearches { search -> viewModel.setSearchData(search) }
        PopularDestinations{ search -> viewModel.setSearchData(search) }
    }
    if (currentSheet.value != BottomSheetType.NONE){
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                if (currentSheet.value != BottomSheetType.NONE){
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .background(Color.White)
                    ) {
                        when (currentSheet.value) {
                            BottomSheetType.DATE_PICKER -> DatePickerBottomSheet(searchData, viewModel, context
                            ) { onDismiss() }
                            BottomSheetType.GUEST_PICKER -> RoomGuestSelectionBottomSheet(searchData, viewModel, context){ onDismiss() }
                            BottomSheetType.PRICE_PICKER -> PriceRangeBottomSheet(searchData, viewModel, context){ onDismiss() }
                            BottomSheetType.DESTINATION_PICKER -> DestinationSelectionBottomSheet(searchData, viewModel){ onDismiss() }
                            else -> {}
                        }
                    }
                }

            },
            sheetPeekHeight = 0.dp,
            content = {
            }
        )
    }
}

@Composable
fun SearchInfor(
    title: String,
    content: String,
    icon: Int,
    onColumnClick: () -> Unit = {},
    isValid: Boolean = true
) {
    Column(modifier = Modifier
        .padding(vertical = 6.dp)
        .clickable { onColumnClick() }) {
        Row {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(15.dp)
            )
            Text(
                text = title,
                fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                fontSize = 15.sp
            )
        }
        Text(
            text = if (isValid) content else "Không thể trống",
            fontSize = 16.sp,
            color = if (isValid) colorResource(id = R.color.secondBlack) else Color.Red,
            fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
            modifier = Modifier.padding(bottom = 3.dp, top = 8.dp, start = 20.dp)
        )
        HorizontalDivider(thickness = 0.5.dp)
    }
}

@Composable
fun RecentSearches(
    onSearchClick: (Search) -> Unit
) {
    val searchItems = listOf(
        Search(
            "Vũng Tàu",
            System.currentTimeMillis(),
            System.currentTimeMillis() + (24 * 60 * 60 * 1000),
            1,
            2,
            1,
            0,
            2000000),
        Search(
            "Hà Nội",
            System.currentTimeMillis(),
            System.currentTimeMillis() + (24 * 60 * 60 * 1000)*2,
            2,
            4,
            2,
            100000,
            600000),
        Search(
            "Huế",
            System.currentTimeMillis(),
            System.currentTimeMillis() + (24 * 60 * 60 * 1000)*3,
            3,
            2,
            1,
            100000,
            900000))
    Column {
        Text(
            text = "Tra cứu gần đây",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
            modifier = Modifier.padding(start = 16.dp, top = 8.dp ,bottom = 8.dp)
        )
        LazyRow {
            items(searchItems){item ->
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable { onSearchClick(item) }
                ) {
                    Column(modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                        .width(200.dp)
                        .height(70.dp)) {
                        Row {
                            Image(painter = painterResource(id = R.drawable.ic_hotel),
                                colorFilter = ColorFilter.tint(colorResource(id = R.color.primary)),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .size(18.dp))
                            Text(text = item.destination, fontSize = 16.sp, color = colorResource(id = R.color.primary),
                                fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                                modifier = Modifier.padding(bottom = 15.dp))
                        }
                        Text(text = formatDate(item.departureDate) + " - " + formatDate(item.returnDate), fontSize = 13.sp, color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                            modifier = Modifier.padding(bottom = 5.dp))
                        Text(text = item.rooms.toString() + " phòng, "+ item.guests +" người", fontSize = 13.sp, color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
                    }
                }
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun PopularDestinations(
    onColumnClick: (Search) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Điểm đến phổ biến",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
            modifier = Modifier.padding(top = 8.dp ,bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            DestinationCard(
                onColumnClick = onColumnClick,
                imageRes = R.drawable.img_vung_tau,
                title = "Vũng Tàu",
                description = "10+ khách sạn"
            )
            DestinationCard(
                onColumnClick = onColumnClick,
                imageRes = R.drawable.img_ha_noi,
                title = "Hà Nội",
                description = "10+ khách sạn"
            )
            DestinationCard(
                onColumnClick = onColumnClick,
                imageRes = R.drawable.img_hue,
                title = "Huế",
                description = "5+ khách sạn"
            )
            DestinationCard(
                onColumnClick = onColumnClick,
                imageRes = R.drawable.img_ha_long,
                title = "Hạ Long",
                description = "5+ khách sạn"
            )
        }
    }
}
@Composable
fun DestinationCard(
    onColumnClick: (Search) -> Unit = {},
    @DrawableRes imageRes: Int,
    title: String,
    description: String
) {
    val search = Search(destination = title)
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onColumnClick(search) }
    ) {
        // Hiển thị ảnh
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            colorResource(id = R.color.primary).copy(alpha = 0.9f)
                        ),
                        startY = 50f
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        ) {
            Text(
                text = title,
                color = Color.White,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = description,
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


