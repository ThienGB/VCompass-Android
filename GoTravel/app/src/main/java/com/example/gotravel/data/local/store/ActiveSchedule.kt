import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object ActiveScheduleStore {
    private const val KEY_ACTIVE_SCHEDULE_ID = "active_schedule_id"

    suspend fun setActiveScheduleId(dataStore: DataStore<Preferences>, scheduleId: String?) {
        dataStore.edit { preferences ->
            if (scheduleId != null) {
                preferences[stringPreferencesKey(KEY_ACTIVE_SCHEDULE_ID)] = scheduleId
            } else {
                preferences.remove(stringPreferencesKey(KEY_ACTIVE_SCHEDULE_ID))
            }
        }
    }

    fun getActiveScheduleId(dataStore: DataStore<Preferences>): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(KEY_ACTIVE_SCHEDULE_ID)]
        }
    }
}