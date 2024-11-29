package com.example.gotravel.ui.module.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotravel.data.model.Message
import com.example.gotravel.data.model.UserAccount

@Composable
fun MessageBubble(message: Message, user1: UserAccount) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if (message.id_user == user1.userId) Arrangement.End else Arrangement.Start
    ) {
        Column(
            horizontalAlignment = if (message.id_user == user1.userId) Alignment.End else Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .background(
                        if (message.id_user == user1.userId) Color(0xFFDCF8C6) else Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
                    .widthIn(max = 300.dp)
            ) {
                Text(text = message.content.toString(), style = MaterialTheme.typography.bodySmall)
            }

            Text(
                text = formatDate(message.createdAt.toString()),
                color = Color.Gray,
                fontSize = 10.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun ChatList(
    messages: List<Message>,
    user1: UserAccount,
    modifier: Modifier = Modifier
) {
    val sortedMessages = messages.sortedByDescending { it.createdAt }
    LazyColumn(
        modifier = modifier.padding(horizontal = 10.dp),
        reverseLayout = true
    ) {
        items(sortedMessages) { message ->
            MessageBubble(message = message, user1 = user1)
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
            messageText = ""
        }) {
            Icon(Icons.Default.Send, contentDescription = "Send Message")
        }
    }
}


