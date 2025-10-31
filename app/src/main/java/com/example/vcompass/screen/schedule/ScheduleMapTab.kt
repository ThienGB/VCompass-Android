package com.example.vcompass.screen.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ScheduleMapTab() {
    val routePoints = listOf(
        LatLng(10.3401, 107.0843), // Bãi Sau
        LatLng(10.3372, 107.0840), // Tượng Chúa Kitô Vua
        LatLng(10.3389, 107.0806), // Hải đăng Vũng Tàu
        LatLng(10.3460, 107.0848), // Bạch Dinh
        LatLng(10.3335, 107.0910), // Mũi Nghinh Phong
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(routePoints.first(), 14f)
    }

    Column {
        Text(
            text = "Ngày 1: 15/4/2025",
            fontSize = 26.sp,
            fontWeight = FontWeight.W700,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 10.dp, top = 15.dp, bottom = 5.dp)
        )

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            cameraPositionState = cameraPositionState
        ) {
            // Marker cho từng điểm
            routePoints.forEachIndexed { index, latLng ->
                Marker(
                    state = MarkerState(position = latLng),
                    title = "Điểm ${index + 1}",
                    snippet = when (index) {
                        0 -> "Bãi Sau"
                        1 -> "Tượng Chúa Kitô Vua"
                        2 -> "Hải đăng Vũng Tàu"
                        3 -> "Bạch Dinh"
                        4 -> "Mũi Nghinh Phong"
                        else -> "Điểm ${index + 1}"
                    }
                )
            }

            Polyline(
                points = routePoints,
                color = Color.Blue,
                width = 5f
            )
        }
    }
}