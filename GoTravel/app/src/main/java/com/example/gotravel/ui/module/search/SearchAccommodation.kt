package com.example.gotravel.ui.module.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotravel.R
import com.example.gotravel.ui.components.NavTitle
import java.time.LocalDate

class SearchAccomodation {

}

@Preview(showBackground = true)
@Composable
fun PreviewSearch(){
    Column {
        NavTitle()
        NavSearch()
        NightSelectionBottomSheet()
    }
}

@Composable
fun NavSearch(){
    HorizontalDivider(thickness = 0.3.dp, color = Color.White)
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(colorResource(id = R.color.primary))){
        Row(verticalAlignment = Alignment.CenterVertically,) {
            Image(painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(start = 12.dp, end = 5.dp, top = 8.dp, bottom = 8.dp)
                    .size(30.dp)
            )
            Text(text = "01/01/1999", color = Color.White,
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
            Text(text = "2", color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
            Image(painter = painterResource(id = R.drawable.ic_door),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(start = 12.dp, end = 5.dp, top = 8.dp, bottom = 8.dp)
                    .size(30.dp)
            )
            Text(text = "2", color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
            Image(painter = painterResource(id = R.drawable.ic_people),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(start = 12.dp, top = 8.dp, bottom = 8.dp)
                    .size(30.dp)
            )
            Text(text = "2", color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))

            Image(painter = painterResource(id = R.drawable.ic_down_arrow),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(start = 20.dp, top = 8.dp, bottom = 8.dp, end = 5.dp)
                    .size(30.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomGuestSelectionBottomSheet() {
    val sheetState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Chọn số phòng và khách", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                // Phòng selection
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Phòng")
                    Spacer(modifier = Modifier.weight(1f))
                    Row {
                        IconButton(onClick = { /* Handle minus */ }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove")
                        }
                        Text(text = "1") // Số lượng phòng
                        IconButton(onClick = { /* Handle add */ }) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                }

                // Người lớn selection
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Người lớn (Từ 18 tuổi)")
                    Spacer(modifier = Modifier.weight(1f))
                    Row {
                        IconButton(onClick = { /* Handle minus */ }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove")
                        }
                        Text(text = "1") // Số lượng người lớn
                        IconButton(onClick = { /* Handle add */ }) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                }

                // Trẻ em selection
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Trẻ em (Từ 0 đến 17 tuổi)")
                    Spacer(modifier = Modifier.weight(1f))
                    Row {
                        IconButton(onClick = { /* Handle minus */ }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove")
                        }
                        Text(text = "0") // Số lượng trẻ em
                        IconButton(onClick = { /* Handle add */ }) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /* Close bottom sheet */ }) {
                    Text(text = "Hoàn tất")
                }
            }
        },
        sheetPeekHeight = 0.dp // Ẩn BottomSheet khi không hiển thị
    ) {
        // Nội dung chính của màn hình
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NightSelectionBottomSheet() {
    val sheetState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Chọn số đêm nghỉ", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(listOf("1 đêm", "2 đêm", "3 đêm", "4 đêm", "5 đêm", "6 đêm", "7 đêm")) { nightOption ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)) {
                            Text(text = nightOption)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "Trả phòng: 03/02/2022") // Ngày trả phòng có thể thay đổi
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /* Close bottom sheet */ }) {
                    Text(text = "Hoàn tất")
                }
            }
        },
        // sheetPeekHeight = 0.dp // Ẩn BottomSheet khi không hiển thị
    ) {
        // Nội dung chính của màn hình
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBottomSheet() {
    val sheetState = rememberBottomSheetScaffoldState()
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Chọn ngày nhận phòng", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                DatePicker(state = DatePickerState(CalendarLocale(LocalTextStyle.toString())))

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /* Close bottom sheet */ }) {
                    Text(text = "Hoàn tất")
                }
            }
        },
        sheetPeekHeight = 0.dp
    ) {
    }
}
enum class BottomSheetType {
    NONE, DATE_PICKER, ANOTHER_SHEET
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetManager(sheetState: BottomSheetScaffoldState, currentSheet: BottomSheetType) {
    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            when (currentSheet) {
                BottomSheetType.DATE_PICKER -> DatePickerBottomSheet()
                BottomSheetType.ANOTHER_SHEET -> RoomGuestSelectionBottomSheet()
                else -> {}
            }
        },
        sheetPeekHeight = 0.dp
    ) {
    }
}
