package com.accessed.data.util

import androidx.security.crypto.EncryptedSharedPreferences
import com.google.gson.Gson

inline fun <reified T> EncryptedSharedPreferences.putValue(key: String, value: T) {
    if (value == null) return
    edit().apply {
        when (value) {
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Int -> putInt(key, value)
            is String -> putString(key, value)
            else -> putString(key, Gson().toJson(value))
        }
    }.apply()
}

fun EncryptedSharedPreferences.setValueByMap(map: HashMap<String, Any?>) {
    if (map.isEmpty()) return
    map.forEach {
        putValue(it.key, it.value)
    }
}

inline fun <reified T> EncryptedSharedPreferences.getValue(
    key: String,
    defaultValue: T? = defaultValueByType<T>()
): T? {
    if (!contains(key)) return defaultValueByType<T>()

    val value = when (defaultValue) {
        is String -> getString(key, defaultValue) as T
        is Int -> getInt(key, defaultValue) as T
        is Boolean -> getBoolean(key, defaultValue) as T
        is Float -> getFloat(key, defaultValue) as T
        is Long -> getLong(key, defaultValue) as T
        else -> Gson().fromJson(getString(key, ""), T::class.java)
    }
    return value
}

inline fun <reified T> defaultValueByType(): T? {
    val value = when (T::class) {
        String::class -> "" as T
        Int::class -> 0 as T
        Boolean::class -> false as T
        Float::class -> 0F as T
        Long::class -> 0L as T
        else -> null
    }
    return value
}

fun EncryptedSharedPreferences.removeKey(key: String) {
    val keyExist: Boolean = contains(key)
    if (!keyExist) return
    edit().apply {
        remove(key)
    }.apply()
}

fun EncryptedSharedPreferences.removeKeys(keys: List<String>) {
    if (keys.isEmpty()) return
    keys.forEach {
        removeKey(it)
    }
}

fun EncryptedSharedPreferences.clearAllCache() {
    edit().clear().apply()
}
