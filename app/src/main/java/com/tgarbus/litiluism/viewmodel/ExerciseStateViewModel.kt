package com.tgarbus.litiluism.viewmodel

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.TransliterationExerciseState
import com.tgarbus.litiluism.data.TransliterationExerciseStatesRepository
import com.tgarbus.litiluism.isSeparator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseStateViewModel() : ViewModel() {

    var transliterationExercise: TransliterationExercise? = null
    private val _state: MutableStateFlow<TransliterationExerciseState> =
        MutableStateFlow(TransliterationExerciseState())
    private val exerciseStatesRepository = TransliterationExerciseStatesRepository.getInstance()
    val state = _state.asStateFlow()

    private fun updateStateByOnePosition(c: Char) {
        _state.update { s -> s.copy(inputs = s.inputs + c, position = s.position + 1) }
        _state.update { s -> s.copy(complete = isComplete()) }
    }

    fun updateState(c: Char) {
        updateStateByOnePosition(c)
        while (!isComplete() && isSeparator(transliterationExercise!!.runes[state.value.position])) {
            updateStateByOnePosition(transliterationExercise!!.runes[state.value.position])
        }
        exerciseStatesRepository.updateState(transliterationExercise!!.id, state.value)
        viewModelScope.launch {
            exerciseStatesRepository.updateExerciseStateInDataStore(
                transliterationExercise!!.id,
                state.value
            )
        }
    }

    fun isComplete(): Boolean {
        return state.value.position == transliterationExercise!!.runes.length
    }
}