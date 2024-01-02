package com.tgarbus.litiluism.viewmodel

import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.TransliterationExerciseStatesRepository

class ListOfExercisesViewModel: ViewModel() {
    private val exerciseStatesRepository = TransliterationExerciseStatesRepository.getInstance()
    private val staticContentRepository = StaticContentRepository.getInstance()

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