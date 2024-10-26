package com.example.gotravel

import com.example.gotravel.helper.RealmHelper
import android.app.Application
import com.google.firebase.FirebaseApp
import io.realm.Realm

class MainApplication : Application() {
    lateinit var realmHelper: RealmHelper

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Realm.init(this)
        realmHelper = RealmHelper.getInstance(this)
    }
}
