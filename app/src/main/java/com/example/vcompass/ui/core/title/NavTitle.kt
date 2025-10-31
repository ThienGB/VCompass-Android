package com.example.vcompass.ui.core.title

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vcompass.core.resource.MyColor

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NavTitle(title: String = "Tìm kiếm khách sạn",
             onBackClick: () -> Unit = {}){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(MyColor.Primary)){
        Row(verticalAlignment = Alignment.CenterVertically,) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null,
                modifier = Modifier.padding(12.dp).size(30.dp)
                    .clickable { onBackClick() },
                tint = Color.White)
            Text(text = title,
                fontSize = 21.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth().padding(end = 40.dp),
                textAlign = TextAlign.Center)
        }
    }
}