package com.tgarbus.litiluism.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgarbus.litiluism.data.InputMethod
import com.tgarbus.litiluism.data.SettingsRepository
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExerciseState
import com.tgarbus.litiluism.data.TransliterationExerciseStatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

class TransliterationExerciseViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val staticContentRepository = StaticContentRepository.getInstance()
    var transliterationExercise =
        staticContentRepository.exercisesMap[savedStateHandle["exerciseId"]!!]!!
    private val stateEditMutex = Mutex()

    fun getState(context: Context): Flow<TransliterationExerciseState> {
        return TransliterationExerciseStatesRepository.getInstance(context)
            .getExerciseStateAsFlow(transliterationExercise)
    }

    fun onUserInput(c: Char, position: Int, context: Context, countAsCorrect: Boolean) {
        viewModelScope.launch {
            stateEditMutex.lock()
            TransliterationExerciseStatesRepository.getInstance(context)
                .updateExerciseWithUserInput(transliterationExercise, c, position, countAsCorrect)
            stateEditMutex.unlock()
        }
    }

    fun getInputMethodAsFlow(context: Context): Flow<InputMethod> {
        return SettingsRepository(context).inputMethodAsFlow()
    }

    fun maybeInitState(context: Context) {
        viewModelScope.launch {
            stateEditMutex.lock()
            TransliterationExerciseStatesRepository.getInstance(context)
                .maybeInitStateForExercise(transliterationExercise)
            stateEditMutex.unlock()
        }
    }

    fun resetProgress(context: Context) {
        viewModelScope.launch {
            stateEditMutex.lock()
            TransliterationExerciseStatesRepository.getInstance(context).resetProgress(
                transliterationExercise
            )
            stateEditMutex.unlock()
        }
    }
}