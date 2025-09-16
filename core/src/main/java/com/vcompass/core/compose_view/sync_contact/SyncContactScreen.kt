package com.vcompass.core.compose_view.sync_contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vcompass.core.R
import com.vcompass.core.compose_view.AvatarModel
import com.vcompass.core.compose_view.ButtonNoIcon
import com.vcompass.core.compose_view.TitleBarAction
import com.vcompass.core.compose_view.UserAvatar
import com.vcompass.core.typography.CoreTypography

@Composable
fun SyncContactScreen(
    avatar: AvatarModel = AvatarModel.None,
    onBack: () -> Unit = {},
    onAddContactClicked: () -> Unit = {}
) {
    val fakeAvatar = listOf(
        R.drawable.fk_avatar1,
        R.drawable.fk_avatar2,
        R.drawable.fk_avatar3,
        R.drawable.fk_avatar4,
        R.drawable.fk_avatar5,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBarAction(isHaveActionRight = false, onBack = onBack)
        Spacer(modifier = Modifier.height(32.dp))
        UserAvatar(
            modifier = Modifier.size(110.dp),
            avatar = avatar
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            fontSize = 25.sp,
            fontWeight = W600,
            style = CoreTypography.displayMedium,
            text = stringResource(R.string.lb_syn_contact_title),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.lb_sync_contact_desc),
            textAlign = TextAlign.Center,
            style = CoreTypography.displayMedium,
            fontWeight = W500,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = colorResource(R.color.textColorLight)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow (horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(fakeAvatar){ index, item  ->
                if (index == fakeAvatar.size - 1){
                    Box (
                        contentAlignment = Alignment.Center,
                    ){
                        UserAvatar(
                            modifier = Modifier.size(54.dp),
                            avatar = AvatarModel.Resource(item)
                        )
                        Text(
                            text = "•••",
                            fontSize = 16.sp,
                            lineHeight = 54.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            modifier = Modifier.clip(CircleShape).background(Color.Black.copy(0.6f)).size(54.dp)
                        )
                    }
                } else {
                    UserAvatar(
                        modifier = Modifier.size(54.dp),
                        avatar = AvatarModel.Resource(item)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.lb_num_of_friend_joined),
            color = colorResource(R.color.textColorLight),
            fontSize = 13.sp,
            style = CoreTypography.displayMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonNoIcon(
            text = stringResource(R.string.btn_add_from_contacts),
            onClick = { onAddContactClicked },
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.padding_32dp),
                end = dimensionResource(R.dimen.padding_32dp),
                bottom = 70.dp
            ),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SyncContactScreenPreview() {
    SyncContactScreen()
}
