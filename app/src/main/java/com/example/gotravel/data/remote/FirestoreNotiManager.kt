package com.example.gotravel.data.remote

import android.util.Log
import com.example.gotravel.data.local.dao.NotificationDao
import com.example.gotravel.data.model.Notification
import com.example.gotravel.helper.FirestoreHelper.CL_NOTIFICATION
import com.example.gotravel.helper.FirestoreHelper.FL_CONTENT
import com.example.gotravel.helper.FirestoreHelper.FL_CREATE_AT
import com.example.gotravel.helper.FirestoreHelper.FL_ID_RECEIVER
import com.example.gotravel.helper.FirestoreHelper.FL_ID_SENDER
import com.example.gotravel.helper.FirestoreHelper.FL_ISREAD
import com.example.gotravel.helper.FirestoreHelper.FL_NOTIFICATION_ID
import com.example.gotravel.helper.FirestoreHelper.FL_TITLE
import com.example.gotravel.helper.FirestoreHelper.FL_TYPE
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreNotiManager {
    private var notificationDao: NotificationDao = NotificationDao()
    private var listenerRegistration: ListenerRegistration? = null
    private val db = Firebase.firestore

    fun listenToNotifications(receiverId: String ,onDataUpdated: () -> Unit) {
        listenerRegistration = db.collection(CL_NOTIFICATION)
            .whereEqualTo("id_receiver", receiverId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                snapshot?.documentChanges?.forEach { change ->
                    when (change.type) {
                        DocumentChange.Type.ADDED, DocumentChange.Type.MODIFIED-> {
                            val notification = change.document.toObject<Notification>()
                            notification.isRead =change.document.data.get("isRead").toString()
                            notificationDao.insertOrUpdateNotification(notification, onDataUpdated)
                        }
                        DocumentChange.Type.REMOVED -> {}
                    }
                }
            }
    }

    suspend fun fetchNotifications(receiver:String, onComplete: () -> Unit) {
        try {
            val result = db.collection(CL_NOTIFICATION).whereEqualTo("id_receiver", receiver).get().await()
            val notificationList = mutableListOf<Notification>()
            for (document in result) {
                val notificationFirebase = document.toObject<Notification>()
                notificationFirebase.isRead =document.data.get("isRead").toString()
                notificationList.add(notificationFirebase)
            }
            notificationDao.insertOrUpdateNotifications(notificationList, onComplete)
        } catch (exception: Exception) {
            Log.w("FirestoreNotificationManager", "Error getting documents.", exception)
        }
    }
    fun convertNotificationToMap(notification: Notification): Map<String, Any?> {
        val notificationMap = mutableMapOf<String, Any?>()

        notificationMap["id_notification"] = notification.id_notification
        notificationMap["id_receiver"] = notification.id_receiver
        notificationMap["id_sender"] = notification.id_sender
        notificationMap["content"] = notification.content
        notificationMap["create_at"] = notification.create_at?.time // Convert Date to timestamp
        notificationMap["buttonText"] = notification.buttonText
        notificationMap["isRead"] = notification.isRead

        return notificationMap
    }
    fun stopListening() {
        listenerRegistration?.remove()
    }

    fun deleteAllNotifications(id: String) {
        try {
            db.collection(CL_NOTIFICATION)
                .whereEqualTo("id_receiver", id) // Lọc theo id_receiver
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        db.collection(CL_NOTIFICATION)
                            .document(document.id)
                            .delete()
                            .addOnSuccessListener {
                                Log.d("DeleteNotification", "Notification ${document.id} successfully deleted.")
                            }
                            .addOnFailureListener { exception ->
                                Log.e("DeleteNotification", "Error deleting notification ${document.id}", exception)
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("DeleteNotification", "Error fetching notifications for id_receiver $id", exception)
                }

        }
        catch (e: Exception) {
            Log.w("FirestoreNotificationManager", "Error delete documents.", e)
        }
    }

    fun changeStatus(id: String) {
        try {
            val query = db.collection(CL_NOTIFICATION).whereEqualTo("id_notification", id)
            query.get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        for (document in querySnapshot.documents) {
                            val documentRef = document.reference
                            documentRef.update("isRead", "true")
                                .addOnSuccessListener {
                                    Log.d("FirestoreNotificationManager", "Document with id_notification: $id updated successfully.")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("FirestoreNotificationManager", "Error updating document with id_notification: $id", e)
                                }
                        }
                    } else {
                        Log.w("FirestoreNotificationManager", "No document found with id_notification = $id")
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("FirestoreNotificationManager", "Error finding document by id_notification", e)
                }
        } catch (e: Exception) {
            Log.w("FirestoreNotificationManager", "Error during Firestore operation.", e)
        }
    }

    fun addNotification(notification: Notification) {
        val data: MutableMap<String, Any> = HashMap()
        data[FL_NOTIFICATION_ID] = notification.id_notification.toString()
        data[FL_TYPE] = notification.type.toString()
        data[FL_TITLE] = notification.title.toString()
        data[FL_CONTENT] = notification.content.toString()
        data[FL_CREATE_AT] = notification.create_at
        data[FL_ID_SENDER] = notification.id_sender.toString()
        data[FL_ID_RECEIVER] = notification.id_receiver.toString()
        data[FL_ISREAD] = "false"
        db.collection(CL_NOTIFICATION).document(notification.id_notification).set(data)
    }
    fun removeListener() {
        listenerRegistration?.remove()
    }
}
