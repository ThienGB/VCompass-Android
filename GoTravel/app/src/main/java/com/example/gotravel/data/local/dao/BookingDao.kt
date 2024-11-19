import android.util.Log
import com.example.gotravel.data.model.Booking
import com.example.gotravel.helper.FirestoreHelper.FL_BOOKINGID
import com.example.gotravel.helper.FirestoreHelper.FL_USERID
import io.realm.Realm
import io.realm.kotlin.where

class BookingDao() {
    private val realm: Realm = Realm.getDefaultInstance()
    fun getBookings(): List<Booking> {
        return realm.where<Booking>().findAll()
    }

    fun getBookingsByUser(userId: String): List<Booking> {
        return realm.where<Booking>()
            .equalTo(FL_USERID, userId)
            .findAll()
    }

    fun insertOrUpdateBooking(booking: Booking, onSuccess: () -> Unit = {}) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                transactionRealm.where<Booking>().findAll()?.deleteAllFromRealm()
                transactionRealm.insertOrUpdate(booking)
            },
            {
                Log.d("RealmNotification", "Inserted booking")
                onSuccess()
            },
            { error ->
                Log.e("RealmNotification", "Error inserting booking", error)
            }
        )
    }
    fun insertOrUpdateBooking(bookings: List<Booking>, onSuccess: () -> Unit = {}) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                transactionRealm.insertOrUpdate(bookings)
            },
            {
                Log.d("RealmNotification", "Inserted booking")
                onSuccess()
            },
            { error ->
                Log.e("RealmNotification", "Error inserting booking", error)
            }
        )
    }
    fun getBookingById(bookingId: String): Booking? {
        return realm.where(Booking::class.java)
            .equalTo(FL_BOOKINGID, bookingId)
            .findFirst()
    }

    fun deleteBooking(bookingId: String){
        realm.executeTransaction { transactionRealm ->
            val existingBooking = transactionRealm.where<Booking>()
                .equalTo(FL_BOOKINGID, bookingId)
                .findFirst()
            existingBooking?.deleteFromRealm()
        }
    }
    fun deleteAllBooking(){
        realm.executeTransactionAsync { transactionRealm ->
            transactionRealm.where<Booking>().findAll()?.deleteAllFromRealm()
        }
    }
}
