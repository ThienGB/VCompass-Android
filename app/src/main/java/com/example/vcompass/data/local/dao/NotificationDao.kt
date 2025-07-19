package com.example.vcompass.data.local.dao

import android.util.Log
import com.example.vcompass.data.model.Notification
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationDao {
    private val realm: Realm = Realm.getDefaultInstance()

    fun getAllNotifications(): List<Notification> {
        return realm.where<Notification>().findAll()
    }
    fun insertOrUpdateNotification(notification: Notification, onSuccess: () -> Unit = {}) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                Log.d("RealmNotification", "Starting transaction for notification ID: ${notification.isRead}")
                transactionRealm.insertOrUpdate(notification)
            },
            {
                Log.d("RealmNotification", "Inserted Notification")
                onSuccess()
            },
            { error ->
                Log.e("RealmNotification", "Error inserting Notification", error)
            }
        )
    }
    fun insertOrUpdateNotifications(notification: List<Notification>, onSuccess: () -> Unit = {}) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                transactionRealm.where<Notification>().findAll()?.deleteAllFromRealm()
                transactionRealm.insertOrUpdate(notification)
            },
            {
                Log.d("RealmNotification", "Inserted Notification")
                onSuccess()
            },
            { error ->
                Log.e("RealmNotification", "Error inserting Notification", error)
            }
        )
    }
    suspend fun deleteAllNotificationsRealm() {
        // Use Realm within the same thread
        withContext(Dispatchers.IO) {
            val realm = Realm.getDefaultInstance()
            try {
                realm.executeTransaction { transactionRealm ->
                    transactionRealm.deleteAll()
                }
                Log.d("NotificationDao", "All notifications deleted.")
            } finally {
                realm.close() // Always close Realm instances to avoid memory leaks
            }
        }
    }
}
