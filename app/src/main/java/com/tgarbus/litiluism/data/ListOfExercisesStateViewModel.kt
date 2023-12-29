package com.tgarbus.litiluism.data

import androidx.lifecycle.ViewModel

class ListOfExercisesStateViewModel: ViewModel() {
    val exerciseStatesRepository = TransliterationExerciseStatesRepository.getInstance()
    fun isComplete(exercise: TransliterationExercise): Boolean {
        return exerciseStatesRepository.getExerciseState(exercise.id).complete
    }
}