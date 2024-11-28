package com.example.gotravel.data.local

import android.util.Log
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Conversation
import com.example.gotravel.data.model.Message
import io.realm.Realm
import io.realm.kotlin.where

class ConversationDao {
    private val realm: Realm = Realm.getDefaultInstance()

    fun insertOrUpdateConversation(conversation: Conversation, onSuccess: () -> Unit = {}) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                transactionRealm.insertOrUpdate(conversation)
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
    fun insertOrUpdateConversations(conversation: List<Conversation>, onSuccess: () -> Unit = {}) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                transactionRealm.where<Conversation>().findAll()?.deleteAllFromRealm()
                transactionRealm.insertOrUpdate(conversation)
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
    fun addMessage(conversationId: String, newMessage: Message, onSuccess: () -> Unit = {}) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                val conversation = transactionRealm.where(Conversation::class.java)
                    .equalTo("id_conversation", conversationId)
                    .findFirst()
                val isMessageExist = conversation?.messages?.any { it.id_message == newMessage.id_message } ?: false
                if (!isMessageExist) {
                    conversation?.messages?.add(newMessage)
                }
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

    fun getAllConversations(id: String): List<Conversation> {
        return realm.where<Conversation>().findAll()
    }

    fun getAllMessages(id: String): List<Conversation> {
        return realm.where<Conversation>().equalTo("id_conversation", id).findAll()
    }
}
