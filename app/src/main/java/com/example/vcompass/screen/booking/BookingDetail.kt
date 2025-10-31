//package com.example.vcompass.ui.module.user.booking
//
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.vcompass.R
//import com.example.vcompass.data.model.Search
//import com.example.vcompass.data.model.UserAccount
//import com.example.vcompass.helper.CommonUtils.formatCurrency
//import com.example.vcompass.ui.core.title.NavTitle
//import com.example.vcompass.ui.module.user.main.MainUserViewModel
//
//@Composable
//fun ContactInfoForm(
//    accommodation: Accommodation = Accommodation(),
//    room: Room = Room(),
//    user: UserAccount,
//    viewModel: MainUserViewModel,
//    search: Search,
//    intentToBooking: () -> Unit
//) {
//    val context = LocalContext.current
//    var fullName by remember { mutableStateOf(user.fullName) }
//    var phoneNumber by remember { mutableStateOf(user.phone) }
//    var email by remember { mutableStateOf(user.email) }
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.White)
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        Column (verticalArrangement = Arrangement.spacedBy(16.dp)) {
//            Text(
//                text = "Thông tin liên hệ",
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//            )
//            ContactTextField(
//                label = "Họ và tên",
//                placeholder = "Ví dụ: HOANG CONG THIEN",
//                value = fullName,
//                onValueChange = { fullName = it }
//            )
//            ContactTextField(
//                label = "Số điện thoại",
//                placeholder = "Ví dụ: 0900123456",
//                value = phoneNumber,
//                onValueChange = { phoneNumber = it }
//            )
//            ContactTextField(
//                label = "Email",
//                placeholder = "Ví dụ: congthien@gmail.com",
//                value = email,
//                onValueChange = { email = it }
//            )
//        }
//        Spacer(modifier = Modifier.weight(1f))
//        HorizontalDivider(thickness = 1.dp)
//        Column {
//            Row {
//                Text(text = "Tổng giá: ",
//                    fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
//                    fontSize = 19.sp, modifier = Modifier.padding(bottom = 10.dp))
//                Spacer(modifier = Modifier.weight(1f))
//                Column(modifier = Modifier.padding(end = 10.dp)) {
//                    Text(text = formatCurrency(room.price.toString()) + " đ",
//                        textAlign = TextAlign.Right,
//                        modifier = Modifier.fillMaxWidth(),
//                        fontSize = 19.sp,
//                        fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
//                        color = colorResource(id = R.color.primary)
//                    )
//                    Spacer(modifier = Modifier.padding(bottom = 10.dp))
//                    Text(text = "Đã bao gồm thuế",
//                        fontSize = 17.sp,
//                        textAlign = TextAlign.Right,
//                        modifier = Modifier.fillMaxWidth()
//                            .padding(bottom = 10.dp),
//                        fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
//                        color = colorResource(id = R.color.secondBlack)
//                    )
//                }
//            }
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding()
//                    .height(50.dp)
//                    .clip(RoundedCornerShape(5.dp))
//                    .background(colorResource(id = R.color.primary))
//                    .clickable {
//                        viewModel.handleConfirmBooking(accommodation, room, user, search, phoneNumber, fullName, email)
//                        Toast.makeText(context, "Đặt phòng thành công", Toast.LENGTH_SHORT).show()
//                        intentToBooking()
//                    }
//            ) {
//                Text(text = "Xác nhận", color = Color.White,
//                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
//                    fontSize = 20.sp)
//            }
//        }
//
//    }
//}
//
//@Composable
//fun ContactTextField(label: String, placeholder: String, value: String, onValueChange: (String) -> Unit) {
//    Column(
//        verticalArrangement = Arrangement.spacedBy(8.dp),
//    ) {
//        Text(
//            text = label,
//            fontSize = 18.sp,
//            fontWeight = FontWeight(500),
//        )
//        OutlinedTextField(
//            value = value,
//            onValueChange = onValueChange,
//            placeholder = {
//                Text(text = placeholder)
//            },
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}
//
//@Composable
//fun BookingDetailScreen(
//    accommodation: Accommodation = Accommodation(),
//    room: Room = Room(),
//    user: UserAccount = UserAccount(),
//    navController: NavController,
//    viewModel: MainUserViewModel,
//    search: Search,
//    intentToBooking: () -> Unit
//) {
//    Column {
//        NavTitle("Thông tin khách"){navController.navigate("room_detail")}
//        ContactInfoForm(accommodation, room, user, viewModel, search, intentToBooking)
//    }
//}
