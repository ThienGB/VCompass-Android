package com.example.gotravel.ui.module.room

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.gotravel.R
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Room
import com.example.gotravel.helper.CommonUtils.formatCurrency
import com.example.gotravel.ui.components.NavTitle
import com.example.gotravel.ui.module.main.user.MainUserViewModel


@Composable
fun RoomDetailScreen(
    accommodation: Accommodation = Accommodation(),
    viewModel: MainUserViewModel,
    navController: NavController = NavController(LocalContext.current),
){
    val groupedRooms = accommodation.rooms.groupBy { room -> room.roomType }
    Column {
        NavTitle(accommodation.name) { navController.navigate("accom_detail") }
        Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
            groupedRooms.forEach { (_, rooms) ->
                if (rooms.isNotEmpty())
                    RoomItem(rooms[0], rooms.size, viewModel, navController)
            }
        }

    }
}

@Composable
fun RoomItem(
    room: Room = Room(),
    freeRoom:Int = 0,
    viewModel: MainUserViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(15.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberAsyncImagePainter(room.image),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column (modifier = Modifier.fillMaxWidth()) {
                Text(text = room.name,
                    fontSize = 20.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = room.area.toString() + "m²",
                    color = Color.Gray,)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row (verticalAlignment = Alignment.CenterVertically) {
               Icon(painter = painterResource(id = R.drawable.ic_user),
                   contentDescription = null,
                   modifier = Modifier.size(25.dp),
                   tint = colorResource(id = R.color.secondBlack))
               Spacer(modifier = Modifier.width(8.dp))
               Text(text = "${room.people} người", fontSize = 17.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row  (verticalAlignment = Alignment.CenterVertically){
                Icon(painter = painterResource(id = R.drawable.ic_hotel),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = colorResource(id = R.color.secondBlack))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${room.bed} giường đôi", fontSize = 17.sp)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_check_circle),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = colorResource(id = R.color.green))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Bữa sáng miễn phí" , fontSize = 17.sp)

        }
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Icon(painter = painterResource(id = R.drawable.ic_x_circle),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Không hoàn tiền" , fontSize = 17.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_check_circle),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = colorResource(id = R.color.green))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Wifi miễn phí" , fontSize = 17.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(text = "Chỉ còn $freeRoom căn với giá này",
                color = Color.Red)
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = formatCurrency(room.price.toString()) + " đ",
                textAlign = TextAlign.Left,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                color = colorResource(id = R.color.primary),
                modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(colorResource(id = R.color.primary))
                    .clickable {
                        viewModel.setRoom(room)
                        navController.navigate("booking_detail")
                    }

            ) {
                Text(text = "Đặt phòng", color = Color.White,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                    fontSize = 20.sp)
            }
        }
    }
}
