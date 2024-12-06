package com.example.gotravel.data.remote

import AccommodationDao
import BookingDao
import android.util.Log
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Booking
import com.example.gotravel.data.model.Rating
import com.example.gotravel.data.model.Room
import com.example.gotravel.helper.FirestoreHelper.CL_ACCOM
import com.example.gotravel.helper.FirestoreHelper.CL_BOOKING
import com.example.gotravel.helper.FirestoreHelper.CL_RATING
import com.example.gotravel.helper.FirestoreHelper.CL_ROOM
import com.example.gotravel.helper.FirestoreHelper.FL_ACCOMID
import com.example.gotravel.helper.FirestoreHelper.FL_ACCOMNAME
import com.example.gotravel.helper.FirestoreHelper.FL_ADDRESS
import com.example.gotravel.helper.FirestoreHelper.FL_AMENTITIES
import com.example.gotravel.helper.FirestoreHelper.FL_AREA
import com.example.gotravel.helper.FirestoreHelper.FL_BED
import com.example.gotravel.helper.FirestoreHelper.FL_BOOKINGID
import com.example.gotravel.helper.FirestoreHelper.FL_CITY
import com.example.gotravel.helper.FirestoreHelper.FL_CONTENT
import com.example.gotravel.helper.FirestoreHelper.FL_CREATEDAT
import com.example.gotravel.helper.FirestoreHelper.FL_DESCRIPTION
import com.example.gotravel.helper.FirestoreHelper.FL_EMAIL
import com.example.gotravel.helper.FirestoreHelper.FL_ENDDATE
import com.example.gotravel.helper.FirestoreHelper.FL_FULLNAME
import com.example.gotravel.helper.FirestoreHelper.FL_IMAGE
import com.example.gotravel.helper.FirestoreHelper.FL_NAME
import com.example.gotravel.helper.FirestoreHelper.FL_PARTNERID
import com.example.gotravel.helper.FirestoreHelper.FL_PEOPLE
import com.example.gotravel.helper.FirestoreHelper.FL_PHONE
import com.example.gotravel.helper.FirestoreHelper.FL_PRICE
import com.example.gotravel.helper.FirestoreHelper.FL_RATE
import com.example.gotravel.helper.FirestoreHelper.FL_RATINGID
import com.example.gotravel.helper.FirestoreHelper.FL_ROOMID
import com.example.gotravel.helper.FirestoreHelper.FL_ROOMTYPE
import com.example.gotravel.helper.FirestoreHelper.FL_STARTDATE
import com.example.gotravel.helper.FirestoreHelper.FL_STATUS
import com.example.gotravel.helper.FirestoreHelper.FL_TOTALRATE
import com.example.gotravel.helper.FirestoreHelper.FL_USERID
import com.example.gotravel.helper.FirestoreHelper.FL_USERNAME
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import io.realm.RealmList
import kotlinx.coroutines.tasks.await

class FirestoreDataManager () {
    private var accomDao: AccommodationDao = AccommodationDao()
    private var bookingDao: BookingDao = BookingDao()
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
            val accommodationsList = mutableListOf<Accommodation>()

            for (document in result) {
                val accommodation = document.toObject<Accommodation>().apply {
                    accommodationId = document.id
                }
                val roomsSnapshot = db.collection(CL_ACCOM)
                    .document(document.id)
                    .collection("rooms")
                    .get().await()
                val rooms = roomsSnapshot.documents.mapNotNull { roomDoc ->
                    roomDoc.toObject(Room::class.java)?.apply { roomId = roomDoc.id }
                }
                val reviewsSnapshot = db.collection(CL_ACCOM)
                    .document(document.id)
                    .collection("ratings")
                    .get().await()
                val ratings = reviewsSnapshot.documents.mapNotNull { ratingDoc ->
                    ratingDoc.toObject(Rating::class.java)?.apply { ratingId = ratingDoc.id }
                }
                accommodation.rooms = RealmList<Room>().apply { addAll(rooms) }
                accommodation.ratings = RealmList<Rating>().apply { addAll(ratings) }
                accommodationsList.add(accommodation)
            }
            if (accommodationsList.isNotEmpty()) {
                accomDao.insertOrUpdateAccomm(accommodationsList, onComplete)
            } else {
                onComplete()
            }

        } catch (exception: Exception) {
            Log.w("FirestoreDataManager", "Error getting documents.", exception)
        }
    }

    suspend fun fetchBooking(onComplete: () -> Unit) {
        try {
            val result = db.collection(CL_BOOKING).get().await()
            val bookingsList = mutableListOf<Booking>()
            for (document in result) {
                val booking = document.toObject<Booking>()
                bookingsList.add(booking)
            }
            if (bookingsList.isNotEmpty()) {
                bookingDao.insertOrUpdateBooking(bookingsList, onComplete)
            } else {
                onComplete()
            }

        } catch (exception: Exception) {
            Log.w("FirestoreDataManager", "Error getting documents.", exception)
        }
    }

    fun stopListening() {
        listenerRegistration?.remove()
    }
    fun addBookingToFirestore(booking: Booking) {
        val bookingMap = hashMapOf(
            FL_BOOKINGID to booking.bookingId,
            FL_ROOMID to booking.roomId,
            FL_STARTDATE to booking.startDate,
            FL_ENDDATE to booking.endDate,
            FL_ACCOMID to booking.accommodationId,
            FL_ACCOMNAME to booking.accommodationName,
            FL_USERID to booking.userId,
            FL_PRICE to booking.price,
            FL_STATUS to booking.status,
            FL_PHONE to booking.phoneNumber,
            FL_FULLNAME to booking.fullName,
            FL_EMAIL to booking.email
        )
        db.collection(CL_BOOKING)
            .document(booking.bookingId)
            .set(bookingMap)
            .addOnSuccessListener {
                println("Booking added successfully!")
            }
            .addOnFailureListener { e ->
                println("Error adding booking: $e")
            }
    }
    fun updateRatingsInFirestore(accommodationId: String, rating: Rating) {
        val ratingsCollectionRef = db.collection(CL_ACCOM)
            .document(accommodationId)
            .collection(CL_RATING)
            val ratingMap = hashMapOf(
                FL_RATINGID to rating.ratingId,
                FL_RATE to rating.rate,
                FL_CONTENT to rating.content,
                FL_USERNAME to rating.userName,
                FL_CREATEDAT to rating.createdAt,
                FL_USERID to rating.userId
            )
            ratingsCollectionRef
                .document(rating.ratingId)
                .set(ratingMap)
                .addOnSuccessListener {
                    println("Rating ${rating.ratingId} updated successfully!")
                }
                .addOnFailureListener { e ->
                    println("Error updating rating ${rating.ratingId}: $e")
                }
    }
    fun updateResponseRating(accommodationId: String, ratingId: String, response: String, responseTime: Long) {
        val ratingsCollectionRef = db.collection(CL_ACCOM)
            .document(accommodationId)
            .collection(CL_RATING)

        val updates = mapOf(
            "response" to response,
            "responseTime" to responseTime
        )
        ratingsCollectionRef
            .document(ratingId)
            .update(updates)
            .addOnSuccessListener {
                println("Rating ${ratingId} updated successfully!")
            }
            .addOnFailureListener { e ->
                println("Error updating rating ${ratingId}: $e")
            }
    }
    fun updateRoomsInFirestore(accommodationId: String, room: Room) {
        val ratingsCollectionRef = db.collection(CL_ACCOM)
            .document(accommodationId)
            .collection(CL_ROOM)
        val roomMap = hashMapOf(
            FL_ROOMID to room.roomId,
            FL_NAME to room.name,
            FL_ROOMTYPE to room.roomType,
            FL_PRICE to room.price,
            FL_PEOPLE to room.people,
            FL_BED to room.bed,
            FL_IMAGE to room.image,
            FL_AREA to room.area,
            FL_STATUS to room.status
        )
        ratingsCollectionRef
            .document(room.roomId)
            .set(roomMap)
            .addOnSuccessListener {
                println("Rating ${room.roomId} updated successfully!")
            }
            .addOnFailureListener { e ->
                println("Error updating rating ${room.roomId}: $e")
            }
    }
    fun addAccommodationToFirestore(accommodation: Accommodation) {
        val accomMap = hashMapOf(
            FL_ACCOMID to accommodation.accommodationId,
            FL_PARTNERID to accommodation.partnerId,
            FL_NAME to accommodation.name,
            FL_IMAGE to accommodation.image,
            FL_DESCRIPTION to accommodation.description,
            FL_ADDRESS to accommodation.address,
            FL_CITY to accommodation.city,
            FL_AMENTITIES to accommodation.amentities,
            FL_TOTALRATE to accommodation.totalRate,
            FL_STATUS to accommodation.status
        )
        db.collection(CL_ACCOM)
            .document(accommodation.accommodationId)
            .set(accomMap)
            .addOnSuccessListener {
                println("Accommodation added successfully!")
            }
            .addOnFailureListener { e ->
                println("Error adding accommodation: $e")
            }
    }
}
