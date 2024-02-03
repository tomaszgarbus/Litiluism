package com.tgarbus.litiluism.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore("settings")

enum class InputMethod {
    VARIANTS,
    KEYBOARD
}

class SettingsRepository(private val context: Context) {
    private val inputMethodKey = stringPreferencesKey("inputMethod")

    fun inputMethodAsFlow(): Flow<InputMethod> {
        return context.settingsDataStore.data.map {
            preferences ->
            InputMethod.valueOf(preferences[inputMethodKey] ?: InputMethod.VARIANTS.name)
        }
    }

    suspend fun setInputMethod(inputMethod: InputMethod) {
        context.settingsDataStore.edit { preferences ->
            preferences[inputMethodKey] = inputMethod.toString()
        }
    }
}