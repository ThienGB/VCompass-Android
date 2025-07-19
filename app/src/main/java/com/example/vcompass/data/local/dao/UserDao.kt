package com.example.vcompass.data.local.dao

import android.util.Log
import com.example.vcompass.data.model.User
import io.realm.Realm
import io.realm.kotlin.where

class UserDao {
    private val realm: Realm = Realm.getDefaultInstance()

    fun getAllUsers(): List<User> {
        return realm.where<User>().findAll()
    }
    fun insertOrUpdateUser(users: List<User>, onSuccess: () -> Unit = {}) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                transactionRealm.where<User>().findAll()?.deleteAllFromRealm()
                transactionRealm.insertOrUpdate(users)
            },
            {
                Log.d("RealmNotification", "Inserted/Updated users")
                onSuccess()
            },
            { error ->
                Log.e("RealmNotification", "Error inserting/updating users", error)
            }
        )
    }
    fun insertOrUpdateUser(user: User, onSuccess: () -> Unit = {}) {
        realm.executeTransactionAsync(
            { transactionRealm ->
                transactionRealm.insertOrUpdate(user)
            },
            {
                Log.d("RealmNotification", "Inserted User")
                onSuccess()
            },
            { error ->
                Log.e("RealmNotification", "Error inserting User", error)
            }
        )
    }
}