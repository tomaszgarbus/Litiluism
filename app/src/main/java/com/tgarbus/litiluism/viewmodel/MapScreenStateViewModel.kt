package com.tgarbus.litiluism.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.Location
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExercise

class MapScreenStateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val staticContentRepository = StaticContentRepository.getInstance()
    val exercisesByLocation = HashMap<Location, List<TransliterationExercise>>()
    private val locationId: String? = savedStateHandle["locationId"]
    val locationFromSavedStateHandle: Location? =
        staticContentRepository.locations.find { l -> l.id == locationId }

    init {
        val result = HashMap<Location, ArrayList<TransliterationExercise>>()
        for (exercise in staticContentRepository.transliterationExercises) {
            if (exercise.location != null) {
                result[exercise.location] =
                    result.getOrDefault(
                        exercise.location,
                        arrayListOf<TransliterationExercise>()
                    )
                result[exercise.location]!!.add(exercise)
            }
        }
        for (kv in result.entries) {
            exercisesByLocation[kv.key] = kv.value
        }
    }

}