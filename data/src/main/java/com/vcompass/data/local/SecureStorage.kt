package com.vcompass.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.vcompass.data.util.clearAllCache
import com.vcompass.data.util.getValue
import com.vcompass.data.util.putValue
import com.vcompass.data.util.removeKey
import com.vcompass.data.util.removeKeys

class SecureStorage(context: Context) {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val prefs = EncryptedSharedPreferences.create(
        "secure_accessed",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    ) as EncryptedSharedPreferences

    fun putString(key: String, value: String) {
        prefs.putValue(key, value)
    }

    fun getString(key: String, default: String? = null): String? {
        return prefs.getValue(key, default)
    }

    fun putBoolean(key: String, value: Boolean) {
        prefs.putValue(key, value)
    }

    fun getBoolean(key: String): Boolean {
        return prefs.getValue(key, false) ?: false
    }

    fun remove(key: String) {
        prefs.removeKey(key)
    }

    fun remove(keys: List<String>) {
        prefs.removeKeys(keys)
    }

    fun clearAll() {
        prefs.clearAllCache()
    }
}