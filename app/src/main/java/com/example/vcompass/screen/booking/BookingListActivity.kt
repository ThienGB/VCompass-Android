//package com.example.vcompass.ui.module.user.booking
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.NavController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.example.vcompass.MainApplication
//import com.example.vcompass.R
//import com.example.vcompass.helper.CommonUtils.formatCurrency
//import com.example.vcompass.helper.CommonUtils.getUserFromShareRef
//import com.example.vcompass.helper.RealmHelper
//import com.example.vcompass.helper.SharedPreferencesHelper
//import com.example.vcompass.ui.core.general.Loading
//import com.example.vcompass.ui.core.title.NavTitle
//import com.example.vcompass.ui.factory.ViewModelFactory
//import com.example.vcompass.ui.module.general.accomodation.HotelDetailsScreen
//import com.example.vcompass.ui.module.user.main.MainUserActivity
//import com.example.vcompass.ui.module.user.review.ReviewScreen
//
//class BookingListActivity: ComponentActivity() {
//    private lateinit var viewModel: BookingListViewModel
//    private lateinit var realmHelper: RealmHelper
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        realmHelper = (application as MainApplication).realmHelper
//        val factory = ViewModelFactory(BookingListViewModel::class.java, realmHelper)
//        viewModel = ViewModelProvider(this, factory)[BookingListViewModel::class.java]
//        val sharedPreferences = getSharedPreferences(SharedPreferencesHelper.SHARED_PREFS, Context.MODE_PRIVATE)
//        val user = getUserFromShareRef(sharedPreferences)
//        viewModel.setUserId(user.userId)
//        viewModel.fetchData()
//        setContent {
//            val navController = rememberNavController()
//            NavHost(navController = navController, startDestination = "booking_list") {
//                composable("booking_list") {
//                    val bookings by viewModel.bookings.collectAsState()
//                    val isLoading by viewModel.isLoading.collectAsState()
//                    BookingStatusList(
//                        bookings = bookings,
//                        isLoading = isLoading,
//                        viewModel = viewModel,
//                        intentToHome = { intentToHome() },
//                        navController = navController
//                    )
//                }
//                composable("booking_infor",) {
//                    val booking by viewModel.booking.collectAsState()
//                    BookingInforScreen(booking, viewModel, navController)
//                }
//                composable("rating",) {
//                    val accommodation by viewModel.accommodation.collectAsState()
//                    ReviewScreen(accommodation, viewModel, user, navController)
//                }
//                composable("accom_detail") {
//                    val accommodation by viewModel.accommodation.collectAsState()
//                    HotelDetailsScreen(accommodation, navController, calledBy = "admin")
//                }
//            }
//        }
//    }
//    private fun intentToHome(){
//        intent = Intent(this, MainUserActivity::class.java)
//        startActivity(intent)
//    }
//}
//
//@Composable
//fun BookingStatusItem(
//    status: String,
//    hotelName: String,
//    bookingCode: String,
//    price: String,
//    booking: Booking,
//    navController: NavController,
//    viewModel: BookingListViewModel
//) {
//    val backgroundColor = when (status) {
//        "pending" -> Color(0xFFFAEDC4)
//        "success" -> Color(0xFFCDFCCE)
//        "failed" -> Color(0xFFFAD4D1)
//        else -> {Color(0xFFCACACA)
//        }
//    }
//    val statusString = when (status) {
//        "pending" -> "Đang xử lý"
//        "success" -> "Đặt Thành công"
//        "failed" -> "Thất bại"
//        else -> {"Hủy"}
//    }
//    val textColor = when (status) {
//        "pending" -> Color(0xFFCF9B00)
//        "success" -> Color(0xFF008005)
//        "failed" -> Color(0xFFB90C00)
//        else -> {Color(0xFF050505)
//        }
//    }
//    Card(
//        elevation = CardDefaults.cardElevation(4.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .clickable {
//                viewModel.setBooking(booking)
//                navController.navigate("booking_infor")
//            }
//    ) {
//        Column(modifier = Modifier
//            .background(Color.White)
//            .padding(16.dp)
//            ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.padding(bottom = 8.dp)
//            ) {
//                Box(
//                    modifier = Modifier
//                        .background(color = backgroundColor, shape = RoundedCornerShape(30.dp))
//                        .padding(horizontal = 20.dp, vertical = 6.dp)
//                ) {
//                    Text(
//                        text = statusString,
//                        color = textColor,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight(450)
//                    )
//                }
//            }
//            Row (verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.padding(vertical = 10.dp)) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_accommodation),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(end = 8.dp)
//                        .size(24.dp)
//                )
//                Text(
//                    text = hotelName,
//                    fontWeight = FontWeight(600),
//                    fontSize = 18.sp
//                )
//            }
//            HorizontalDivider(thickness = 1.dp)
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 10.dp)
//            ) {
//                Text(
//                    text = "Mã: $bookingCode",
//                )
//
//                Text(
//                    text = formatCurrency(price) + " đ",
//                    color = colorResource(id = R.color.primary),
//                    fontWeight = FontWeight(600),
//                    fontSize = 16.sp
//                )
//            }
//        }
//    }
//}
//@Composable
//fun BookingStatusList(
//    bookings: List<Booking> = emptyList(),
//    isLoading: Boolean = true,
//    viewModel: BookingListViewModel,
//    intentToHome: () -> Unit,
//    navController: NavController
//) {
//    Column {
//        NavTitle("Danh sách đặt phòng"){ intentToHome() }
//        Spacer(modifier = Modifier.height(15.dp))
//        if (isLoading){
//            Loading()
//        }else {
//            if (bookings.isEmpty()){
//                Column(modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.White)
//                    .padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painter = painterResource(id = R.drawable.ic_no_booking),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .padding(bottom = 20.dp)
//                            .size(150.dp)
//                    )
//                    Text(text = "Bạn chưa có bất kỳ đặt chỗ nào",
//                        fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
//                        fontSize = 20.sp)
//                }
//            } else {
//                LazyColumn (modifier =Modifier.background(Color.White)) {
//                    items(bookings) { booking ->
//                        BookingStatusItem(booking.status, booking.accommodationName, booking.bookingId,
//                            booking.price.toString(),booking,navController, viewModel)
//                    }
//                }
//            }
//
//        }
//
//    }
//}
