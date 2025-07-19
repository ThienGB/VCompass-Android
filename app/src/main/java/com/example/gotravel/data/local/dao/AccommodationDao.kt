import android.util.Log
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Rating
import com.example.gotravel.data.model.Room
import com.example.gotravel.helper.FirestoreHelper
import com.example.gotravel.helper.FirestoreHelper.FL_PARTNERID
import com.example.gotravel.helper.RealmHelper
import io.realm.Realm
import io.realm.kotlin.where

class AccommodationDao() {
    private val realm: Realm = Realm.getDefaultInstance()
    fun getAllAccommodations(): List<Accommodation> {
        return realm.where<Accommodation>().findAll()
    }

    fun insertOrUpdateAccomm(accommodation: Accommodation, onSuccess: () -> Unit = {}) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                transactionRealm.insertOrUpdate(accommodation)
            },
            {
                Log.d("RealmNotification", "Inserted Accommodation")
                onSuccess()
            },
            { error ->
                Log.e("RealmNotification", "Error inserting Accommodation", error)
            }
        )
    }
    fun insertOrUpdateAccomm(accommodations: List<Accommodation>, onSuccess: () -> Unit = {}) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                transactionRealm.where<Accommodation>().findAll()?.deleteAllFromRealm()
                transactionRealm.insertOrUpdate(accommodations)
            },
            {
                Log.d("RealmNotification", "Inserted/Updated Accommodations")
                onSuccess()  // Gọi onSuccess khi tất cả các phần tử được chèn hoặc cập nhật thành công
            },
            { error ->
                Log.e("RealmNotification", "Error inserting/updating Accommodations", error)
            }
        )
    }
    fun insertRating(accommodationId: String, newRating: Rating) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                val accommodation = transactionRealm.where(Accommodation::class.java)
                    .equalTo("accommodationId", accommodationId)
                    .findFirst()
                if (accommodation != null) {
                    accommodation.ratings.add(newRating)
                    transactionRealm.insert(newRating)
                } else {
                    Log.e("RealmNotification", "Accommodation with ID $accommodationId not found.")
                }
            },
            {
                Log.d("RealmNotification", "Rating ${newRating.ratingId} added successfully.")
            },
            { error ->
                Log.e("RealmNotification", "Error adding Rating: $error")
            }
        )
    }
    fun updateRatingResponse(accommodationId: String, ratingId: String, newResponse: String, responseTime: Long,  onSuccess: () -> Unit) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                val accommodation = transactionRealm.where(Accommodation::class.java)
                    .equalTo("accommodationId", accommodationId)
                    .findFirst()

                accommodation?.ratings?.find { it.ratingId == ratingId }?.let { rating ->
                    rating.response = newResponse
                    rating.responseTime = responseTime
                }
            },
            {
                onSuccess()
            },
            { error ->
                Log.e("RealmNotification", "Error updating Rating: $error")
            }
        )
    }
    fun insertRoom(accommodationId: String, room: Room, onSuccess: () -> Unit = {}) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                val accommodation = transactionRealm.where(Accommodation::class.java)
                    .equalTo("accommodationId", accommodationId)
                    .findFirst()
                if (accommodation != null) {
                    accommodation.rooms.add(room)
                    transactionRealm.insertOrUpdate(room)
                } else {
                    Log.e("RealmNotification", "Accommodation with ID $accommodationId not found.")
                }
            },
            {
                onSuccess()
                Log.d("RealmNotification", "Rating ${room.roomId} added successfully.")
            },
            { error ->
                Log.e("RealmNotification", "Error adding Rating: $error")
            }
        )
    }
    fun getAccommById(accommodationId: String): Accommodation? {
        return realm.where(Accommodation::class.java)
            .equalTo("accommodationId", accommodationId)
            .findFirst()
    }
    fun getAccommByPartner(partnerId: String): Accommodation? {
        return realm.where(Accommodation::class.java)
            .equalTo(FL_PARTNERID, partnerId)
            .findFirst()
    }

    fun deleteAccommodation(accommodationId: String) {
        realm.executeTransaction { transactionRealm ->
            val existingAccommodation = transactionRealm.where<Accommodation>()
                .equalTo("accommodationId", accommodationId)
                .findFirst()
            existingAccommodation?.deleteFromRealm()
        }
    }
    fun deleteAllAccommodation() {
        realm.executeTransactionAsync { transactionRealm ->
            transactionRealm.where<Accommodation>().findAll()?.deleteAllFromRealm()
        }
    }


}
