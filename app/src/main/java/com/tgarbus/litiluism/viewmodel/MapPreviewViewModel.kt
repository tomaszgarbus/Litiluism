package com.tgarbus.litiluism.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.TransliterationExerciseStatesRepository

class MapPreviewViewModel() : ViewModel() {
    private val exerciseStatesRepository = TransliterationExerciseStatesRepository.getInstance()
    private val staticContentRepository = StaticContentRepository.getInstance()

    fun getExercisesWithLocations(): List<TransliterationExercise> {
        val result = arrayListOf<TransliterationExercise>()
        for (exercise in staticContentRepository.transliterationExercises) {
            if (exercise.location != null) {
                result.add(exercise)
            }
        }
        Log.d("debug", result.toString())
        return result.toList()
    }
}