package com.example.gotravel.helper

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmHelper private constructor(context: Context) {
    private val realm: Realm

    init {
        // Cấu hình Realm
        val config = RealmConfiguration.Builder()
            .name("travel.realm") // Tên file Realm
            .schemaVersion(1) // Phiên bản schema
            .build()

        Realm.init(context) // Khởi tạo Realm
        realm = Realm.getInstance(config) // Lấy instance Realm
    }

    fun getRealm(): Realm {
        return realm
    }

    companion object {
        @Volatile
        private var INSTANCE: RealmHelper? = null

        // Phương thức getInstance
        fun getInstance(context: Context): RealmHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RealmHelper(context).also { INSTANCE = it }
            }
        }
    }
    // Đóng Realm nếu chưa đóng
    fun closeRealm() {
        if (!realm.isClosed) {
            realm.close()
        }
    }

}
