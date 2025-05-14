package com.example.gotravel.ui.components.sheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.gotravel.R
import com.example.gotravel.helper.CommonUtils.formatCurrency
import com.example.gotravel.ui.components.CustomSearchBar
import com.example.gotravel.ui.custom_property.clickableWithScale

@Preview(showSystemUi = true)
@Composable
fun AddActivityBottomSheet(
    onDismiss: () -> Unit = {}
){
    var selectedBoxIndex by remember { mutableStateOf<Int?>(null) }
    Column (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.94f)
        .background(Color.White)
        .padding(horizontal = 10.dp)) {
        Row(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Thêm hoạt động",
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                modifier = Modifier
                    .weight(1f)
            )
        }
        AnimatedContent(
            targetState = selectedBoxIndex,
            transitionSpec = {
                if (targetState != null)
                    slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                else
                    slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
            },
            label = "BoxTransition"
        ) { index ->
            if (index == null) {
                Column {
                    Spacer(modifier = Modifier.height(25.dp))
                    Box (contentAlignment = Alignment.Center,
                        modifier = Modifier.clip(RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_accommodation),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.White.copy(alpha = 0.1f)),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row (modifier = Modifier
                                .shadow(
                                    6.dp,
                                    shape = RoundedCornerShape(99.dp),
                                    ambientColor = Color.Black.copy(alpha = 0.5f)
                                )
                                .clickableWithScale { selectedBoxIndex = 0 }
                                .clip(RoundedCornerShape(99.dp))
                                .background(colorResource(id = R.color.priceScheduleBackground))
                                .padding(vertical = 8.dp, horizontal = 30.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_accommodation_selected),
                                    contentDescription = null,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Chỗ ở",
                                    fontWeight = FontWeight.W600,
                                    fontSize = 16.sp,
                                )
                            }
                        }
                    }
                    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray, modifier = Modifier.padding(vertical = 8.dp))
                    Box (contentAlignment = Alignment.Center,
                        modifier = Modifier.clip(RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_ha_noi),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.White.copy(alpha = 0.1f)),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row (modifier = Modifier
                                .shadow(
                                    6.dp,
                                    shape = RoundedCornerShape(99.dp),
                                    ambientColor = Color.Black.copy(alpha = 0.5f)
                                )
                                .clickableWithScale { selectedBoxIndex = 1 }
                                .clip(RoundedCornerShape(99.dp))
                                .background(colorResource(id = R.color.timeScheduleBackground))
                                .padding(vertical = 8.dp, horizontal = 30.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_beach),
                                    contentDescription = null,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Tham quan",
                                    fontWeight = FontWeight.W600,
                                    fontSize = 16.sp,
                                )
                            }
                        }
                    }
                    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray, modifier = Modifier.padding(vertical = 8.dp))
                    Box (contentAlignment = Alignment.Center,
                        modifier = Modifier.clip(RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_food_service),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.White.copy(alpha = 0.1f)),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row (modifier = Modifier
                                .shadow(
                                    6.dp,
                                    shape = RoundedCornerShape(99.dp),
                                    ambientColor = Color.Black.copy(alpha = 0.5f)
                                )
                                .clickableWithScale { selectedBoxIndex = 2 }
                                .clip(RoundedCornerShape(99.dp))
                                .background(colorResource(id = R.color.yellowBackground))
                                .padding(vertical = 8.dp, horizontal = 30.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_food),
                                    contentDescription = null,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Ăn uống",
                                    fontWeight = FontWeight.W600,
                                    fontSize = 16.sp,
                                )
                            }
                        }
                    }
                    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray, modifier = Modifier.padding(vertical = 8.dp))
                    Box (contentAlignment = Alignment.Center,
                        modifier = Modifier.clip(RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_hue),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.White.copy(alpha = 0.2f)),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row (modifier = Modifier
                                .shadow(
                                    6.dp,
                                    shape = RoundedCornerShape(99.dp),
                                    ambientColor = Color.Black.copy(alpha = 0.5f)
                                )
                                .clickableWithScale { selectedBoxIndex = 3 }
                                .clip(RoundedCornerShape(99.dp))
                                .background(colorResource(id = R.color.purpleBackground))
                                .padding(vertical = 8.dp)
                                .padding(horizontal = 30.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_notification),
                                    contentDescription = null,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Khác",
                                    fontWeight = FontWeight.W600,
                                    fontSize = 16.sp,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
            else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(22.dp)
                                .clickableWithScale { selectedBoxIndex = null }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        CustomSearchBar(placeholder = "Tìm theo tên hoặc địa chỉ")
                    }
                    Column (modifier = Modifier.verticalScroll(rememberScrollState())
                        .padding(vertical = 10.dp)) {
                        repeat(9){
                            ActivityCard(onDismiss)
                        }
                    }
                }
            }
        }

    }
}
@Composable
fun ActivityCard(
    onDismiss: () -> Unit
){
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(vertical = 3.dp)
            .fillMaxWidth()
            .clickableWithScale { onDismiss() }
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.img_accommodation),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(110.dp)
                    .width(90.dp)
                    .padding(end = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .height(110.dp)
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Paradise Home Ton Duc Thang",
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(4) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color.Yellow,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                    repeat(5 - 4) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(painter = painterResource(id = R.drawable.ic_rating),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.primary)),
                        modifier = Modifier
                            .size(18.dp)
                    )
                    Text(
                        text = "123",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Text(
                    text = "Vias Hotel Vung Tau, 179, Thuy Van Street, Khu Phương Nam, Ward 8, Vũng Tàu, Bà Rịa - Vũng Tàu Province, 76666, Vietnam",
                    maxLines = 2,
                    color = colorResource(id = R.color.primary),
                    fontSize = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                    fontStyle = FontStyle.Italic
                )
                Row (horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = formatCurrency("300000") + " đ",
                        color = colorResource(id = R.color.primary),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
