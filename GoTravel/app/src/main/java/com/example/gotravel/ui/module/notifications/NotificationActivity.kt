package com.example.gotravel.ui.module.notifications

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gotravel.R

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notification)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

val notificationList = listOf<Notification>(
    Notification(
        title = "Đơn hàng đã hoàn tất",
        content = "Đơn hàng 240923MYSTBT7Y đã hoàn thành. Bạn hãy đánh giá sản phẩm trước ngày 30-10-2024 để nhận 200 xu.",
        imageUrl = "https://yourimageurl.com", // Replace with your image URL
        time = "07:27 30-09-2024",
        buttonText = "Đánh Giá Sản Phẩm",
        isRead = false,
        ),
    Notification(
        title = "Đơn hàng đã hoàn tất",
        content = "Đơn hàng 240923MYSTBT7Y đã hoàn thành. Bạn hãy đánh giá sản phẩm trước ngày 30-10-2024 để nhận 200 xu.",
        imageUrl = "https://yourimageurl.com", // Replace with your image URL
        time = "07:27 30-09-2024",
        buttonText = "Đánh Giá Sản Phẩm",isRead = false,
    ),
    Notification(
        title = "Đơn hàng đã hoàn tất",
        content = "Đơn hàng 240923MYSTBT7Y đã hoàn thành. Bạn hãy đánh giá sản phẩm trước ngày 30-10-2024 để nhận 200 xu.",
        imageUrl = "https://yourimageurl.com", // Replace with your image URL
        time = "07:27 30-09-2024",
        buttonText = "Đánh Giá Sản Phẩm",isRead = true,

        ),
    Notification(
        title = "Đơn hàng đã hoàn tất",
        content = "Đơn hàng 240923MYSTBT7Y đã hoàn thành. Bạn hãy đánh giá sản phẩm trước ngày 30-10-2024 để nhận 200 xu.",
        imageUrl = "https://yourimageurl.com", // Replace with your image URL
        time = "07:27 30-09-2024",
        buttonText = "Đánh Giá Sản Phẩm",isRead = false,
    ),
    Notification(
        title = "Đơn hàng đã hoàn tất",
        content = "Đơn hàng 240923MYSTBT7Y đã hoàn thành. Bạn hãy đánh giá sản phẩm trước ngày 30-10-2024 để nhận 200 xu.",
        imageUrl = "https://yourimageurl.com", // Replace with your image URL
        time = "07:27 30-09-2024",
        buttonText = "Đánh Giá Sản Phẩm",isRead = true,

        ),
    Notification(
        title = "Đơn hàng đã hoàn tất",
        content = "Đơn hàng 240923MYSTBT7Y đã hoàn thành. Bạn hãy đánh giá sản phẩm trước ngày 30-10-2024 để nhận 200 xu.",
        imageUrl = "https://yourimageurl.com", // Replace with your image URL
        time = "07:27 30-09-2024",
        buttonText = "Đánh Giá Sản Phẩm",isRead = true,
    ),

    )


@Composable
fun Notification_Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(colorResource(id = R.color.primary)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            text = "Notification",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        )
        Text(
            text = "(10)",
            modifier = Modifier.padding(start = 10.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White),
        )
    }
}

@Composable
fun HorizontalDivider() {
    Divider(
        color = Color.Gray, // Set the color of the line
        thickness = 0.5.dp,   // Set the thickness of the line
        modifier = Modifier.padding(vertical = 10.dp) // Optional padding around the line
    )
}



@Composable
fun NotificationItem(
    title: String,
    content: String,
    imageUrl: String,
    time: String,
    buttonText: String,
    isRead: Boolean, // Add isRead parameter to control opacity
    onButtonClick: () -> Unit
) {
    // Use alpha based on whether the notification is read
    val alphaValue = if (isRead) 0.5f else 1f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .graphicsLayer(alpha = alphaValue) // Apply transparency based on isRead
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_food),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(64.dp) // Set the size for the image
                    .clip(RoundedCornerShape(8.dp)) // Set the shape of the image
                    .border(1.dp, Color.LightGray) // Optional border
            )

            Spacer(modifier = Modifier.width(8.dp)) // Space between image and text

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = content, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = time, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            }

        }
        HorizontalDivider()
    }
}


@Composable
fun NotificationForm(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Notification_Header()

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(notificationList) { notification ->
                NotificationItem(
                    title = notification.title,
                    content = notification.content,
                    imageUrl = notification.imageUrl,
                    time = notification.time,
                    buttonText = notification.buttonText,
                    onButtonClick = { /* Handle button click */ },
                    isRead = notification.isRead,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationFormPreview() {
    NotificationForm()
}

