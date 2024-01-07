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
import com.tgarbus.litiluism.isSeparator
import kotlinx.coroutines.flow.Flow
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
    fun getExerciseStateAsFlow(exerciseId: String): Flow<TransliterationExerciseState> {
        return context.transliterationStatesDataStore.data.map { preferences ->
            val positionKey = intPreferencesKey("${exerciseId}#position")
            val inputsKey = stringPreferencesKey("${exerciseId}#inputs")
            val completeKey = booleanPreferencesKey("${exerciseId}#complete")
            val position = preferences[positionKey] ?: 0
            val inputs = preferences[inputsKey] ?: ""
            val complete = preferences[completeKey] ?: false
            TransliterationExerciseState(
                position = position,
                inputs = inputs,
                complete = complete
            )
        }
    }

    private suspend fun isExerciseComplete(exercise: TransliterationExercise): Boolean {
        val exerciseStateFlow = getExerciseStateAsFlow(exercise.id)
        val state = exerciseStateFlow.firstOrNull() ?: return false
        return state.complete
    }

    private suspend fun getExercisePosition(exercise: TransliterationExercise): Int {
        val exerciseStateFlow = getExerciseStateAsFlow(exercise.id)
        val state = exerciseStateFlow.firstOrNull() ?: return 0
        return state.position
    }

    suspend fun appendInputToExerciseState(exercise: TransliterationExercise, c: Char) {
        context.transliterationStatesDataStore.edit { preferences ->
            val positionKey = intPreferencesKey("${exercise.id}#position")
            val inputsKey = stringPreferencesKey("${exercise.id}#inputs")
            val completeKey = booleanPreferencesKey("${exercise.id}#complete")
            val position = preferences[positionKey] ?: 0
            val inputs = preferences[inputsKey] ?: ""
            preferences[positionKey] = position + 1
            preferences[inputsKey] = inputs + c
            preferences[completeKey] = (position + 1 == exercise.runes.length)
        }
    }

    suspend fun updateExerciseWithUserInput(exercise: TransliterationExercise, c: Char) {
        appendInputToExerciseState(exercise, c)
        var position = getExercisePosition(exercise)
        while (!isExerciseComplete(exercise) && isSeparator(exercise.runes[position])) {
            appendInputToExerciseState(exercise, exercise.runes[position])
            position = getExercisePosition(exercise)
        }
    }

    companion object {
        fun getInstance(context: Context): TransliterationExerciseStatesRepository {
            return TransliterationExerciseStatesRepository(context)
        }
    }
}