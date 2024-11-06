package com.example.gotravel.ui.module.accomodation

import AccommodationDao
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.remote.FirestoreDataManager
import com.example.gotravel.helper.RealmHelper
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccommodationDetailViewModel (private val realmHelper: RealmHelper) : ViewModel(){
    private val firestoreDataManager = FirestoreDataManager()
    private var accomDao: AccommodationDao = AccommodationDao()
    private val _accommodations = MutableStateFlow(Accommodation())
    val accommodations: StateFlow<Accommodation> get() = _accommodations
    var currentId: String = ""

    init {
        viewModelScope.launch {
            firestoreDataManager.fetchAccommodation {
                fetchAccommodationsFromRealm()
            }
        }
        firestoreDataManager.listenToAccommodations { fetchAccommodationsFromRealm() }
    }
    private fun fetchAccommodationsFromRealm() {
        viewModelScope.launch {
            val accom = accomDao.getAccommById(currentId)
            if (accom != null) {
                _accommodations.value = accom.copy()
                val gson = Gson()
                val json = gson.toJson(accom)
                Log.d("AccommodationDetail", json)
            } else {
                Log.d("AccommodationDetail", "No accommodation found with the given ID.")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        realmHelper.closeRealm()
        firestoreDataManager.stopListening()
    }

}