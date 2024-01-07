package com.tgarbus.litiluism.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExerciseState
import com.tgarbus.litiluism.data.TransliterationExerciseStatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TransliterationExerciseViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val staticContentRepository = StaticContentRepository.getInstance()
    var transliterationExercise =
        staticContentRepository.exercisesMap[savedStateHandle["exerciseId"]!!]!!

    fun getState(context: Context): Flow<TransliterationExerciseState> {
        return TransliterationExerciseStatesRepository.getInstance(context)
            .getExerciseStateAsFlow(transliterationExercise.id)
    }

    fun updateState(c: Char, context: Context) {
        viewModelScope.launch {
            TransliterationExerciseStatesRepository.getInstance(context)
                .updateExerciseWithUserInput(transliterationExercise, c)
        }
    }
}