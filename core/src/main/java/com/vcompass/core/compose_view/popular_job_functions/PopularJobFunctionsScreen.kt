package com.vcompass.core.compose_view.popular_job_functions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vcompass.core.R
import com.vcompass.core.utils.extension.randomColor

data class JobFunction(
    val id: String? = null,
    val name: String? = null,
    val image: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularJobFunctionsScreen(
    jobFunctions: List<JobFunction> = listOf(),
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onBack: () -> Unit = {},
    onItemClick: (JobFunction) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        _root_ide_package_.com.vcompass.core.compose_view.TitleBarAction(
            isHaveActionRight = false,
            onBack = onBack,
            text = stringResource(R.string.lb_popular_job_functions)
        )
        _root_ide_package_.com.vcompass.core.compose_view.PullToRefreshContainer(
            modifier = Modifier
                .weight(1f),
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            content = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.content_padding)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.content_padding)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.content_padding)),
                ) {
                    // Add Header Spacer
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {}
                    itemsIndexed(
                        jobFunctions,
                        key = { _, it -> it.id.toString() }) { index, jobFunction ->
                        JobFunctionItem(
                            jobFunction = jobFunction,
                            onItemClick = {
                                onItemClick(it)
                            }
                        )
                    }
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.navigation_bar_height)))
                    }
                }
            }
        )
    }
}

@Composable
fun JobFunctionItem(
    jobFunction: JobFunction,
    onItemClick: (JobFunction) -> Unit
) {
    val radius by remember { mutableStateOf(6.dp) }
    val randomIntImage = randomColor()
    Card(
        shape = RoundedCornerShape(radius),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.clickable { onItemClick(jobFunction) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(Color.White)
        ) {
            AsyncImage(
                model = jobFunction.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(topStart = radius, topEnd = radius))
                    .background(randomIntImage),
                placeholder = painterResource(R.drawable.img_placeholder),
            )
            Text(
                text = jobFunction.name.toString(),
                fontSize = 14.sp,
                color = colorResource(R.color.textColorDark),
                fontWeight = FontWeight.W500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(dimensionResource(R.dimen.vertical_space_small))
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PopularJobFunctionsPreview() {
    val image =
        "https://logowik.com/content/uploads/images/affinity-designer-icon1720733537.logowik.com.webp"
    val jobFunctions = listOf(
        JobFunction(id = "1", name = "Software Engineer", image = image),
        JobFunction(id = "2", name = "Product Manager", image = image),
        JobFunction(id = "3", name = "Designer", image = image),
        JobFunction(id = "4", name = "Data Scientist"),
        JobFunction(id = "5", name = "Marketing Specialist"),
        JobFunction(id = "6", name = "Sales Representative"),
        JobFunction(id = "7", name = "Financial Analyst"),
        JobFunction(id = "8", name = "Human Resources Manager", image = image),
    )
    PopularJobFunctionsScreen(jobFunctions = jobFunctions)
}
