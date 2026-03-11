package com.example.vcompass.screen.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.EmojiEmotions
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.button.BackButton
import com.example.vcompass.ui.core.divider.ItemDivider
import com.example.vcompass.ui.core.general.BaseViewWithPullToRefresh
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.text_field.NoBorderTextField
import com.example.vcompass.ui.core.title.ActionIcon
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.back
import com.vcompass.presentation.viewmodel.accommodation.AccommodationDetailViewModel
import org.koin.androidx.compose.koinViewModel

data class ChatMessage(
    val id: String,
    val message: String? = null,
    val image: String? = null,
    val isMine: Boolean,
    val avatar: String? = null,
    val caption: String? = null
)

@Composable
fun ChatDetailScreen() {
    val viewModel: AccommodationDetailViewModel = koinViewModel()
    val navController = ScreenContext.navController
    val state by viewModel.stateUI.collectAsState()
    val mockMessages = listOf(

        ChatMessage(
            id = "1",
            message = "Chào bạn, tôi có thể giúp gì cho chuyến đi Đà Lạt sắp tới của bạn không?",
            isMine = false,
            avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlimH-52ei6mqDPohL-qaT0VuDdTK3B7dJZg&s"
        ),

        ChatMessage(
            id = "2",
            message = "Chào Nam, mình muốn hỏi về tour tham quan Thác Datanla vào sáng mai. Bên mình còn chỗ không ạ?",
            isMine = true
        ),

        ChatMessage(
            id = "3",
            message = "Dạ tour đó vẫn còn chỗ ạ. Sáng mai khởi hành lúc 8:00 tại trung tâm thành phố. Bạn đi mấy người để mình giữ chỗ nhé?",
            isMine = false,
            avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlimH-52ei6mqDPohL-qaT0VuDdTK3B7dJZg&s"
        ),

        ChatMessage(
            id = "4",
            isMine = false,
            avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlimH-52ei6mqDPohL-qaT0VuDdTK3B7dJZg&s",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlimH-52ei6mqDPohL-qaT0VuDdTK3B7dJZg&s",
            caption = "Hình ảnh thực tế từ tour tuần trước."
        )
    )
    BaseViewWithPullToRefresh(
        viewModel = viewModel,
        state = state,
        navController = navController,
        topBar = {
            Column {
                ChatHeader()
                ItemDivider()
            }

        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ChatMessageList(
                modifier = Modifier.weight(1f),
                mockMessages
            )
            ItemDivider()
            ChatInputBar()
        }
    }
}

@Composable
fun ChatHeader() {
    val navController = ScreenContext.navController
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton { navController.back() }
        CoreImage(
            source = CoreImageSource.Url("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlimH-52ei6mqDPohL-qaT0VuDdTK3B7dJZg&s"),
            modifier = Modifier
                .size(MyDimen.p40)
                .clip(CircleShape)
        )

        SpaceWidth8()

        Column(
            modifier = Modifier.weight(1f)
        ) {
            CoreText(
                text = "Tư vấn viên Nam",
                style = CoreTypographySemiBold.labelLarge
            )
            CoreText(
                text = "Đang hoạt động",
                style = CoreTypography.labelSmall,
                color = MyColor.TextColorLight
            )
        }
        ActionIcon(imageVector = Icons.Rounded.Info, tintColor = MyColor.Primary) { }
    }
}

@Composable
fun MessageBubble(
    message: ChatMessage
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MyDimen.p16,
                vertical = MyDimen.p6
            ),
        horizontalArrangement =
            if (message.isMine) Arrangement.End else Arrangement.Start
    ) {

        if (!message.isMine) {

            CoreImage(
                source = CoreImageSource.Url(message.avatar.toString()),
                modifier = Modifier
                    .size(MyDimen.p28)
                    .clip(CircleShape)
            )

            SpaceWidth8()
        }

        Column(
            horizontalAlignment =
                if (message.isMine) Alignment.End else Alignment.Start
        ) {

            if (message.message != null) {

                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = MyDimen.p16,
                                topEnd = MyDimen.p16,
                                bottomStart = if (message.isMine) MyDimen.p16 else MyDimen.p4,
                                bottomEnd = if (message.isMine) MyDimen.p4 else MyDimen.p16
                            )
                        )
                        .background(
                            if (message.isMine) MyColor.Primary
                            else MyColor.GrayEEE
                        )
                        .padding(MyDimen.p12)
                ) {

                    CoreText(
                        text = message.message,
                        style = CoreTypography.labelMedium,
                        color = if (message.isMine) MyColor.White else MyColor.TextColorPrimary
                    )
                }
            }

            if (message.image != null) {

                Spacer(Modifier.height(MyDimen.p8))

                Column {

                    CoreImage(
                        source = CoreImageSource.Url(message.image.toString()),
                        contentDescription = null,
                        modifier = Modifier
                            .width(MyDimen.p220)
                            .clip(RoundedCornerShape(MyDimen.p16))
                    )

                    message.caption?.let {

                        Spacer(Modifier.height(MyDimen.p4))

                        CoreText(
                            text = it,
                            style = CoreTypography.labelSmall,
                            color = MyColor.TextColorLight
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatMessageList(
    modifier: Modifier = Modifier,
    messages: List<ChatMessage>
) {
    LazyColumn(
        modifier = modifier
    ) {

        item {

            CoreText(
                text = "HÔM NAY",
                style = CoreTypography.labelSmall,
                color = MyColor.TextColorLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MyDimen.p12),
                textAlign = TextAlign.Center
            )
        }

        items(messages) {

            MessageBubble(it)
        }
    }
}

@Composable
fun ChatInputBar() {
    var message by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MyDimen.p12),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoreIcon(imageVector = Icons.Rounded.Image, tintColor = MyColor.Primary)

        SpaceWidth8()
        NoBorderTextField(
            placeholder = "Nhắn tin...",
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .weight(1f)
                .background(MyColor.GrayF5, RoundedCornerShape(MyDimen.p28))
                .height(MyDimen.p48),
            trailingIcon = {
                CoreIcon(
                    imageVector = Icons.Rounded.EmojiEmotions, tintColor = MyColor.Primary,
                    iconModifier = Modifier
                        .clip(CircleShape)
                        .clickable {})
            }
        )

        SpaceWidth8()

        CoreIcon(
            imageVector = Icons.AutoMirrored.Rounded.Send, tintColor = MyColor.Primary,
            iconModifier = Modifier
                .clip(CircleShape)
                .clickable {}
        )
    }
}