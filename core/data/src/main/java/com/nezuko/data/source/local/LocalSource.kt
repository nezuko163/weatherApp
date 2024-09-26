package com.nezuko.data.source.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LocalSource @Inject constructor(
    private val context: Context
) {
//    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cities")
//
//
//    suspend fun saveString(key: String, value: String) {
//        val dataStoreKey = stringPreferencesKey(key)
//        context.dataStore.edit { preferences ->
//            preferences[dataStoreKey] = value
//        }
//    }
//
//    suspend fun readString(key: String): String? {
//        val dataStoreKey = stringPreferencesKey(key)
//        val preferences = context.dataStore.data.co
//        return preferences[dataStoreKey]
//    }

    private val CITIES = "cities"
    private val SEPARATOR = ";"
    private val TAG = "LocalSource"

    private val preferences = context.getSharedPreferences(CITIES, Context.MODE_PRIVATE)

    fun saveStringList(key: String = CITIES, list: List<String>) {
//        val preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val joinedString = list.joinToString(SEPARATOR)
        editor.putString(key, joinedString)
        editor.apply()
        Log.i(TAG, "saveStringList: $list")
    }

    fun addStringItem(key: String = CITIES, string: String) {
        saveStringList(key = key, list = getStringList() + string)
    }

    fun getStringList(key: String = CITIES): List<String> {
//        val preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val joinedString = preferences.getString(key, "") ?: ""

        Log.i(TAG, "getStringList: $joinedString")
        if (joinedString.isEmpty()) return listOf()

        return joinedString.split(SEPARATOR)
    }

    fun removeString(string: String) {
        val list = ArrayList(getStringList())

        if (!list.contains(string)) return

        list.remove(string)

        saveStringList(list = list)
    }

    fun clearStorage() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()

    }
}