package com.tgarbus.litiluism.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.ExerciseScore

class AfterExerciseViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val correct: Int = savedStateHandle["correct"]!!
    private val total: Int = savedStateHandle["total"]!!
    val score = ExerciseScore(correct, total)
}