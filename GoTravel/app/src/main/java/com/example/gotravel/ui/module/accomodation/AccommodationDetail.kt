package com.example.gotravel.ui.module.accomodation

import android.credentials.CredentialDescription
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.gotravel.MainApplication
import com.example.gotravel.R
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.helper.CommonUtils.formatCurrency
import com.example.gotravel.helper.RealmHelper
import com.example.gotravel.ui.factory.ViewModelFactory

class AccomodationDetail: ComponentActivity() {
    private lateinit var viewModel: AccommodationDetailViewModel
    private lateinit var realmHelper: RealmHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accommodationId = intent.getStringExtra("accommodationId")
        realmHelper = (application as MainApplication).realmHelper
        val factory = ViewModelFactory(AccommodationDetailViewModel::class.java, realmHelper)
        viewModel = ViewModelProvider(this, factory)[AccommodationDetailViewModel::class.java]
        if (accommodationId != null) {
            viewModel.currentId = accommodationId
        }
        setContent {
            val accommodation by viewModel.accommodations.collectAsState()
            HotelDetailsScreen(accommodation)
        }
    }
}

@Composable
fun HotelDetailsScreen(
    accommodations: Accommodation,
    onClick: () -> Unit = {},
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Box(){
            ImageSection(accommodations.image)
            Column (modifier = Modifier
                .padding(top = 180.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
            ) {
                HotelInfoSection(
                    title = accommodations.name,
                    numStart = 5,
                    location = accommodations.address
                )
                HorizontalDivider(
                    thickness = 7.dp,
                    color = colorResource(R.color.lightGray)
                )
                RatingsSection()
                HorizontalDivider(
                    thickness = 7.dp,
                    color = colorResource(R.color.lightGray)
                )
                AmenitiesSection()
                HorizontalDivider(
                    thickness = 7.dp,
                    color = colorResource(R.color.lightGray)
                )
                CheckInOutSection()
                HorizontalDivider(
                    thickness = 7.dp,
                    color = colorResource(R.color.lightGray)
                )
                HotelDescriptionSection(accommodations.description)
                HorizontalDivider(
                    thickness = 7.dp,
                    color = colorResource(R.color.lightGray)
                )
                PriceSection(accommodations.price.toString())
                BookButton(onClick)
            }
        }
    }
}

@Composable
fun ImageSection(imageUrl: String) {
    Box {
        Image(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            painter = rememberAsyncImagePainter(ImageRequest.Builder
                (LocalContext.current).data(data = imageUrl).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                placeholder(R.drawable.accommodation_sample)

            }).build()
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
fun HotelInfoSection(
    title: String = "Khách sạn Pullman Vũng Tàu",
    numStart: Int = 5,
    location: String = "15 Thi Sách, Phường Thắng Tam, Vũng Tàu, Bà Rịa - Vũng Tàu, Việt Nam"
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = title,
            fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
            fontSize = 22.sp)
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
        ) {
            for (i in 1..numStart){
                Icon(imageVector = Icons.Default.Star,
                    contentDescription = null, tint = Color.Yellow)
            }
        }
        Row {
            Icon(imageVector = Icons.Default.LocationOn,
                tint = colorResource(id = R.color.secondBlack),
                contentDescription = null,
                modifier = Modifier.padding(end = 7.dp))
            Text(text = location, fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
        }
    }
}

@Composable
fun RatingsSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Xếp hạng và đánh giá",
                fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                fontSize = 22.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Xem tất cả",
                fontSize = 16.sp,
                color = colorResource(id = R.color.primary),
                fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = colorResource(id = R.color.primary),
                contentDescription = null)
        }
        Row(modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "8.6", fontSize = 50.sp,
                fontFamily = FontFamily(Font(R.font.proxima_nova_semi_bold)),
                color = colorResource(id = R.color.primary))
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(text = "Ấn tượng", fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                    color = colorResource(id = R.color.primary))
                Text(text = "288 lượt đánh giá", fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
            }
        }

        RatingTags()
        Spacer(modifier = Modifier.padding(top = 15.dp))
        Text(text = "Đánh giá hàng đầu",
            fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
            fontSize = 20.sp)

        TopReviewSection()
    }
}

@Composable
fun RatingTags() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.horizontalScroll(rememberScrollState())) {
        RatingTag("Phòng sạch")
        RatingTag("Nhân viên thân thiện")
        RatingTag("Dịch vụ tốt")
        RatingTag("Nhân viên thân thiện")
        RatingTag("Dịch vụ tốt")
    }
}

@Composable
fun RatingTag(text: String) {
    Surface(shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, Color.Gray)) {
        Text(text = text, modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun TopReviewSection() {
    val reviews = listOf(Review("Nguyen V.A.", "Khách sạn mới và đẹp, gần biển lại thuận tiện với nhân viên nhiệt tình và thân thiện."),
        Review("Tran V.B.", "Khách sạn sạch sẽ, vị trí đắc địa, nhân viên nhiệt tình. Xứng đáng với giá tiền."))
    ReviewItem(reviews)
}
data class Review(val name: String, val review: String)

@Composable
fun ReviewItem(
    listItem :List<Review>
) {
    LazyRow(modifier = Modifier
        .fillMaxWidth()) {
        items(listItem){ item ->
            Box(
                modifier = Modifier
                    .padding(end = 10.dp, top = 10.dp)
                    .width(250.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(id = R.color.bgRate))) {
                Column(Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)){
                    Text(text = item.name,fontFamily = FontFamily(Font(R.font.proxima_nova_bold)))
                    Text(text = item.review, fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
                }
            }
        }
    }
}

@Composable
fun AmenitiesSection(
    listItem: List<String> = listOf("Nhà hàng", "Lễ tân 24h", "Hồ bơi", "Wifi", "Massage", "Thú cưng")
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 10.dp)) {
            Text(text = "Tiện nghi",
                fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                fontSize = 22.sp)
            Spacer(modifier = Modifier.padding(bottom = 5.dp))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Xem tất cả",
                fontSize = 16.sp,
                color = colorResource(id = R.color.primary),
                fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = colorResource(id = R.color.primary),
                contentDescription = null)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState())) {
            listItem.forEach(){item ->
                AmenityItem(item)
            }
        }
    }
}

@Composable
fun AmenityItem(name: String) {
    Surface(shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, Color.Gray)) {
        Text(text = name, modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun CheckInOutSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row {
            Text(text = "Giờ nhận phòng/Trả phòng",
                fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                fontSize = 22.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
            Column {
                Text(text = "Nhận phòng", style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_bold)))
                Text(text = "15:00 - 03:00", style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(text = "Trả phòng", style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_bold)))
                Text(text = "trước 11:00", style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
            }
        }
    }
}

@Composable
fun HotelDescriptionSection(description: String?) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Mô tả nơi ở ",
            fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
            fontSize = 22.sp, modifier = Modifier.padding(bottom = 10.dp))
        Text(
            text = description.toString(),
            fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun PriceSection(price: String) {
    Row(modifier = Modifier.padding(16.dp)) {
        Text(text = "Giá phòng mỗi đêm từ ",
            fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
            fontSize = 18.sp, modifier = Modifier.padding(bottom = 10.dp))
        Spacer(modifier = Modifier.weight(1f))
        Column(modifier = Modifier.padding(end = 10.dp)) {
            Text(text = formatCurrency(price) + " đ",
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                color = colorResource(id = R.color.primary))
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
            Text(text = "Đã bao gồm thuế",
                fontSize = 17.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth(),
                fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                color = colorResource(id = R.color.secondBlack))
        }
    }
}

@Composable
fun BookButton(
    onClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(colorResource(id = R.color.primary))
            .clickable { onClick }
    ) {
        Text(text = "Chọn phòng", color = Color.White,
            fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
            fontSize = 20.sp)
    }
}
