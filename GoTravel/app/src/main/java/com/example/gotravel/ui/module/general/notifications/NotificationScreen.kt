package com.example.gotravel.ui.module.general.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotravel.R
import com.example.gotravel.data.model.Booking
import com.example.gotravel.data.model.Notification
import com.example.gotravel.helper.CommonUtils.formatDateToVi
import com.example.gotravel.ui.components.NavTitle
import com.example.gotravel.ui.module.user.booking.BookingListViewModel

@Preview(showSystemUi = true)
@Composable
fun NotificationDetail(
    notification: Notification = Notification(),
    navController: NavController = NavController(LocalContext.current)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NavTitle("Thông báo") { navController.popBackStack() }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE3F2FD))
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_noti),
                    contentDescription = "Hotel",
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = notification.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
            HorizontalDivider(thickness = 1.dp)
            Text(
                text = notification.content,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(16.dp)
                    .height(300.dp))
            HorizontalDivider(thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp)) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = "Được gửi lúc:", fontSize = 13.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = formatDateToVi(notification.create_at), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}






@Composable
fun NotificationItem(
    notification: Notification = Notification(),
    onStatusChange: (String) -> Unit = {},
    onClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .clickable {
                    onStatusChange(notification.id_notification)
                    onClick()
                }
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var imgType = R.drawable.ic_noti
            if (notification.type == "booking"){
                imgType = R.drawable.ic_noti_booking
            }else if (notification.type == "accom"){
                imgType = R.drawable.ic_add_accom
            }
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = colorResource(id = R.color.colorSeparator80),
                        shape = CircleShape
                    )
                    .padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(imgType),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    tint = if (notification.isRead == "true") {
                        (Color.Gray)
                    } else {
                        (colorResource(id = R.color.primary))
                    }
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Column (modifier = Modifier
                .weight(1f)
                .padding(vertical = 5.dp)) {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = notification.title,
                    fontWeight = if (notification.isRead == "true") FontWeight.Normal else FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (notification.isRead == "true") Color.Gray else Color.Black
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = formatDateToVi(notification.create_at),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
        HorizontalDivider(thickness = 1.5.dp)
    }
}


@Composable
fun NotificationScreen(
    notifications: List<Notification>,
    navController: NavController,
    onDeleteAll:() -> Unit = {},
    onStatusChange: (String) -> Unit,
    onNotificationClick: (Notification) -> Unit
) {
    Column {
        NavTitle("Thông báo") { navController.popBackStack() }
        if (notifications.isEmpty()){
            Column(modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.ic_no_notification),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .size(150.dp)
                )
                Text(text = "Hiện chưa thông báo nào", fontSize = 20.sp)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val sortedNotification = notifications.sortedByDescending { it.create_at }
                    items(sortedNotification) { notification ->
                        NotificationItem(
                            notification = notification,
                            onStatusChange = onStatusChange,
                            onClick = { onNotificationClick(notification)
                            navController.navigate("notification_detail")}
                        )
                    }
                }
            }
        }
    }

}

