package com.tgarbus.litiluism.data

import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.isSeparator
import com.tgarbus.litiluism.toHashMap
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

data class TransliterationExercise(
    val id: String,
    // Object description
    val title: String,
    val description: String,
    val explanation: String,
    val country: Country,
    val sources: List<String>,
    // Exercise
    val runes: String,
    val runeRow: RuneRow,
    // Images
    val imgResourceName: String,
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

data class TransliterationExerciseState(
    val inputs: String = "",
    val position: Int = 0,
    val complete: Boolean = false,
)

data class RuneRow(
    val id: String,
    val name: String,
    val mapping: HashMap<String, List<String>>,
)

typealias RuneRowsMap = HashMap<String, RuneRow>
typealias ExercisesMap = HashMap<String, TransliterationExercise>

data class Content(
    val transliterationExercises: List<TransliterationExercise>,
    val runeRowsMap: RuneRowsMap
) {
    private var _exercisesMap: ExercisesMap = ExercisesMap()

    fun exercisesMap(): ExercisesMap {
        if (_exercisesMap.isEmpty()) {
            _exercisesMap = transliterationExercises.toHashMap()
        }
        return _exercisesMap
    }
}