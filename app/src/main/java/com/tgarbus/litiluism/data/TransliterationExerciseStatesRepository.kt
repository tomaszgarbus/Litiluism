package com.tgarbus.litiluism.data

typealias TransliterationExerciseStates = HashMap<String, TransliterationExerciseState>

class TransliterationExerciseStatesRepository {
    private val _states: TransliterationExerciseStates = TransliterationExerciseStates()

    fun getExerciseState(exerciseId: String): TransliterationExerciseState {
        return _states.getOrDefault(exerciseId, TransliterationExerciseState())
    }

    fun updateState(exerciseId: String, state: TransliterationExerciseState) {
        _states[exerciseId] = state
    }

    companion object {
        private var _instance: TransliterationExerciseStatesRepository? = null

        fun getInstance(): TransliterationExerciseStatesRepository {
            if (_instance == null) {
                _instance = TransliterationExerciseStatesRepository()
            }
            return _instance!!
        }
    }
}