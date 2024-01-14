package com.tgarbus.litiluism.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgarbus.litiluism.data.ExerciseScore
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.ThreeButtonOptions
import com.tgarbus.litiluism.generateOptions
import com.tgarbus.litiluism.generateRuneToLatinOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

// TODO: common interface for this and RuneToLatin
class LatinToRuneExerciseViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val staticContentRepository = StaticContentRepository.getInstance()
    private val runeRow =
        staticContentRepository.runeRowsMap[savedStateHandle["runeRowId"]!!]!!
    private val reverseMapping = buildReverseMapping()
    private val queue = ArrayDeque(reverseMapping.keys.shuffled())
    val queueSize: Int
        get() = queue.size
    val optionsFlow = MutableStateFlow(getOptions())
    val questionsFlow = MutableStateFlow(getQuestion())
    val runeRowName = runeRow.name
    val finished = MutableStateFlow(false)
    val score = mutableStateOf(ExerciseScore())

    private fun buildReverseMapping(): Map<Char, List<Char>> {
        // TODO: should I worry about two runes transliterated to the same latin symbol?
        return runeRow.mapping.entries.associateBy({ it.value[0] }, { listOf(it.key) })
    }

    private fun getOptions(): ThreeButtonOptions {
        return generateOptions(reverseMapping, queue.first())
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

    fun onCorrectClick(countAsCorrect: Boolean) {
        score.value.recordAnswer(countAsCorrect)
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