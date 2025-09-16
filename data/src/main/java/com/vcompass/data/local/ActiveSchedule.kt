import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File

class ActiveScheduleStore(
    context: Context
) {
    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
        File(context.filesDir, "datastore/schedule_preferences.preferences_pb")
    }

    private val keyActiveScheduleId = stringPreferencesKey("active_schedule_id")

    suspend fun setActiveScheduleId(scheduleId: String?) {
        dataStore.edit { preferences ->
            if (scheduleId != null) {
                preferences[keyActiveScheduleId] = scheduleId
            } else {
                preferences.remove(keyActiveScheduleId)
            }
        }
    }

    fun getActiveScheduleId(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[keyActiveScheduleId]
        }
    }
}
