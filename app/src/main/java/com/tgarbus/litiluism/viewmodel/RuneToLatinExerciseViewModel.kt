package com.tgarbus.litiluism.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.StaticContentRepository

class RuneToLatinExerciseViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val staticContentRepository = StaticContentRepository.getInstance()
    private val queue = ArrayDeque<String>()
    private val runeRow =
        staticContentRepository.runeRowsMap[savedStateHandle["runeRowId"]!!]!!

    fun isFinished(): Boolean {
        return queue.isEmpty()
    }
}