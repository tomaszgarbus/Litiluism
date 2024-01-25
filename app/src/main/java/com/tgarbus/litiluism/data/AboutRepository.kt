package com.tgarbus.litiluism.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.aboutDataStore: DataStore<Preferences> by preferencesDataStore("about")

class AboutRepository {
    private val key = booleanPreferencesKey("completed")

    fun getCompletedAsFlow(context: Context): Flow<Boolean> {
        return context.transliterationStatesDataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    suspend fun markCompleted(context: Context) {
        context.transliterationStatesDataStore.edit { preferences ->
            preferences[key] = true
        }
    }
}