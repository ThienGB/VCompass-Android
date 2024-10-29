import android.util.Log
import com.example.gotravel.data.model.Accommodation
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
    fun getAccommById(accommodationId: String): Accommodation? {
        return realm.where(Accommodation::class.java)
            .equalTo("accommodationId", accommodationId)
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
}
