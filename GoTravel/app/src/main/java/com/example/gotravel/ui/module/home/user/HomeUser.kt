package com.example.gotravel.ui.module.home.user

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.BottomSheetDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gotravel.R
import com.example.gotravel.data.model.Search
import com.example.gotravel.helper.CommonUtils.formatDate
import com.example.gotravel.ui.module.main.user.MainUserViewModel
import com.example.gotravel.ui.module.search.BottomSheetType
import com.example.gotravel.ui.module.search.DatePickerBottomSheet
import com.example.gotravel.ui.module.search.DestinationSelectionBottomSheet
import com.example.gotravel.ui.module.search.PriceRangeBottomSheet
import com.example.gotravel.ui.module.search.RoomGuestSelectionBottomSheet
import com.example.gotravel.ui.module.search.ShareSearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUserScreen(
    navController: NavHostController,
    viewModel: MainUserViewModel,
    context: Context,
) {
    viewModel.setIsShowBottomBar(true)
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
    Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.bg_home),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            )
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .padding(top = 100.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    SearchInfor(
                        title = "Điểm đến - khách sạn",
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
                                    Toast.makeText(context, "Vui lòng chọn đủ thông tin", Toast.LENGTH_SHORT).show()
                            }
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .background(colorResource(id = R.color.primary))
                            .padding(10.dp)
                    )
                }
            }
        }
        RecentSearches()
    }
    if (currentSheet.value != BottomSheetType.NONE){
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                if (currentSheet.value != BottomSheetType.NONE){
                    Box(
                        modifier = Modifier.wrapContentHeight().background(Color.White)
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
        .padding(vertical = 8.dp)
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
                fontSize = 16.sp
            )
        }
        Text(
            text = if (isValid) content else "Không thể trống",
            fontSize = 18.sp,
            color = if (isValid) colorResource(id = R.color.secondBlack) else Color.Red,
            fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
            modifier = Modifier.padding(bottom = 3.dp, top = 10.dp, start = 20.dp)
        )
        HorizontalDivider(thickness = 0.5.dp)
    }
}

@Composable
fun RecentSearches() {
    val searchItems = listOf(
        Search(
            "Vũng Tàu",
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            2,
            2,
            2,
            1000000),
        Search(
            "Phan Thiet",
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            2,
            4,
            2,
            1000000),)
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

