package com.example.vcompass.screen.schedule

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vcompass.R
import com.example.vcompass.data.api.model.Schedule
import com.example.vcompass.helper.CommonUtils.formatCurrency
import com.vcompass.core.compose_view.image.CoreIcon
import com.vcompass.core.compose_view.space.SpaceHeight
import com.vcompass.core.compose_view.space.SpaceWidth4
import com.vcompass.core.resource.MyColor
import com.vcompass.core.resource.MyDimen

@Composable
fun PriceSection(
    schedule: Schedule?
) {
    val total = schedule?.calculateTotalCost()
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MyColor.DarkBlue)
            .padding(top = MyDimen.p20),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = formatCurrency(total.toString()), fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.W700,
            letterSpacing = 1.5.sp
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(99.dp))
                .clickable { isExpanded = !isExpanded }
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(99.dp)
                )
                .padding(horizontal = 20.dp, vertical = 8.dp)

        ) {
            Text(
                text = "Xem chi tiết", fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.W500,
                maxLines = 1
            )
            CoreIcon(
                imageVector = if (!isExpanded) Icons.AutoMirrored.Rounded.KeyboardArrowRight
                else Icons.AutoMirrored.Rounded.ArrowForward,
                tintColor = MyColor.White,
                iconModifier = Modifier.size(MyDimen.p20)
            )
        }
        SpaceHeight(MyDimen.p30)
    }
    AnimatedVisibility(
        visible = isExpanded,
        enter = expandVertically(animationSpec = tween(500)) + fadeIn(),
        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-12).dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(vertical = 10.dp, horizontal = 15.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Chi tiêu", fontSize = 25.sp, fontWeight = FontWeight.W700)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Sắp xếp: ",
                    fontWeight = FontWeight.W600,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Ngày (tăng dần)",
                    fontWeight = FontWeight.W400,
                    color = Color.Gray,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                )
                SpaceWidth4()
                CoreIcon(
                    resDrawable = R.drawable.ic_filter_24dp,
                    iconModifier = Modifier.size(MyDimen.p16)
                )
            }
            SpaceHeight()
            schedule?.activities?.forEach { day ->
                day.activity?.filter { (it.cost ?: 0) > 0 }?.forEach { activity ->
                    PriceItem(
                        title = activity.costDescription ?: activity.name.toString(),
                        price = activity.cost ?: 0,
                        type = activity.activityType
                    )
                }
            }
            schedule?.additionalExpenses?.filter { (it.cost ?: 0) > 0 }
                ?.forEach { expense ->
                    PriceItem(
                        title = expense.description ?: expense.description
                        ?: "Chi phí khác",
                        price = expense.cost ?: 0,
                        type = "other"
                    )
                }
            //// Thiếu thêm chi phí phát sinh
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun PriceItem(
    title: String,
    price: Int,
    type: String
) {
    val typeName = if (type == "Accommodation") "Chỗ nghỉ"
    else if (type == "FoodService") "Ăn uống"
    else if (type == "Attraction") "Tham quan"
    else "Hoạt động khác"
    Row(verticalAlignment = Alignment.CenterVertically) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(99.dp))
                .background(MyColor.Gray999)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(
                    if (type == "Accommodation") R.drawable.ic_accommodation_24dp
                    else if (type == "FoodService") R.drawable.ic_food
                    else if (type == "Attraction") R.drawable.ic_beach_24dp
                    else R.drawable.ic_notification
                ),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                fontWeight = FontWeight.W600,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = typeName,
                fontWeight = FontWeight.W400,
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = formatCurrency(price.toString()) + " đ",
            fontWeight = FontWeight.W600,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 20.dp)
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
    Spacer(modifier = Modifier.height(5.dp))
}

fun Schedule.calculateTotalCost(): Int {
    val activityCost = activities?.sumOf { day ->
        day.activity?.sumOf { it.cost ?: 0 } ?: 0
    } ?: 0
    val additionalCost = additionalExpenses?.sumOf { it.cost ?: 0 } ?: 0
    return activityCost + additionalCost
}