package com.tgarbus.litiluism

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class Country {
    SE,
    NO,
    FO,
    DA,
    IS,
    ANY,
}

data class Exercise(
    val id: String,
    // Object description
    val title: String,
    val description: String,
    val explanation: String,
    val country: Country,
    // Exercise
    val runes: String,
    val runeRow: RuneRow,
    // Images
    val imgResource: Int,
    val thumbnailResource: Int,
) {
    private var _solution: String = ""

    fun solution(): String {
        if (_solution.isEmpty()) {
            for (r in runes) {
                if (isSeparator(r)) {
                    _solution += r
                } else {
                    _solution += runeRow.mapping[r.toString()]!![0]
                }
            }
        }
        return _solution
    }
}

data class ExerciseState(
    val inputs: String = "",
    val position: Int = 0,
)

data class RuneRow(
    val id: String,
    val name: String,
    val mapping: HashMap<String, List<String>>,
)

typealias RuneRowsMap = HashMap<String, RuneRow>
typealias ExercisesMap = HashMap<String, Exercise>

class ExerciseStateViewModel() : ViewModel() {
    private val _state: MutableStateFlow<ExerciseState> = MutableStateFlow(ExerciseState())
    val state = _state.asStateFlow()

    fun update(c: Char) {
        _state.update { s -> s.copy(s.inputs + c, s.position + 1) }
    }
}

data class Content(val exercises: List<Exercise>, val runeRowsMap: RuneRowsMap) {
    private var _exercisesMap: ExercisesMap = ExercisesMap()

    fun exercisesMap(): ExercisesMap {
        if (_exercisesMap.isEmpty()) {
            _exercisesMap = exercises.toHashMap()
        }
        return _exercisesMap
    }
}