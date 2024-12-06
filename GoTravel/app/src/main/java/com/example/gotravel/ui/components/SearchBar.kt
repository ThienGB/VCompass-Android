package com.example.gotravel.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotravel.R

@Preview(showSystemUi = true)
@Composable
fun CustomSearchBar(
    onTextChange: (String) -> Unit = {},
    placeholder: String = "Tìm kiếm theo tên hoặc email",
){
    var message by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 5.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = message,
            onValueChange = { newValue ->
                message = newValue
                onTextChange(newValue)},
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.padding(6.dp)
                )
            },
            textStyle = TextStyle(
                fontSize = 14.sp),
            trailingIcon = {
                if (message.isNotEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(6.dp)
                            .clickable { message = "" }
                    )
                }
            },
            placeholder = {
                Text(text = placeholder,
                    color = colorResource(id = R.color.text_grey_hint),
                    fontSize = 17.sp)
            },
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.colorSeparator),
                    shape = RoundedCornerShape(9.dp)
                ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent),
        )
    }

}