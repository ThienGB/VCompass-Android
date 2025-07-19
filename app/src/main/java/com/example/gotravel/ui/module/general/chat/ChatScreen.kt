package com.example.gotravel.ui.module.general.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.gotravel.R
import com.example.gotravel.data.model.Conversation
import com.example.gotravel.data.model.Message
import com.example.gotravel.data.model.UserAccount
import com.example.gotravel.helper.CommonUtils.formatDateToVi
import com.example.gotravel.ui.components.NavTitle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun ChatItem(
    conversation: Conversation,
    user: UserAccount,
    onItemClick: (Conversation) -> Unit,
) {
    val sortedMessage = conversation.messages.sortedByDescending { it.createdAt }
    val fullName = if (conversation.idFirstUser == user.userId) {
        conversation.userSecond?.fullName
    } else {
        conversation.userFirst?.fullName
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(conversation) }
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model =  (if (conversation.idFirstUser == user.userId) {
                    conversation.userSecond?.image
                } else {
                    conversation.userFirst?.image
                }),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                (if (conversation.idFirstUser == user.userId) {
                    conversation.userSecond?.fullName
                } else {
                    conversation.userFirst?.fullName
                })?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = sortedMessage.firstOrNull()?.content ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = formatDateToVi(sortedMessage.firstOrNull()?.createdAt),
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                textAlign = TextAlign.End,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp),
            thickness = 0.5.dp,
            color = Color.LightGray.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun ConversationScreen(
    conversations: List<Conversation>,
    navController: NavHostController,
    user: UserAccount,
    onItemClick: (Conversation) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NavTitle("Tin nhắn") { navController.popBackStack() }
        if (conversations.isEmpty()) {
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
                Text(text = "Hiện chưa có tin nhắn nào, hãy đặt phòng để chat!", fontSize = 20.sp)
            }
        } else {
            LazyColumn (
                modifier = Modifier.padding(10.dp)
            ) {
                val sortedConversation =conversations.sortedByDescending { conversation ->
                    conversation.messages.maxByOrNull { it.createdAt!! }?.createdAt
                }
                items(sortedConversation) { item ->
                    ChatItem(item, user
                    ) {
                        onItemClick(item)
                        navController.navigate("chat_room")
                    }
                }
            }
        }

    }

}

@Composable
fun ChatScreen(
    conversation: Conversation,
    user: UserAccount,
    navController: NavHostController,
    sendMessage: (Message) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val fullName = if (conversation.idFirstUser == user.userId) {
            conversation.userSecond?.fullName
        } else {
            conversation.userFirst?.fullName
        }
        NavTitle(fullName.toString()){ navController.popBackStack()}
        ChatList(conversation.messages ,user, Modifier.weight(1f))
        ChatInput(onSendClick = { message ->
            sendMessage(Message().apply{
                id_message = UUID.randomUUID().toString()
                id_user = user.userId
                content = message
                createdAt = Date()
            })
        })
    }
}