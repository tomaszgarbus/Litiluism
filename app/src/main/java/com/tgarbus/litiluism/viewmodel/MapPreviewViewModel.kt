package com.tgarbus.litiluism.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.Location
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.TransliterationExerciseStatesRepository

class MapPreviewViewModel() : ViewModel() {
    private val exerciseStatesRepository = TransliterationExerciseStatesRepository.getInstance()
    private val staticContentRepository = StaticContentRepository.getInstance()

    fun getLocations(): List<Location> {
        return staticContentRepository.locations
    }
}