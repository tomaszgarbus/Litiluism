package com.tgarbus.litiluism.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tgarbus.litiluism.isSeparator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

val Context.transliterationStatesDataStore: DataStore<Preferences> by preferencesDataStore("transliterationExerciseStates")

class TransliterationExerciseStatesRepository(private val context: Context) {
    private val positionKey = { e: String -> intPreferencesKey("${e}#position") }
    private val inputsKey = { e: String -> stringPreferencesKey("${e}#inputs") }
    private val completeKey = { e: String -> booleanPreferencesKey("${e}#complete") }
    private val correctAnswersKey = { e: String -> intPreferencesKey("${e}#correctAnswers") }
    private val totalAnswersKey = { e: String -> intPreferencesKey("${e}#totalAnswers") }

    private fun preferencesToState(
        preferences: Preferences,
        exerciseId: String,
        leadingSeparators: String = ""
    ): TransliterationExerciseState {
        val position = preferences[positionKey(exerciseId)] ?: leadingSeparators.length
        val inputs = preferences[inputsKey(exerciseId)] ?: leadingSeparators
        val complete = preferences[completeKey(exerciseId)] ?: false
        val correctAnswers = preferences[correctAnswersKey(exerciseId)] ?: 0
        val totalAnswers = preferences[totalAnswersKey(exerciseId)] ?: 0
        return TransliterationExerciseState(
            position = position,
            inputs = inputs,
            complete = complete,
            score = ExerciseScore(correctAnswers, totalAnswers)
        )
    }

    suspend fun maybeInitStateForExercise(exercise: TransliterationExercise) {
        context.transliterationStatesDataStore.edit { preferences ->
            val position = preferences[positionKey(exercise.id)]
            if (position == null) {
                preferences[positionKey(exercise.id)] = exercise.leadingSeparators().length
                preferences[inputsKey(exercise.id)] = exercise.leadingSeparators()
                preferences[completeKey(exercise.id)] = false
                preferences[correctAnswersKey(exercise.id)] = 0
                preferences[totalAnswersKey(exercise.id)] = 0
            }
        }
    }

    fun getExerciseStateAsFlow(exercise: TransliterationExercise): Flow<TransliterationExerciseState> {
        return context.transliterationStatesDataStore.data.map { preferences ->
            preferencesToState(preferences, exercise.id, exercise.leadingSeparators())
        }
    }

    fun getExerciseStatesAsFlow(exerciseIds: List<String>): Flow<Map<String, TransliterationExerciseState>> {
        return context.transliterationStatesDataStore.data.map { preferences ->
            val result = HashMap<String, TransliterationExerciseState>()
            for (exerciseId in exerciseIds) {
                result[exerciseId] = preferencesToState(preferences, exerciseId)
            }
            result
        }
    }

    private suspend fun isExerciseComplete(exercise: TransliterationExercise): Boolean {
        val exerciseStateFlow = getExerciseStateAsFlow(exercise)
        val state = exerciseStateFlow.firstOrNull() ?: return false
        return state.complete
    }

    private suspend fun getExercisePosition(exercise: TransliterationExercise): Int {
        val exerciseStateFlow = getExerciseStateAsFlow(exercise)
        val state = exerciseStateFlow.firstOrNull() ?: return 0
        return state.position
    }

    private suspend fun appendInputsToExerciseState(
        exercise: TransliterationExercise,
        inputPosition: Int,
        cs: List<Char>,
        scoreUpdate: (ExerciseScore) -> Unit
    ) {
        context.transliterationStatesDataStore.edit { preferences ->
            val state = preferencesToState(preferences, exercise.id)
            if (state.position != inputPosition) {
                return@edit
            }
            scoreUpdate(state.score)
            preferences[positionKey(exercise.id)] = state.position + cs.size
            preferences[inputsKey(exercise.id)] = state.inputs + cs.joinToString("")
            preferences[completeKey(exercise.id)] = (state.position + cs.size == exercise.runes.length)
            preferences[correctAnswersKey(exercise.id)] = state.score.correct
            preferences[totalAnswersKey(exercise.id)] = state.score.total
        }
    }

    suspend fun updateExerciseWithUserInput(
        exercise: TransliterationExercise,
        c: Char,
        inputPosition: Int,  // Used to verify that we're inputting at right position.
        countAsCorrect: Boolean
    ) {
        var position = inputPosition
        val inputsToAppend = arrayListOf(c)
        position++
        while (position < exercise.runes.length && isSeparator(exercise.runes[position])) {
            inputsToAppend.add(exercise.runes[position])
            position++
        }
        appendInputsToExerciseState(exercise, inputPosition, inputsToAppend) { score ->
            score.recordAnswer(
                countAsCorrect
            )
        }
    }

    suspend fun resetProgress(exercise: TransliterationExercise) {
        context.transliterationStatesDataStore.edit { preferences ->
            val leadingSeparators = exercise.leadingSeparators()
            preferences[positionKey(exercise.id)] = leadingSeparators.length
            preferences[inputsKey(exercise.id)] = leadingSeparators
            preferences[completeKey(exercise.id)] = false
            preferences[correctAnswersKey(exercise.id)] = 0
            preferences[totalAnswersKey(exercise.id)] = 0
        }
    }

    companion object {
        fun getInstance(context: Context): TransliterationExerciseStatesRepository {
            return TransliterationExerciseStatesRepository(context)
        }
    }
}