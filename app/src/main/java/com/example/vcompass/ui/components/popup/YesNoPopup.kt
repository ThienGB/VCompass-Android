package com.example.vcompass.ui.components.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.vcompass.R


@Composable
fun YesNoPopup(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onYesClick: () -> Unit,
    onNoClick: () -> Unit
) {
    if (showDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .zIndex(99f),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = colorResource(id = R.color.dark_slate_blue),
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bạn có chắc không?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                onYesClick()
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(Color.White),
                            modifier = Modifier
                                .height(48.dp)
                                .width(120.dp)
                                .clip(RoundedCornerShape(24.dp))
                        ) {
                            Text("Yes", color = colorResource(id = R.color.dark_slate_blue))
                        }
                        Button(
                            onClick = {
                                onNoClick()
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(Color.White),
                            modifier = Modifier
                                .height(48.dp)
                                .width(120.dp)
                                .clip(RoundedCornerShape(24.dp))
                        ) {
                            Text("No", color = colorResource(id = R.color.dark_slate_blue))
                        }
                    }
                }
            }
        }
    }
}
