package com.tgarbus.litiluism.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map

typealias TransliterationExerciseStates = HashMap<String, TransliterationExerciseState>

val Context.transliterationStatesDataStore: DataStore<Preferences> by preferencesDataStore("transliterationExerciseStates")

class TransliterationExerciseStatesRepository(private val context: Context) {
    private val _states: TransliterationExerciseStates = TransliterationExerciseStates()
    private val _staticContentRepo = StaticContentRepository.getInstance()
    private var _loadedStates = false

    fun getExerciseState(exerciseId: String): TransliterationExerciseState {
        return _states.getOrDefault(exerciseId, TransliterationExerciseState())
    }

    fun updateState(exerciseId: String, state: TransliterationExerciseState) {
        _states[exerciseId] = state
    }

    private suspend fun loadDataFromDataStore() {
        val preferences =
            context.transliterationStatesDataStore.data.firstOrNull() ?: return
        for (exercise in _staticContentRepo.transliterationExercises) {
            val positionKey = intPreferencesKey("${exercise.id}#position")
            val inputsKey = stringPreferencesKey("${exercise.id}#inputs")
            val completeKey = booleanPreferencesKey("${exercise.id}#complete")
            val position = preferences[positionKey] ?: 0
            val inputs = preferences[inputsKey] ?: ""
            val complete = preferences[completeKey] ?: false
            Log.d("loaded", "$position $inputs $complete")
            updateState(
                exercise.id,
                TransliterationExerciseState(
                    position = position,
                    inputs = inputs,
                    complete = complete
                )
            )
        }
    }

    suspend fun maybeLoadDataFromDataStore() {
        Log.d("loaded", _loadedStates.toString())
        if (!_loadedStates) {
            Log.d("loaded", "not loaded states")
            loadDataFromDataStore()
            _loadedStates = true
            Log.d("loaded", "loaded states")
        }
    }

    suspend fun updateExerciseStateInDataStore(
        exerciseId: String,
        state: TransliterationExerciseState
    ) {
        context.transliterationStatesDataStore.edit { preferences ->
            val positionKey = intPreferencesKey("${exerciseId}#position")
            val inputsKey = stringPreferencesKey("${exerciseId}#inputs")
            val completeKey = booleanPreferencesKey("${exerciseId}#complete")
            preferences[positionKey] = state.position
            preferences[inputsKey] = state.inputs
            preferences[completeKey] = state.complete
        }
        Log.d("loaded", "updated state $state")
    }

    fun ready(): Boolean {
        return _loadedStates
    }

    companion object {
        // TODO: fix the memory leak
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