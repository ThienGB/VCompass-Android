package com.vcompass.core.compose_view

import android.R.attr.contentDescription
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vcompass.core.R
import com.vcompass.core.typography.CoreTypography

@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    @DrawableRes
    icon: Int = R.drawable.ic_category,
    text: String,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }.padding(16.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(icon)
                .crossfade(true)
                .build(),
            placeholder = debugPlaceholder(debugPreview = R.drawable.img_placeholder),
            contentDescription = contentDescription.toString(),
            modifier = Modifier.size(50.dp),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = W500,
            style = CoreTypography.displayMedium,
            maxLines = 2,
            minLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }

}

@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) = if (LocalInspectionMode.current) {
    painterResource(id = debugPreview)
} else {
    null
}

@Preview(showSystemUi = true)
@Composable
fun ButtonWithIconPreview() {
    Row {
        ButtonWithIcon(
            modifier = Modifier.clip(shape = RoundedCornerShape(99.dp)).weight(1f),
            icon = R.drawable.ic_category,
            text = "Category and growcy",
            onClick = { },
        )
        ButtonWithIcon(
            modifier = Modifier.clip(shape = RoundedCornerShape(8.dp)).weight(1f),
            icon = R.drawable.img_placeholder,
            text = "Category",
            onClick = { },
        )
        ButtonWithIcon(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_category,
            text = "Category",
            onClick = { },
        )
    }
}



