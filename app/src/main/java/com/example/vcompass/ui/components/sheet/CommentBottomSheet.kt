package com.example.vcompass.ui.components.sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.vcompass.R
import com.example.vcompass.data.api.model.Comment
import com.example.vcompass.data.api.model.Reply
import com.example.vcompass.helper.CommonUtils.getTimeAgo
import com.example.vcompass.ui.module.user.schedule.ScheduleViewModel

@Composable
fun CommentBottomSheet(
    viewModel: ScheduleViewModel,
){
    val comments = viewModel.schedule?.comments
    val listState = rememberLazyListState()
    var inputText by remember { mutableStateOf("") }
    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            Text(
                text = "Bình luận",
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(comments!!, key = { index, comment -> comment.id.toString() }) { index, comment ->
                Comment(comment)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .imePadding()
                .zIndex(1f)
        ) {
            Column {
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                Row(modifier = Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(40.dp).background(color = Color.White, shape = CircleShape)
                            .border(width = 1.dp, color = Color.Gray, shape = CircleShape)
                            .padding(1.5.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_vung_tau),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize().clip(CircleShape)
                        )
                    }
                    TextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier
                            .weight(1f),
                        placeholder = { Text("Nhập bình luận...") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Send
                        ),
                        keyboardActions = KeyboardActions(onSend = {
                            if (inputText.isNotBlank()) {
                                inputText = ""
                            }
                        }),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_send),
                        contentDescription = null,
                        tint = colorResource(R.color.blue) ,
                        modifier = Modifier
                            .size(22.dp)
                    )
                }
            }

        }

    }
}

@Composable
fun Comment(
    comment: Comment
){
    var isExpanded by rememberSaveable  { mutableStateOf(false) }
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = CircleShape
                    )
                    .padding(1.5.dp)
                    .align(Alignment.Top)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(comment.avatar),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column (verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxWidth()) {
                Row {
                    Text(
                        text = comment.userName.toString(),
                        fontWeight = FontWeight.W600,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = getTimeAgo(comment.createdAt),
                        fontWeight = FontWeight.W400,
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = comment.content.toString(),
                    fontWeight = FontWeight.W400,
                    color = Color.DarkGray,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Trả lời",
                    fontWeight = FontWeight.W600,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                ) {
                    Column {
                        comment.replies?.forEach { reply ->
                            CommentResponse(reply)
                        }
                    }
                }
                if (comment.replies?.isNotEmpty() == true) {
                    Row (verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray,
                            modifier = Modifier.width(30.dp))
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = if (isExpanded) "Thu gọn" else "Xem " + comment.replies?.size.toString() + " phản hồi khác",
                            fontWeight = FontWeight.W600,
                            color = Color.DarkGray,
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun CommentResponse(
    reply: Reply
){
    Row (verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
                .padding(1.dp)
                .align(Alignment.Top)
        ) {
            Image(
                painter = rememberAsyncImagePainter(reply.avatar),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Text(
                    text = reply.userName.toString(),
                    fontWeight = FontWeight.W600,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = getTimeAgo(reply.createdAt),
                    fontWeight = FontWeight.W400,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = reply.content.toString(),
                fontWeight = FontWeight.W400,
                color = Color.DarkGray,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Trả lời",
                fontWeight = FontWeight.W600,
                color = Color.Gray,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}