package com.example.gotravel.ui.factory

import com.example.gotravel.helper.RealmHelper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory<T : ViewModel>(
    private val viewModelClass: Class<T>,
    private val realmHelper: RealmHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (viewModelClass.isAssignableFrom(modelClass)) {
            // Khởi tạo ViewModel với com.example.gotravel.helper.RealmHelper
            return viewModelClass.getConstructor(RealmHelper::class.java).newInstance(realmHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}