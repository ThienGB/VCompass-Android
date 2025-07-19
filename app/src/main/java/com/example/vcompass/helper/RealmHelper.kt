package com.example.vcompass.helper

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmHelper private constructor(context: Context) {
    private val realm: Realm

    init {
        val config = RealmConfiguration.Builder()
            .name("travel.realm")
            .schemaVersion(1)
            .build()
        Realm.init(context)
        realm = Realm.getInstance(config)
    }
    fun getRealm(): Realm {
        return realm
    }
    companion object {
        @Volatile
        private var INSTANCE: RealmHelper? = null
        fun getInstance(context: Context): RealmHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RealmHelper(context).also { INSTANCE = it }
            }
        }
    }
    fun closeRealm() {
        if (!realm.isClosed) {
            realm.close()
        }
    }

}
