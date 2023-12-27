package com.tgarbus.litiluism.data

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.VIEW_MODEL_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.tgarbus.litiluism.isSeparator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ExerciseStateViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    var transliterationExercise: TransliterationExercise? = null
    private val _state: MutableStateFlow<TransliterationExerciseState> =
        MutableStateFlow(TransliterationExerciseState())
    val state = _state.asStateFlow()

    init {
        val exerciseId: String = savedStateHandle["exerciseId"]!!
        transliterationExercise = StaticContentRepository.getInstance().exercisesMap[exerciseId]!!
        _state.value =
            TransliterationExerciseStatesRepository.getInstance().getExerciseState(exerciseId)
    }

    private fun updateStateByOnePosition(c: Char) {
        _state.update { s -> s.copy(s.inputs + c, s.position + 1) }
        _state.update { s -> s.copy(complete = isComplete()) }
    }

    fun updateState(c: Char) {
        updateStateByOnePosition(c)
        while (!isComplete() && isSeparator(transliterationExercise!!.runes[state.value.position])) {
            updateStateByOnePosition(transliterationExercise!!.runes[state.value.position])
        }
        TransliterationExerciseStatesRepository.getInstance()
            .updateState(transliterationExercise!!.id, _state.value)
    }

    fun isComplete(): Boolean {
        return state.value.position == transliterationExercise!!.runes.length
    }
}