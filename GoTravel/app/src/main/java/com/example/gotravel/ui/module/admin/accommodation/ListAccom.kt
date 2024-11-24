package com.example.gotravel.ui.module.admin.accommodation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.gotravel.R
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.helper.CommonUtils.formatCurrency
import com.example.gotravel.ui.components.CustomSearchBar
import com.example.gotravel.ui.components.NavTitle
import com.example.gotravel.ui.module.admin.main.MainAdminViewModel
import kotlin.math.roundToInt

@Composable
fun AccomList(
    accommodations: List<Accommodation> = listOf(),
    navController: NavController,
    viewModel: MainAdminViewModel
) {
    viewModel.setIsShowBottomBar(false)
    var filterAccom by remember { mutableStateOf(accommodations) }
    fun onTextChange(query: String){
        filterAccom = accommodations.filter { accom ->
            accom.name.contains(query, ignoreCase = true) ||
                    accom.address.contains(query, ignoreCase = true)
        }
    }
    Column {
        NavTitle("Danh sách chỗ ở"){ navController.popBackStack() }
        CustomSearchBar(onTextChange = { onTextChange(it) })
        Spacer(modifier = Modifier.height(15.dp))
        if (accommodations.isEmpty()){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.ic_no_booking),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .size(150.dp)
                )
                Text(text = "Hiện chưa có bất kỳ chỗ ở nào!",
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                    fontSize = 20.sp)
            }
        }else if (filterAccom.isEmpty()) {
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
                Text(text = "Không có kết quả nào phù hợp!",
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                    fontSize = 20.sp)
            }
        } else {
            LazyColumn {
                items(filterAccom) { accommodation ->
                    AccomItem(accommodation, navController, viewModel)
                }
            }
        }

    }
}
@Composable
fun AccomItem(
    accommodation: Accommodation = Accommodation(),
    navController: NavController = NavController(LocalContext.current),
    viewModel: MainAdminViewModel
) {
    val lowestPrice = accommodation.rooms.minOfOrNull { it.price } ?: 0
    val averageRating = if (accommodation.ratings.isNotEmpty()) {
        accommodation.ratings.map { it.rate }.average().roundToInt()
    } else {
        5
    }
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                viewModel.setAccommodation(accommodation)
                navController.navigate("accom_infor_admin")
            }
            .padding(horizontal = 8.dp, vertical = 4.dp)
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
                    repeat(averageRating) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color.Yellow,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    repeat(5 - averageRating) {
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
