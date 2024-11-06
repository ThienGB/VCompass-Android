package com.example.gotravel.data.remote

import AccommodationDao
import android.util.Log
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.helper.FirestoreHelper.CL_ACCOM
import com.example.gotravel.helper.FirestoreHelper.FL_ACCOMID
import com.example.gotravel.helper.FirestoreHelper.FL_ADDRESS
import com.example.gotravel.helper.FirestoreHelper.FL_CITYID
import com.example.gotravel.helper.FirestoreHelper.FL_DESCRIPTION
import com.example.gotravel.helper.FirestoreHelper.FL_IMAGE
import com.example.gotravel.helper.FirestoreHelper.FL_LATITUDE
import com.example.gotravel.helper.FirestoreHelper.FL_LONGTITUDE
import com.example.gotravel.helper.FirestoreHelper.FL_NAME
import com.example.gotravel.helper.FirestoreHelper.FL_PRICE
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreDataManager () {
    private var accomDao: AccommodationDao = AccommodationDao()
    private var listenerRegistration: ListenerRegistration? = null
    private val db = Firebase.firestore
    fun listenToAccommodations(onDataUpdated: () -> Unit) {
        listenerRegistration = db.collection(CL_ACCOM)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                snapshot?.documentChanges?.forEach { change ->
                    when (change.type) {
                        DocumentChange.Type.ADDED -> {
                            val accommodation = change.document.toObject<Accommodation>()
                                accomDao.insertOrUpdateAccomm(accommodation, onDataUpdated)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val accommodation = change.document.toObject<Accommodation>()
                                accomDao.insertOrUpdateAccomm(accommodation, onDataUpdated)
                        }
                        DocumentChange.Type.REMOVED -> {
                            val accommodationId = change.document.id
                                accomDao.deleteAccommodation(accommodationId)
                                onDataUpdated()
                        }
                    }
                }
            }
    }

    suspend fun fetchAccommodation(onComplete: () -> Unit) {
        try {
            val result = db.collection(CL_ACCOM).get().await()
            for (document in result) {
                val accommodation = document.toObject<Accommodation>()
                accomDao.insertOrUpdateAccomm(accommodation, onComplete)
                Log.d("fetchAccommodation", "Document ID: ${document.id}")
            }
        } catch (exception: Exception) {
            Log.w("FirestoreDataManager", "Error getting documents.", exception)
        }
    }

    fun stopListening() {
        listenerRegistration?.remove()
    }
    fun addAccommodation(accom: Accommodation) {
        val user: MutableMap<String, Any> = HashMap()
        user[FL_ACCOMID] = accom.accommodationId.toString()
        user[FL_NAME] = accom.name.toString()
        user[FL_PRICE] = accom.price
        user[FL_IMAGE] = accom.image.toString()
        user[FL_DESCRIPTION] = accom.description.toString()
        user[FL_ADDRESS] = accom.address.toString()
        user[FL_CITYID] = accom.cityId.toString()
        db.collection(CL_ACCOM).document(accom.accommodationId.toString()).set(user)
    }
}
