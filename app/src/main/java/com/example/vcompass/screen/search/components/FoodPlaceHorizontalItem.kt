package com.example.vcompass.screen.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.space.ExpandableSpacer
import com.example.vcompass.ui.core.text.CoreText
import com.vcompass.presentation.model.business.foodplace.FoodPlace
import com.vcompass.presentation.model.business.foodplace.getPriceRange
import com.vcompass.presentation.model.business.getFirstImage

@Composable
fun FoodPlaceHorizontalItem(
    foodPlace: FoodPlace,
    onClickItem: () -> Unit = {}
) {
    var isFavorite by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(MyDimen.p12),
        modifier = Modifier
            .fillMaxWidth()
            .height(MyDimen.p160)
            .padding(horizontal = MyDimen.p16, vertical = MyDimen.p8),
        colors = CardDefaults.cardColors(MyColor.White),
        elevation = CardDefaults.cardElevation(defaultElevation = MyDimen.p2)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickItem() }) {
            Row {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.3f)
                ) {
                    CoreImage(
                        source = CoreImageSource.Url(foodPlace.getFirstImage()),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(
                    modifier = Modifier.padding(MyDimen.p8)
                ) {
                    CoreText(
                        text = foodPlace.name,
                        style = CoreTypographySemiBold.labelLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(MyDimen.p4))
                    CoreText(
                        text = foodPlace.location?.address,
                        style = CoreTypography.displaySmall,
                        color = MyColor.TextColorLight
                    )
                    Spacer(modifier = Modifier.height(MyDimen.p4))
                    foodPlace.totalRatings?.let {
                        if (it > 0)
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(android.R.drawable.btn_star_big_on),
                                    contentDescription = null,
                                    tint = MyColor.Rating,
                                    modifier = Modifier.size(MyDimen.p12)
                                )
                                Spacer(modifier = Modifier.width(MyDimen.p2))
                                CoreText(
                                    text = foodPlace.averageRating.toString(),
                                    style = CoreTypography.displaySmall,
                                )
                                Spacer(modifier = Modifier.width(MyDimen.p4))
                                CoreText(
                                    text = stringResource(
                                        R.string.lb_no_of_rating,
                                        foodPlace.totalRatings.toString()
                                    ),
                                    style = CoreTypography.displaySmall,
                                    color = MyColor.TextColorLight
                                )
                            }
                    }
                    Spacer(modifier = Modifier.height(MyDimen.p4))
                    ExpandableSpacer()
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = MyDimen.p4)
                    ) {
                        CoreText(
                            text = foodPlace.getPriceRange(),
                            style = CoreTypographyBold.displayMedium,
                            color = MyColor.Black,
                            lineHeight = MyDimen.s16,
                        )
                    }
                }
            }
            FavoriteSelector(
                modifier = Modifier.align(Alignment.TopEnd),
                isFavorite = isFavorite,
                onFavoriteChange = { isFavorite = it }
            )
        }
    }
}