package com.example.gotravel.data.local.dao

import io.realm.Realm

class UserAccountDao {
    private val realm: Realm = Realm.getDefaultInstance()

//    fun getUserByUID(userId: String): UserAccount? {
//        return realm.where(UserAccount::class.java)
//            .equalTo("userId", userId)
//            .findFirst()
//    }

//    fun insertOrUpdateAccomm(userAccount: UserAccount, onSuccess: () -> Unit = {}) {
//        realm.executeTransactionAsync(
//            { transactionRealm ->
//                transactionRealm.insertOrUpdate(userAccount)
//            },
//            {
//                Log.d("RealmNotification", "Inserted UserAccount")
//                onSuccess()
//            },
//            { error ->
//                Log.e("RealmNotification", "Error inserting UserAccount", error)
//            }
//        )
//    }
}