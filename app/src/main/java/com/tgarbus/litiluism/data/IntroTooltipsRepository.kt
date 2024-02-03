package com.tgarbus.litiluism.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.introTooltipsDataStore: DataStore<Preferences> by preferencesDataStore("introTooltips")

class IntroTooltipsRepository(private val context: Context) {
    private val isCompletedKey = { l: String -> booleanPreferencesKey("${l}#completed") }

    // Returning Boolean? instead of Boolean, so that we can collect as state with default value
    // of null (to signal that this is uninitialized).
    // However this function should never return null, it's just a trick for type correctness.
    fun isTooltipCompletedAsFlow(tooltipId: String): Flow<Boolean?> {
        return context.introTooltipsDataStore.data.map { preferences ->
            preferences[isCompletedKey(tooltipId)] ?: false
        }
    }

    fun getCompletedTooltipsAsFlow(): Flow<Set<String>> {
        return context.introTooltipsDataStore.data.map { preferences ->
            val completedTooltips = HashSet<String>()
            for (key in preferences.asMap().keys) {
                completedTooltips.add(key.name.replace("#completed", ""))
            }
            completedTooltips
        }
    }

    suspend fun markIntroTooltipAsCompleted(tooltipId: String) {
        context.introTooltipsDataStore.edit { preferences ->
            preferences[isCompletedKey(tooltipId)] = true
        }
    }
}