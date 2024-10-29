package com.example.gotravel.ui.module.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gotravel.R

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}

@Composable
fun MessageBubble(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (message.isUserMessage) Arrangement.End else Arrangement.Start
    ) {
        Column(
            horizontalAlignment = if (message.isUserMessage) Alignment.End else Alignment.Start
        ) {
            // Message bubble
            Box(
                modifier = Modifier
                    .background(
                        if (message.isUserMessage) Color(0xFFDCF8C6) else Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
                    .widthIn(max = 300.dp) // Limit bubble width
            ) {
                Text(text = message.content, style = MaterialTheme.typography.bodySmall)
            }

            // Timestamp
            Text(
                text = message.timestamp,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun ChatList(messages: List<Message>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .padding(horizontal = 10.dp),
        reverseLayout = true // Ensures the most recent messages are at the bottom
    ) {
        items(messages) { message ->
            MessageBubble(message = message)
        }
    }
}

@Composable
fun ChatHeader(avatarUrl: String, userName: String, status: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.primary)),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Row(
            modifier = Modifier.padding(15.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_food),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = userName, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                Text(text = status, style = MaterialTheme.typography.bodyMedium, color = Color.White)
            }
        }

    }
}

@Composable
fun ChatInput(onSendClick: (String) -> Unit) {
    var messageText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier

            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        TextField(
            value = messageText,
            onValueChange = { messageText = it },
            placeholder = { Text("Gửi tin nhắn...") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        IconButton(onClick = {
            onSendClick(messageText)
            messageText = "" // Clear the input after sending
        }) {
            Icon(Icons.Default.Send, contentDescription = "Send Message")
        }
    }
}

@Composable
fun ChatScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header (User Info)
        ChatHeader(
            avatarUrl = "https://example.com/avatar.png", // Replace with actual avatar URL
            userName = "stylishliving",
            status = "Online 15 giây trước"
        )

        // List of Messages
        val messages = listOf(
            Message("User", "Bạn ơi, đơn hàng đã được ký nhận", "11:18", true),
            Message("User", "Khách hàng thân mến...", "07:28", false),
            // Add more messages
        )
        ChatList(messages = messages)

        // Input field at the bottom
        ChatInput(onSendClick = { message ->
            // Handle message send logic
        })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    ChatComponentScreen()
}
