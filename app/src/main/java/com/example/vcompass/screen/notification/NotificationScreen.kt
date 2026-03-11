package com.example.vcompass.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.divider.ItemDivider
import com.example.vcompass.ui.core.general.BaseView
import com.example.vcompass.ui.core.general.BaseViewWithPullToRefresh
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.ui.core.tab.TabView
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.title.ActionIcon
import com.example.vcompass.ui.core.title.TitleBarAction
import com.example.vcompass.util.ScreenContext
import com.vcompass.presentation.viewmodel.accommodation.AccommodationDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotificationScreen() {
    val viewModel: AccommodationDetailViewModel = koinViewModel()
    val navController = ScreenContext.navController
    val state by viewModel.stateUI.collectAsState()
    val notifications = remember {
        listOf(
            Notification(
                "1",
                "Chuyến đi Đà Lạt sắp tới",
                "Đừng quên kiểm tra lịch trình chi tiết cho chuyến đi ngày mai của bạn.",
                "2 phút trước",
                R.drawable.ic_calendar,
                true
            ),
            Notification(
                "2",
                "Gợi ý điểm đến mới",
                "Khám phá những bãi biển hoang sơ tại Phú Quốc đang chờ bạn.",
                "1 giờ trước",
                R.drawable.ic_notification,
                false
            ),
            Notification(
                "3",
                "Cập nhật hệ thống",
                "Chúng tôi vừa cập nhật tính năng chia sẻ lịch trình mới.",
                "5 giờ trước",
                R.drawable.ic_notification,
                false
            ),
            Notification(
                "4",
                "Vé máy bay đã sẵn sàng",
                "Vé chuyến bay VN123 của bạn đã được xác nhận.",
                "Hôm qua",
                R.drawable.ic_food,
                true
            )
        )
    }
    val tabs = listOf("Tất cả", "Chưa đọc")
    var selectedTabIndex by remember { mutableStateOf(0) }
    val filtered = if (selectedTabIndex == 0) {
        notifications
    } else {
        notifications.filter { it.isUnread }
    }
    BaseViewWithPullToRefresh(
        viewModel = viewModel,
        state = state,
        navController = navController,
        topBar = {
            TitleBarAction(
                text = "Thông báo",
                actionContent = {
                    ActionIcon(imageVector = Icons.Rounded.DoneAll) { }
                }
            )
        }
    ) {
        TabView(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it },
            isScrollable = true,
            tabTitle = { it }
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                filtered.forEach {
                    NotificationItem(
                        item = it,
                        onClick = {}
                    )
                    ItemDivider()
                }
            }
        }
    }
}

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val icon: Int,
    val isUnread: Boolean
)

@Composable
fun NotificationItem(
    item: Notification,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (item.isUnread) MyColor.Primary.copy(alpha = 0.05f)
                else MyColor.White
            )
            .clickable { onClick() }
            .padding(MyDimen.p16)
    ) {

        Box {
            Box(
                modifier = Modifier
                    .size(MyDimen.p44)
                    .clip(CircleShape)
                    .background(MyColor.Primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                CoreIcon(
                    resDrawable = item.icon,
                    tintColor = MyColor.Primary
                )
            }

            if (item.isUnread) {
                Box(
                    modifier = Modifier
                        .size(MyDimen.p8)
                        .align(Alignment.CenterStart)
                        .offset(x = -MyDimen.p10)
                        .background(MyColor.Primary, CircleShape)
                )
            }
        }

        SpaceWidth8()

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                CoreText(
                    text = item.title,
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

            CoreText(
                text = item.message,
                style = CoreTypography.labelSmall,
                color = MyColor.TextColorLight
            )
        }
    }
}

