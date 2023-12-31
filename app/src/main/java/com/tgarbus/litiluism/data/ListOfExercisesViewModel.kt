package com.tgarbus.litiluism.data

import androidx.lifecycle.ViewModel

class ListOfExercisesViewModel: ViewModel() {
    val exerciseStatesRepository = TransliterationExerciseStatesRepository.getInstance()
    val staticContentRepository = StaticContentRepository.getInstance()

    fun isComplete(exercise: TransliterationExercise): Boolean {
        return exerciseStatesRepository.getExerciseState(exercise.id).complete
    }

    fun listRuneRowsNames(): List<String> {
        val result = mutableSetOf<String>()
        for (exercise in staticContentRepository.transliterationExercises) {
            result.add(exercise.runeRow.name)
        }
        return result.toList()
    }
}