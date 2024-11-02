package com.example.gotravel.ui.module.partner.Rooms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RoomViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val roomsCollection = db.collection("rooms")

    // Hàm lưu thông tin Room vào Firestore
    fun saveRoom(room: Room) {
        viewModelScope.launch {
            try {
                // Tạo một document mới trong Firestore với dữ liệu từ đối tượng Room
                roomsCollection.add(room).await()
                // Bạn có thể thêm logic thông báo khi lưu thành công
            } catch (e: Exception) {
                // Xử lý ngoại lệ nếu có lỗi
                e.printStackTrace()
            }
        }
    }
}
