package com.example.gotravel.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotravel.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun CustomSearchBar(
    onTextChange: (String) -> Unit = {},
    placeholder: String = "Tìm kiếm theo tên hoặc email",
){
    val interactionSource = remember { MutableInteractionSource() }
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
        BasicTextField(
            value = message,
            onValueChange = { newValue ->
                message = newValue
                onTextChange(newValue)
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = LocalContentColor.current // đảm bảo giữ màu text đúng
            ),
            singleLine = true,
            modifier = Modifier
                .height(38.dp)
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.colorSeparator),
                    shape = RoundedCornerShape(9.dp)
                ),
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = message,
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    placeholder = {
                        Text(
                            text = placeholder,
                            color = colorResource(id = R.color.text_grey_hint),
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null,
                            modifier = Modifier.padding(2.dp)
                        )
                    },
                    trailingIcon = {
                        if (message.isNotEmpty()) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_cancel),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clickable { message = "" }
                            )
                        }
                    },
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp),
                    container = {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(9.dp)
                                )
                        )
                    },
                )
            }
        )
    }

}