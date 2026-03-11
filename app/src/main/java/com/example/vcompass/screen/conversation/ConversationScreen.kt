package com.example.vcompass.screen.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddComment
import androidx.compose.material.icons.rounded.SmartToy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.divider.ItemDivider
import com.example.vcompass.ui.core.general.BaseViewWithPullToRefresh
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.title.ActionIcon
import com.example.vcompass.ui.core.title.TitleSearchBarAction
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.add
import com.example.vcompass.util.back
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.accommodation.AccommodationDetailViewModel
import org.koin.androidx.compose.koinViewModel

data class Conversation(
    val id: String,
    val name: String,
    val message: String,
    val time: String,
    val avatar: String?,
    val isOnline: Boolean = false,
    val unreadCount: Int = 0,
    val isBot: Boolean = false
)

@Composable
fun ConversationScreen() {
    val viewModel: AccommodationDetailViewModel = koinViewModel()
    val navController = ScreenContext.navController
    val state by viewModel.stateUI.collectAsState()
    val conversations = listOf(
        Conversation(
            "1",
            "Nguyễn An (Tư vấn viên)",
            "Lịch trình của bạn đã sẵn sàng!",
            "Vừa xong",
            "",
            true,
            2
        ),
        Conversation(
            "2",
            "Hội Phượt Đà Lạt 🌲",
            "Trần Bình: Hẹn gặp bạn ở Đà Lạt nhé.",
            "10:30",
            ""
        ),
        Conversation(
            "3",
            "Lê Minh Khoa",
            "Bạn có muốn đi chung taxi không?",
            "08:15",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlimH-52ei6mqDPohL-qaT0VuDdTK3B7dJZg&s"
        ),
        Conversation(
            "4",
            "Hỗ trợ tự động",
            "Yêu cầu hoàn tiền của bạn đang được xử lý.",
            "Hôm qua",
            null,
            isBot = true
        ),
        Conversation(
            "5",
            "Hoàng Yến",
            "Cảm ơn bạn nhiều nhé!",
            "Thứ 3",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlimH-52ei6mqDPohL-qaT0VuDdTK3B7dJZg&s"
        )
    )
    BaseViewWithPullToRefresh(
        viewModel = viewModel,
        state = state,
        navController = navController,
        topBar = {
            TitleSearchBarAction(
                placeholder = "Tìm kiếm cuộc trò chuyện...",
                rightItem = {
                    ActionIcon(imageVector = Icons.Rounded.AddComment) { }
                },
                onBack = { navController.back() }
            )
        }
    ) {
        LazyColumn {
            items(conversations) {
                ConversationItem(
                    item = it,
                    onClick = {
                        navController.add(CoreRoute.ChatDetail.route)
                    }
                )
                ItemDivider()
            }
        }
    }
}

@Composable
fun ConversationItem(
    item: Conversation,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(
                horizontal = MyDimen.p16,
                vertical = MyDimen.p12
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box {
            if (item.isBot) {
                Box(
                    modifier = Modifier
                        .size(MyDimen.p48)
                        .clip(CircleShape)
                        .background(MyColor.Primary),
                    contentAlignment = Alignment.Center
                ) {
                    CoreIcon(
                        imageVector = Icons.Rounded.SmartToy,
                        tintColor = MyColor.White
                    )
                }

            } else {
                CoreImage(
                    source = CoreImageSource.Url(item.avatar.orEmpty()),
                    contentDescription = null,
                    modifier = Modifier
                        .size(MyDimen.p48)
                        .clip(CircleShape),
                )
            }

            if (item.isOnline) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(MyDimen.p10)
                        .background(MyColor.Green, CircleShape)
                )
            }
        }

        SpaceWidth8()

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                CoreText(
                    text = item.name,
                    style = CoreTypographySemiBold.labelLarge,
                    modifier = Modifier.weight(1f)
                )
                CoreText(
                    text = item.time,
                    style = CoreTypography.labelSmall,
                    color = MyColor.TextColorLight
                )
            }

            Spacer(Modifier.height(MyDimen.p4))
            Row(verticalAlignment = Alignment.CenterVertically) {
                CoreText(
                    text = item.message,
                    style = CoreTypography.labelSmall,
                    color = if (item.unreadCount > 0) MyColor.Primary else MyColor.TextColorLight,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                if (item.unreadCount > 0) {
                    SpaceWidth8()
                    Box(
                        modifier = Modifier
                            .size(MyDimen.p22)
                            .background(MyColor.Primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        CoreText(
                            text = item.unreadCount.toString(),
                            style = CoreTypography.labelSmall,
                            color = MyColor.White
                        )
                    }
                }
            }
        }
    }
}