package com.example.gotravel.ui.module.chat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gotravel.R

class ChatListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}

@Composable
fun Chat_Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.primary))
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(

            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified,
                contentDescription = "Setting"
            )
        }
        Text(
            text = "Edit Profile",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White),
            modifier = Modifier.height(20.dp),
            textAlign = TextAlign.Center

        )
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary)),
        ) {

        }
    }
}

@Composable
fun ChatItem(chat: ChatItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // Adjust padding as needed
    ) {
        // Avatar image
        Image(
            painter = painterResource(id = R.drawable.ic_food) ,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(40.dp) // Set avatar size
                .clip(CircleShape) // Clip the image to be circular
                .background(Color.LightGray) // Placeholder color for image
        )

        Spacer(modifier = Modifier.width(8.dp)) // Space between avatar and text

        Column(modifier = Modifier.weight(1f)) {
            // Sender name
            Text(
                text = chat.senderName,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1, // Only one line for the name
                overflow = TextOverflow.Ellipsis // If name is too long, it will show ellipsis
            )

            // Message preview
            Text(
                text = chat.messagePreview,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1, // Only one line for message preview
                overflow = TextOverflow.Ellipsis // Ellipsis for long messages
            )
        }

        Spacer(modifier = Modifier.width(8.dp)) // Space between text and date

        // Date
        Text(
            text = chat.date,
            style = MaterialTheme.typography.bodySmall,
          
        )
    }
}


@Composable
fun ChatList(chatList: List<ChatItem>) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Chat_Header()

        LazyColumn (
            modifier = Modifier.padding(20.dp)
        ) {
            items(chatList) { chat ->
                ChatItem(chat = chat)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewChatList() {

    val chatList = listOf(
        ChatItem(avatarUrl = "https://example.com/avatar1.png", senderName = "ahome.lami...", messagePreview = "Đánh giá để nhận Sho...", date = "Thứ 2"),
        ChatItem(avatarUrl = "https://example.com/avatar2.png", senderName = "stylishliving", messagePreview = "Khách hàng thân mến...", date = "28/09"),
        ChatItem(avatarUrl = "https://example.com/avatar3.png", senderName = "namiko.official", messagePreview = "dạ od1 là 12 nan còn...", date = "07/05"),
        ChatItem(avatarUrl = "https://example.com/avatar4.png", senderName = "tinhtebeauty", messagePreview = "Nhắc nhở đánh giá d...", date = "07/05"),
        ChatItem(avatarUrl = "https://example.com/avatar5.png", senderName = "elmich_official", messagePreview = "Elmich Việt Nam Xin...", date = "06/05")
    )

    ChatList(chatList = chatList)
}
