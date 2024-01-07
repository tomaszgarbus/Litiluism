package com.tgarbus.litiluism.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.ThreeButtonOptions
import com.tgarbus.litiluism.generateOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RuneToLatinExerciseViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val staticContentRepository = StaticContentRepository.getInstance()
    private val runeRow =
        staticContentRepository.runeRowsMap[savedStateHandle["runeRowId"]!!]!!
    private val queue = ArrayDeque(runeRow.mapping.keys.shuffled())
    val optionsFlow = MutableStateFlow(getOptions())
    val questionsFlow = MutableStateFlow(getQuestion())
    val runeRowName = runeRow.name
    val finished = MutableStateFlow(false)

    val queueSize: Int
        get() = queue.size

    private fun getOptions(): ThreeButtonOptions {
        return generateOptions(runeRow, queue.first())
    }

    private fun getQuestion(): Char {
        return queue.first()
    }

    private fun emitNext() {
        viewModelScope.launch {
            questionsFlow.emit(getQuestion())
            optionsFlow.emit(getOptions())
        }
    }

    fun onCorrectClick() {
        queue.removeFirst()
        if (queue.isEmpty()) {
            viewModelScope.launch {
                finished.emit(true)
            }
        } else {
            emitNext()
        }
    }
}