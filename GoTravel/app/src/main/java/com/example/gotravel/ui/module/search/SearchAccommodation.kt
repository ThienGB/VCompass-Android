package com.example.gotravel.ui.module.search

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.gotravel.R
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Search
import com.example.gotravel.helper.CommonUtils.formatCurrency
import com.example.gotravel.helper.CommonUtils.formatDate
import com.example.gotravel.ui.components.Loading
import com.example.gotravel.ui.components.NavTitle
import com.example.gotravel.ui.module.main.user.MainUserViewModel

@Composable
fun SearchAccommodation(
    navController: NavController = NavController(LocalContext.current),
    accommodations: List<Accommodation> = listOf(),
    searchData: Search = Search(),
    isLoading: Boolean = true,
    viewModel: MainUserViewModel = viewModel())
{
    viewModel.setIsShowBottomBar(false)
    Column {
        NavTitle(searchData.destination) { navController.navigate("home") }
        NavSearch(navController, accommodations, searchData, isLoading, viewModel)
    }
}

@Composable
fun NavSearch(
    navController: NavController = NavController(LocalContext.current),
    accommodations: List<Accommodation> = listOf(),
    searchData: Search = Search(),
    isLoading: Boolean = true,
    viewModel: MainUserViewModel = viewModel()
){
    val sortOption = remember { mutableStateOf("Giá thấp nhất") }
    fun sortData(option: String): List<Accommodation> {
        return when (option) {
            "Giá thấp nhất" -> accommodations.sortedBy { it -> it.rooms.minOfOrNull { it.price } ?: 0 }
            "Giá cao nhất" -> accommodations.sortedByDescending { it -> it.rooms.minOfOrNull { it.price } ?: 0 }
            "Phổ biến nhất" -> accommodations.sortedByDescending { it.ratings.size }
            "Đánh giá cao nhất" -> accommodations.sortedByDescending { it.totalRate }
            else -> accommodations
        }
    }
    val sortedList = sortData(sortOption.value)
    Column (modifier = Modifier.background(Color.White)) {
        HorizontalDivider(thickness = 0.3.dp, color = Color.White)
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.primary))
            .indication(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .clickable {
                viewModel.setIsShowBottomBar(true)
                navController.navigate("home")
            }){
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 15.dp)) {
                Image(painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .padding(end = 5.dp, top = 8.dp, bottom = 8.dp)
                        .size(30.dp)
                )
                Text(text = formatDate(searchData.departureDate), color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
                Spacer(modifier = Modifier.weight(1f))
                Image(painter = painterResource(id = R.drawable.ic_moon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .padding(start = 12.dp, end = 5.dp, top = 8.dp, bottom = 8.dp)
                        .size(30.dp)
                )
                Text(text = searchData.vacationDays.toString(), color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
                Image(painter = painterResource(id = R.drawable.ic_door),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .padding(start = 12.dp, end = 5.dp, top = 8.dp, bottom = 8.dp)
                        .size(30.dp)
                )
                Text(text = searchData.rooms.toString(), color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
                Image(painter = painterResource(id = R.drawable.ic_people),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .padding(start = 12.dp, end = 5.dp, top = 8.dp, bottom = 8.dp)
                        .size(30.dp)
                )
                Text(text = searchData.guests.toString(), color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {
                    sortOption.value = when (sortOption.value) {
                        "Giá thấp nhất" -> "Giá cao nhất"
                        "Giá cao nhất" -> "Phổ biến nhất"
                        "Phổ biến nhất" -> "Đánh giá cao nhất"
                        else -> "Giá thấp nhất"
                    }
                },
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, colorResource(id = R.color.primary)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorResource(id = R.color.primary)
                ),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = "Filter",
                    tint = colorResource(id = R.color.primary),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = sortOption.value,
                    color = colorResource(id = R.color.primary),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    if (isLoading){
        Loading()
    }else {
        if (accommodations.isEmpty()){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.ic_no_search),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .size(150.dp)
                )
                Text(text = "Không có kết quả nào phù hợp",
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                    fontSize = 20.sp)
            }
        }else {
            Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
                sortedList.forEach { accommodation ->
                    HotelCard(accommodation, viewModel, navController)
                }
            }
        }

    }

}

@Composable
fun HotelCard(
    accommodation: Accommodation = Accommodation(),
    viewModel: MainUserViewModel,
    navController: NavController
) {
    val lowestPrice = accommodation.rooms.minOfOrNull { it.price } ?: 0
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                viewModel.setAccommodation(accommodation)
                navController.navigate("accom_detail")
            }
            .padding(10.dp)
            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = accommodation.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(140.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .height(130.dp)
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = accommodation.name,
                    fontSize = 17.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(accommodation.totalRate) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color.Yellow,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    repeat(5 - accommodation.totalRate) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(painter = painterResource(id = R.drawable.ic_rating),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.primary)),
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Text(
                        text = accommodation.ratings.size.toString(),
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Text(
                    text = accommodation.address,
                    maxLines = 2,
                    color = colorResource(id = R.color.primary),
                    overflow = TextOverflow.Ellipsis,
                    fontStyle = FontStyle.Italic
                )
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = formatCurrency(lowestPrice.toString()) + " đ",
                        color = colorResource(id = R.color.primary),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun PriceRangeBottomSheet(
    searchData: Search = Search(),
    sharedViewModel: MainUserViewModel,
    context: Context = LocalContext.current,
    onDismiss: () -> Unit = {},
) {
    val minPrice = 0f
    val maxPrice = 2_000_000f
    var currentPrice by remember { mutableStateOf(
        searchData.minPrice.toFloat()..searchData.maxPrice.toFloat()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Chọn giá",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        HorizontalDivider(modifier = Modifier.padding(top =5.dp ,bottom = 10.dp))
        RangeSlider(
            value = currentPrice,
            onValueChange = { newValue ->
                currentPrice = newValue
            },
            valueRange = minPrice..maxPrice,
            steps = 9,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Giá: Từ ${currentPrice.start.toInt()} đ - Đến ${currentPrice.endInclusive.toInt()} đ",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = {
                if (currentPrice.endInclusive.toInt() == 0) {
                    Toast.makeText(context, "Vui lòng chọn cả giá thấp và giá cao", Toast.LENGTH_SHORT).show()
                }else {
                    searchData.minPrice = currentPrice.start.toInt()
                    searchData.maxPrice = currentPrice.endInclusive.toInt()
                    sharedViewModel.setSearchData(searchData)
                    onDismiss()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Xác nhận")
        }
    }
}
@Composable
fun RoomGuestSelectionBottomSheet(
    searchData: Search = Search(),
    sharedViewModel: MainUserViewModel,
    context: Context = LocalContext.current,
    onDismiss: () -> Unit = {}
) {
    val guests = remember { mutableIntStateOf(searchData.guests) }
    val rooms = remember { mutableIntStateOf(searchData.rooms) }
    fun handleAdd(type: String) {
        if (type == "room") {
            if (rooms.intValue >= 4) {
                Toast.makeText(context, "Số phòng đã đạt giới hạn ", Toast.LENGTH_SHORT).show()
                return
            }
            rooms.intValue += 1
        }
        else {
            if (guests.intValue >= 16) {
                Toast.makeText(context, "Số người đã đạt giới hạn ", Toast.LENGTH_SHORT).show()
                return
            }
            guests.intValue += 1
        }
    }
    fun handleMinus(type: String) {
        if (type == "room") {
            if (rooms.intValue <= 0) {
                Toast.makeText(context, "Số phòng phải khác 0 ", Toast.LENGTH_SHORT).show()
                return
            }
            rooms.intValue -= 1
        }
        else {
            if (guests.intValue <= 0) {
                Toast.makeText(context, "Số ngườ phải khác 0 ", Toast.LENGTH_SHORT).show()
                return
            }
            guests.intValue -= 1
        }

    }
    Column(modifier = Modifier.padding(16.dp)) {
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =Arrangement.Center) {
            Text(text = "Chọn số phòng và khách", style = MaterialTheme.typography.titleMedium)
        }
        HorizontalDivider(modifier = Modifier.padding(top =5.dp ,bottom = 10.dp))
        Text(text = "Tối đa 4 phòng, 16 nguời lớn")
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.ic_door),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 5.dp, top = 8.dp, bottom = 8.dp)
                    .size(20.dp)
            )
            Text(text = "Phòng", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Row (verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {handleMinus("room") }) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove")
                }
                Text(text = rooms.intValue.toString())
                IconButton(onClick = {handleAdd("room") }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.ic_people),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 5.dp, top = 8.dp, bottom = 8.dp)
                    .size(20.dp)
            )
            Text(text = "Số nguười: ", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Row (verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { handleMinus("guest") }) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove")
                }
                Text(text = guests.value.toString())
                IconButton(onClick = { handleAdd("guest") }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(end = 15.dp),
            horizontalArrangement = Arrangement.End) {
            Button(onClick = {
                if (rooms.intValue == 0 ||  guests.intValue == 0) {
                    Toast.makeText(context, "Số phòng và khách phải khác 0 ", Toast.LENGTH_SHORT).show()
                } else if (rooms.intValue > guests.intValue) {
                    Toast.makeText(context, "Số khách không được vượt quá số phòng", Toast.LENGTH_SHORT).show()
                } else {
                    searchData.rooms = rooms.intValue
                    searchData.guests = guests.intValue
                    sharedViewModel.setSearchData(searchData)
                    onDismiss() }
                })
                 {
                Text(text = "Xác nhận")
            }
        }
    }
}
@Composable
fun DestinationSelectionBottomSheet(
    searchData: Search = Search(),
    sharedViewModel: MainUserViewModel,
    onDismiss: () -> Unit = {}
) {
    val text = remember { mutableStateOf("") }
    val provinces = listOf(
        "Hà Nội", "Hồ Chí Minh", "Đà Nẵng", "Hải Phòng", "Cần Thơ",
        "Nha Trang", "Bà Rịa - Vũng Tàu", "Quảng Ninh",
        "Khánh Hòa", "Bình Định", "Đắk Lắk", "Gia Lai",
        "Lâm Đồng", "Tây Ninh", "Bình Dương", "Đồng Tháp",
        "Vĩnh Long", "Ninh Thuận", "Bình Thuận", "Thái Nguyên",
        "Lào Cai", "Yên Bái", "Sơn La", "Hà Giang",
        "Nghệ An", "Thanh Hóa", "Quảng Trị", "Thừa Thiên Huế",
        "Bến Tre", "Kiên Giang", "Vĩnh Phúc", "Hải Dương"
    )
    val filteredProvinces = provinces.filter { province ->
        province.contains(text.value, ignoreCase = true)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(16.dp)
    ) {
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =Arrangement.Center) {
            Text(text = "Chọn điểm đến", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = text.value,
            onValueChange = { value -> text.value = value },
            placeholder = { Text("Nhập điểm đến") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Tìm kiếm")
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent),
        )

        Spacer(modifier = Modifier.height(16.dp))
        if (text.value == ""){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tìm kiếm gần đây", fontWeight = FontWeight.Bold)
                Text(
                    text = "Xóa",
                    color = Color.Blue,
                    modifier = Modifier.clickable {  }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                val recentSearches = listOf("Hà Nội, Việt Nam", "InterContinental ", "Khách sạn Pullman Vũng Tàu")
                recentSearches.forEach { destination ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                searchData.destination = destination
                                sharedViewModel.setSearchData(searchData)
                                onDismiss()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Home, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(destination, modifier = Modifier.weight(1f))
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Close, contentDescription = "Xóa")
                        }
                    }
                }
            }
        }else {
            Column (modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())) {
                filteredProvinces.forEach { province ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                searchData.destination = province
                                sharedViewModel.setSearchData(searchData)
                                onDismiss()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Home, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(province, modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 12.dp))
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBottomSheet(
    searchData: Search = Search(),
    sharedViewModel: MainUserViewModel,
    context: Context = LocalContext.current,
    onDismiss: () -> Unit = {}
) {
    val state = rememberDateRangePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val today = System.currentTimeMillis()
                return utcTimeMillis >= today
            }
        }
    )
    Column(modifier = Modifier
        .padding(16.dp)
        .wrapContentHeight())
    {
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =Arrangement.Center) {
            Text(text = "Chọn ngày nhận phòng", style = MaterialTheme.typography.titleMedium)
        }
        DateRangePicker(
            state = state,
            modifier = Modifier.height(450.dp),
            title = {},
            headline = {},
            showModeToggle = false
        )
    }
    Box (modifier = Modifier
        .fillMaxWidth()
        .height(550.dp)
        .padding(end = 15.dp, bottom = 15.dp),
        contentAlignment = Alignment.BottomEnd) {
        Button(onClick = {
            val startDateMillis = state.selectedStartDateMillis
            val endDateMillis = state.selectedEndDateMillis

            if (startDateMillis == null || endDateMillis == null) {
                Toast.makeText(context, "Vui lòng chọn cả ngày đi và ngày về", Toast.LENGTH_SHORT).show()
            } else {
                val days = ((endDateMillis - startDateMillis).toInt()) / 86400000
                if (days <= 10){
                    searchData.departureDate = startDateMillis
                    searchData.returnDate = endDateMillis
                    searchData.vacationDays = days
                    sharedViewModel.setSearchData(searchData)
                    onDismiss()
                }else {
                    Toast.makeText(context, "Vui lòng chọn tối đa 10 ngày", Toast.LENGTH_SHORT).show()
                }

            }
        }) {
            Text(text = "Xác nhận")
        }
    }
}
enum class BottomSheetType {
    NONE, DATE_PICKER, GUEST_PICKER, PRICE_PICKER, DESTINATION_PICKER
}
