package com.example.gotravel.data.remote

import android.util.Log
import com.example.gotravel.data.local.ConversationDao
import com.example.gotravel.data.model.Conversation
import com.example.gotravel.data.model.Message
import com.example.gotravel.data.model.Room
import com.example.gotravel.data.model.User
import com.example.gotravel.helper.FirestoreHelper.CL_ACCOM
import com.example.gotravel.helper.FirestoreHelper.CL_CONVERSATION
import com.example.gotravel.helper.FirestoreHelper.CL_USER
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirestoreConverManager(private val scope: CoroutineScope) {
    private var conversationDao: ConversationDao = ConversationDao()
    private var listenerRegistration: ListenerRegistration? = null
    private val db = Firebase.firestore


    fun listenToConversation(conversations: List<Conversation>, onDataUpdated: () -> Unit) {
        conversations.forEach { conversation ->
            db.collection(CL_CONVERSATION)
                .document(conversation.id_conversation)
                .collection("messages")
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }
                    snapshot?.documentChanges?.forEach { change ->
                        when (change.type) {
                            DocumentChange.Type.ADDED, DocumentChange.Type.MODIFIED -> {
                                val newMessage  = change.document.toObject(Message::class.java)
                                newMessage.let {
                                    conversationDao.addMessage(conversation.id_conversation, newMessage, onDataUpdated)
                                }
                            }
                            DocumentChange.Type.REMOVED -> {}
                        }
                    }
                    onDataUpdated()
                }
        }
    }
    suspend fun fetchConversation(id: String, onComplete: () -> Unit) {
        val conversationsList = mutableListOf<Conversation>()
        try {
            val query1 = db.collection(CL_CONVERSATION)
                .whereEqualTo("idFirstUser", id)
                .get()
                .await()
            val query2 = db.collection(CL_CONVERSATION)
                .whereEqualTo("idSecondUser", id)
                .get()
                .await()
            val combinedResults = (query1.documents + query2.documents).distinctBy { it.id }
            for (document in combinedResults) {
                val conversation = document.toObject<Conversation>()?.apply {
                    id_conversation = document.id
                    idFirstUser = document.getString("idFirstUser").toString()
                    idSecondUser = document.getString("idSecondUser")
                    createdAt = document.getDate("createdAt")
                }
                val userFirstSnapshot = document.getString("idFirstUser")?.let {
                    db.collection(CL_USER)
                        .document(it)
                        .get()
                        .await()
                }
                val userFirst = userFirstSnapshot?.toObject<User>()
                    ?.apply {
                    userId = userFirstSnapshot.id
                }
                if (userFirst != null) {
                    conversation?.userFirst = userFirst
                }

                val userSecondSnapshot = document.getString("idSecondUser")?.let {
                    db.collection(CL_USER)
                        .document(it)
                        .get()
                        .await()
                }
                val userSecond = userSecondSnapshot?.toObject<User>()
                    ?.apply {
                    userId = userSecondSnapshot.id
                }
                if (userSecond != null) {
                    conversation?.userSecond = userSecond
                }
                conversationsList.add(conversation!!)
            }
            if (conversationsList.isNotEmpty()) {
                conversationDao.insertOrUpdateConversations(conversationsList, onComplete)
            }
        } catch (exception: Exception) {
            Log.w("FirestoreNotificationManager", "Error getting documents.", exception)
        }
    }

    suspend fun sendMessage (conversationId:String, message: Message) {
        println(message)
        try {
            db.collection(CL_CONVERSATION)
                .document(conversationId)
                .collection("messages")
                .add(message)
                .await()
        }catch (
            exception:Exception
        ) {
            Log.w("FirestoreNotificationManager", "Error getting documents.", exception)
        }
    }
    fun addConversationToFirestore(conversation: Conversation, onComplete: () -> Unit){
        val conversationQuery = db.collection(CL_CONVERSATION)
            .whereIn("idFirstUser", listOf(conversation.idFirstUser, conversation.idSecondUser))
            .whereIn("idSecondUser", listOf(conversation.idFirstUser, conversation.idSecondUser))
            .get()
        conversationQuery.addOnSuccessListener { querySnapshot ->
            if (querySnapshot.isEmpty) {
                val conversationMap = hashMapOf(
                    "id_conversation" to conversation.id_conversation,
                    "idFirstUser" to conversation.idFirstUser,
                    "idSecondUser" to conversation.idSecondUser,
                    "createdAt" to conversation.createdAt
                )
                db.collection(CL_CONVERSATION)
                    .document(conversation.id_conversation)
                    .set(conversationMap)
                    .addOnSuccessListener {
                        onComplete()
                    }
                    .addOnFailureListener { e ->
                        println("Error adding conversation: ${e.message}")
                    }
            } else {
                println("Conversation already exists!")
            }
        }.addOnFailureListener { e ->
            println("Error checking conversation: ${e.message}")
        }
    }
    fun stopListening() {
        listenerRegistration?.remove()
    }
}
