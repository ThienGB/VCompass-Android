package com.example.gotravel.ui.module.partner.DashBoard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gotravel.R
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Booking
import com.example.gotravel.data.model.Conversation
import com.example.gotravel.data.model.UserAccount
import com.example.gotravel.helper.CommonUtils.formatCurrency
import com.example.gotravel.helper.CommonUtils.formatDate
import com.example.gotravel.helper.CommonUtils.formatDateToVi
import com.example.gotravel.ui.components.NavTitle
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.model.BarData
import java.text.SimpleDateFormat
import java.util.Locale


data class RevenueStats(
    val title: String,
    val revenue: Int
)
fun calculateMonthlyRevenue(bookings: List<Booking>): List<RevenueStats> {
    val formatter = SimpleDateFormat("MM/yyyy", Locale.getDefault())

    return bookings.groupBy { booking ->
        val date = java.util.Date(booking.startDate)
        formatter.format(date)
    }.map { (monthYear, bookingsInMonth) ->
        val totalRevenue = bookingsInMonth.sumOf { it.price }
        RevenueStats(
            title = "Tháng ${monthYear.split("/")[0]}, Năm ${monthYear.split("/")[1]}",
            revenue = totalRevenue
        )
    }.sortedBy { stat ->
        val parts = stat.title.split(", ")
        val month = parts[0].replace("Tháng ", "").toInt()
        val year = parts[1].replace("Năm ", "").toInt()
        year * 100 + month
    }
}
fun calculateYearlyRevenue(bookings: List<Booking>): List<RevenueStats> {
    val formatter = SimpleDateFormat("yyyy", Locale.getDefault())
    return bookings.groupBy { booking ->
        val date = java.util.Date(booking.startDate)
        formatter.format(date) // Nhóm theo năm
    }.map { (year, bookingsInYear) ->
        val totalRevenue = bookingsInYear.sumOf { it.price }
        RevenueStats(
            title = "Năm $year",
            revenue = totalRevenue
        )
    }.sortedBy { it.title.replace("Năm ", "").toInt() } // Sắp xếp theo năm
}

@Preview(showSystemUi = true)
@Composable
fun DashboardDetail(
    bookings: List<Booking> = listOf(),
    navController: NavController = NavController(LocalContext.current)
) {
    val sortOption = remember { mutableStateOf("Doanh thu theo tháng") }
    fun sortData(option: String): List<RevenueStats> {
        return when (option) {
            "Doanh thu theo tháng" -> calculateMonthlyRevenue(bookings)
            "Doanh thu theo năm" -> calculateYearlyRevenue(bookings)
            else -> calculateMonthlyRevenue(bookings)
        }
    }
    val sortedList = sortData(sortOption.value)
    Column (modifier = Modifier.background(Color.White)) {
        NavTitle("Doanh thu") { navController.popBackStack() }
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
                        "Doanh thu theo tháng" -> "Doanh thu theo năm"
                        else -> "Doanh thu theo tháng"
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
        if (bookings.isEmpty()){
            Column(modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxWidth()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.ic_no_search),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .size(150.dp)
                )
                Text(text = "Chưa có doanh thu",
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                    fontSize = 20.sp)
            }
        }else {
            Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
                sortedList.forEach { item ->
                    Column (modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)){
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model =  R.drawable.ic_revenue,
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(Color.LightGray),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = formatCurrency(item.revenue.toString()),
                                    color = colorResource(id = R.color.primary),
                                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 8.dp),
                            thickness = 0.5.dp,
                            color = Color.LightGray.copy(alpha = 0.5f)
                        )
                    }
                }
                RevenueBarChart(sortedList)
            }
        }
    }
}
@Composable
fun RevenueBarChart(revenueStats: List<RevenueStats>) {
    val barData = revenueStats.map { stats ->
        BarData(stats.title, (stats.revenue / 100000).toFloat())
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Biểu đồ Doanh thu",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        BarChart(
            barData = barData,
            onBarClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(200.dp),
            color = Color.Gray,

        )
    }
}