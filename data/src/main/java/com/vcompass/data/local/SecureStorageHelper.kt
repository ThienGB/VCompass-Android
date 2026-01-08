package com.vcompass.data.local

class SecureStorageHelper(val storage: SecureStorage) {
    var accessToken: String?
        get() = storage.getString(ACCESS_TOKEN, "")
        set(value) {
            value?.let { storage.putString(ACCESS_TOKEN, it) }
        }

    var refreshToken: String?
        get() = storage.getString(REFRESH_TOKEN, "")
        set(value) {
            value?.let { storage.putString(REFRESH_TOKEN, it) }
        }

    var isOpenedApp: Boolean?
        get() = storage.getBoolean(FIRST_LAUNCH_APP)
        set(value) {
            value?.let { storage.putBoolean(FIRST_LAUNCH_APP, it) }
        }

    var isRememberMe: Boolean?
        get() = storage.getBoolean(REMEMBER_ME)
        set(value) {
            value?.let { storage.putBoolean(REMEMBER_ME, it) }
        }

    var userId: String?
        get() = storage.getString(USER_ID, "")
        set(value) {
            value?.let { storage.putString(USER_ID, it) }
        }

    fun clearDataAfterLogout() {
        storage.remove(listOf(ACCESS_TOKEN, USER_ID))
    }

    fun clearAll() {
        storage.clearAll()
    }

    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val FIRST_LAUNCH_APP = "FIRST_LAUNCH_APP"
        private const val REMEMBER_ME = "REMEMBER_ME"
        private const val USER_ID = "UserID"
    }
}