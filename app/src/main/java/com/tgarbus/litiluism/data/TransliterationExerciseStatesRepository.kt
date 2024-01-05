package com.tgarbus.litiluism.data

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

typealias TransliterationExerciseStates = HashMap<String, TransliterationExerciseState>

val Context.transliterationStatesDataStore: DataStore<Preferences> by preferencesDataStore("transliterationExerciseStates")

class TransliterationExerciseStatesRepository(context: Context) {
    private val _states: TransliterationExerciseStates = TransliterationExerciseStates()
    private val _dataStore = context.transliterationStatesDataStore

    fun getExerciseState(exerciseId: String): TransliterationExerciseState {
        return _states.getOrDefault(exerciseId, TransliterationExerciseState())
    }

    fun updateState(exerciseId: String, state: TransliterationExerciseState) {
        _states[exerciseId] = state
    }

    fun updateDataStore() {
        // TODO
    }

    companion object {
        private var _instance: TransliterationExerciseStatesRepository? = null

        // TODO: Reconsider how this instance should be managed
        fun init(context: Context): Unit {
            _instance = TransliterationExerciseStatesRepository(context)
        }

        fun getInstance(): TransliterationExerciseStatesRepository {
            return _instance!!
        }
    }
}