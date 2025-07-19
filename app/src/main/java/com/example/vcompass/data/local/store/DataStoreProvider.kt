package com.example.vcompass.data.local.store

import android.content.Context
import androidx.datastore.core.DataStore
import java.io.File
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory

object DataStoreProvider {
    private const val DATASTORE_NAME = "schedule_preferences"
    private var dataStore: DataStore<Preferences>? = null

    fun getDataStore(context: Context): DataStore<Preferences> {
        return dataStore ?: synchronized(this) {
            dataStore ?: PreferenceDataStoreFactory.create {
                File(context.filesDir, "datastore/$DATASTORE_NAME.preferences_pb")
            }.also { dataStore = it }
        }
    }
}