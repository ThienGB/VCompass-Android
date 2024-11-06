package com.example.gotravel.ui.module.main.user

import AccommodationDao
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Search
import com.example.gotravel.data.remote.FirestoreDataManager
import com.example.gotravel.helper.RealmHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainUserViewModel(private val realmHelper: RealmHelper) : ViewModel() {
    private val firestoreDataManager = FirestoreDataManager()
    private var accomDao: AccommodationDao = AccommodationDao()
    private val _searchData = MutableStateFlow(Search())
    val searchData: StateFlow<Search> get() = _searchData
    private val _accommodations = MutableStateFlow<List<Accommodation>>(emptyList())
    val accommodations: StateFlow<List<Accommodation>> get() = _accommodations

    init {
        fetchAccommodation()
    }
    fun setSearchData(data: Search) {
        _searchData.value = data.copy()
    }
    fun fetchAccommodation(){
        viewModelScope.launch {
            firestoreDataManager.fetchAccommodation {
                getListAccom()
            }
        }
    }
    private fun getListAccom() {
        viewModelScope.launch {
            val accom = accomDao.getAllAccommodations()
            if (accom.isNotEmpty()) {
                _accommodations.value = accom
            } else {
                Log.d("AccommodationDetail", "No accommodation found")
            }
        }
    }
}