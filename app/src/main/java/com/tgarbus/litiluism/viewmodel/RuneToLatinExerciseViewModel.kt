package com.tgarbus.litiluism.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgarbus.litiluism.data.ExerciseScore
import com.tgarbus.litiluism.data.InputMethod
import com.tgarbus.litiluism.data.SettingsRepository
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.ThreeButtonOptions
import com.tgarbus.litiluism.generateRuneToLatinOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

// TODO: common interface for this and LatinToRune
class RuneToLatinExerciseViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val staticContentRepository = StaticContentRepository.getInstance()
    private val runeRow =
        staticContentRepository.runeRowsMap[savedStateHandle["runeRowId"]!!]!!
    private val queue = ArrayDeque(runeRow.mapping.keys.shuffled())
    val optionsFlow = MutableStateFlow(getOptions())
    val questionsFlow = MutableStateFlow(getQuestion())
    val runeRowName = runeRow.name
    val finished = MutableStateFlow(false)
    val score = mutableStateOf(ExerciseScore())

    val queueSize: Int
        get() = queue.size

    private fun getOptions(): ThreeButtonOptions {
        return generateRuneToLatinOptions(runeRow, queue.first())
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

    fun getInputMethodAsFlow(context: Context): Flow<InputMethod> {
        return SettingsRepository(context).inputMethodAsFlow()
    }
}