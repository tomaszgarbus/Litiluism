package com.tgarbus.litiluism.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgarbus.litiluism.data.Country
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.TransliterationExerciseStatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListOfExercisesViewModel: ViewModel() {
    private val exerciseStatesRepository = TransliterationExerciseStatesRepository.getInstance()
    private val staticContentRepository = StaticContentRepository.getInstance()

    fun transliterationExercises(): List<TransliterationExercise> {
        return staticContentRepository.transliterationExercises
    }

    fun exercisesByCountryCount(): HashMap<Country, Int> {
        return staticContentRepository.exercisesByCountryCount
    }
}