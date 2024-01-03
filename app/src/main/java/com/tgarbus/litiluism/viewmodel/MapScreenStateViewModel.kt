package com.tgarbus.litiluism.viewmodel

import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.Location
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.toHashMapById

class MapScreenStateViewModel : ViewModel() {
    private val staticContentRepository = StaticContentRepository.getInstance()
    val exercisesByLocation = HashMap<Location, List<TransliterationExercise>>()

    init {
        val result = HashMap<Location, ArrayList<TransliterationExercise>> ()
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