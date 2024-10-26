import android.util.Log
import com.example.gotravel.data.model.Accommodation
import io.realm.Realm
import io.realm.kotlin.where
import javax.inject.Inject

class AccommodationDao(private val realm: Realm) {

    fun getAllAccommodations(): List<Accommodation> {
        return realm.where<Accommodation>().findAll()
    }

    fun insertOfUpdateAccomm(accommodation: Accommodation, onSuccess: () -> Unit) {
        realm.use { realm ->
            realm.executeTransactionAsync(
                { it.insertOrUpdate(accommodation) },
                {
                    Log.d("RealmNotification", "Inserted Accommodation")
                    onSuccess()

                },
                { }
            )
        }
    }
    fun getAccommById(accommodationId: String): Accommodation? {
        return realm.use { realm ->
            realm.where(Accommodation::class.java)
                .equalTo("accommodationId", accommodationId)
                .findFirst()
        }
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
