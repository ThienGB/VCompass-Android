package com.example.gotravel.ui.module.home.user

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotravel.R
import com.example.gotravel.ui.module.search.BottomSheetManager
import com.example.gotravel.ui.module.search.BottomSheetType
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeUserScreen() {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .background(colorResource(id = R.color.lightGray))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchCard()
        RecentSearches()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCard() {
    val sheetState = rememberBottomSheetScaffoldState()
    val currentSheet = remember { mutableStateOf(BottomSheetType.NONE) }
    fun showBottomSheet(sheet: BottomSheetType) {
        currentSheet.value = sheet
    }
    Box(modifier = Modifier.fillMaxWidth()){
        Image(painter = painterResource(id = R.drawable.bg_home),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp))
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .padding(top = 100.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier
                .background(Color.White)
                .padding(16.dp)) {
                SearchInfor(title = "Điểm đến - khách sạn",
                    content = "Vũng Tàu",
                    icon = R.drawable.ic_location,
                    sheetState = sheetState,
                    onColumnClick = ::showBottomSheet,
                    bottomSheetType = BottomSheetType.NONE)
                SearchInfor(title = "Ngày nhận - trả phòng",
                    content = "02/02/2022 - 04/02/2022",
                    icon = R.drawable.ic_calendar,
                    sheetState = sheetState,
                    onColumnClick = ::showBottomSheet,
                    bottomSheetType = BottomSheetType.DATE_PICKER)
                SearchInfor(title = "Số đêm nghỉ",
                    content = "2 đêm",
                    icon = R.drawable.ic_moon)
                SearchInfor(title = "Số khách",
                    content = "2 phòng, 2 người lớn, 1 trẻ em",
                    icon = R.drawable.ic_people,
                    sheetState = sheetState,
                    onColumnClick = ::showBottomSheet,
                    bottomSheetType = BottomSheetType.ANOTHER_SHEET)
                SearchInfor(title = "Bộ lọc",
                    content = "1.500.000 đ - 2.000.000 đ, 5 sao",
                    icon = R.drawable.ic_filter,
                    sheetState = sheetState,
                    onColumnClick = ::showBottomSheet,
                    bottomSheetType = BottomSheetType.DATE_PICKER)

                Text(text = "Tìm kiếm",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .background(colorResource(id = R.color.primary))
                        .padding(10.dp))
            }
        }
    }
    BottomSheetManager(sheetState = sheetState, currentSheet = currentSheet.value)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInfor(
    title: String,
    content: String,
    icon: Int,
    sheetState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    onColumnClick: (BottomSheetType) -> Unit = {},
    bottomSheetType: BottomSheetType = BottomSheetType.NONE
){
    val coroutineScope = rememberCoroutineScope()
    Column (modifier = Modifier.padding(vertical = 8.dp)) {
        Row {
            Image(painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(15.dp)
                    .clickable {
                        coroutineScope.launch {
                            onColumnClick(bottomSheetType)
                            sheetState.bottomSheetState.expand()
                        }
                    })
            Text(text =title,
                fontFamily = FontFamily(Font(R.font.proxima_nova_regular)), fontSize = 16.sp)

        }
        Text(text = content, fontSize = 18.sp, color = colorResource(id = R.color.secondBlack),
            fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
            modifier = Modifier.padding(bottom = 3.dp, top = 10.dp, start = 20.dp))
        HorizontalDivider(thickness = 0.5.dp)
    }
}

data class Search(val destinations: String, val date: String, val nights: String, val people: String)
@Composable
fun RecentSearches() {
    val searchItems = listOf(Search("Vũng Tàu", "02/02/2022 - 04/02/2022", "2 đêm", "2 phòng, 2 người lớn, 1 trẻ em"),
        Search("Phan Thiết", "02/02/2022 - 04/02/2022", "2 đêm", "2 phòng, 2 người lớn, 1 trẻ em"),
        Search("Đà Nẵng", "02/02/2022 - 04/02/2022", "2 đêm", "2 phòng, 2 người lớn, 1 trẻ em"))
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
                            Text(text = item.destinations, fontSize = 16.sp, color = colorResource(id = R.color.primary),
                                fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                                modifier = Modifier.padding(bottom = 15.dp))
                        }
                        Text(text = item.date, fontSize = 13.sp, color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                            modifier = Modifier.padding(bottom = 5.dp))
                        Text(text = item.people, fontSize = 13.sp, color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
                    }
                }
            }
        }

    }
}


