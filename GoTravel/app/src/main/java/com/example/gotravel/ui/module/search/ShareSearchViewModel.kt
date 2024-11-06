package com.example.gotravel.ui.module.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Search
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShareSearchViewModel : ViewModel() {
    private val _searchData = MutableStateFlow(Search())
    val searchData: StateFlow<Search> get() = _searchData

    fun setSearchData(data: Search) {
        _searchData.value = data.copy()
    }
}