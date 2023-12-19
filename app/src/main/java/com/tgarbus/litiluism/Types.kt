package com.tgarbus.litiluism

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Exercise(
    val id: String,
    val runes: String,
    val title: String,
    val description: String,
    val solution: String,
    val imgResource: Int,
)

data class ExerciseState(
    var inputs: String,
    var position: Int,
)

class ExerciseStateViewModel() : ViewModel() {
    private val state_: MutableStateFlow<ExerciseState> = MutableStateFlow(
        ExerciseState(inputs = "tula:lat:r", position = 10))
    val state = state_.asStateFlow()

    fun update(c: Char) {
        state_.update { s -> s.copy(s.inputs + c, s.position + 1) }
    }
}