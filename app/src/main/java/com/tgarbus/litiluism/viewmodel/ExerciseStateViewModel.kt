package com.tgarbus.litiluism.viewmodel

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.TransliterationExerciseState
import com.tgarbus.litiluism.data.TransliterationExerciseStatesRepository
import com.tgarbus.litiluism.isSeparator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ExerciseStateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

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
            .updateState(transliterationExercise!!.id, state.value)
    }

    fun isComplete(): Boolean {
        return state.value.position == transliterationExercise!!.runes.length
    }
}