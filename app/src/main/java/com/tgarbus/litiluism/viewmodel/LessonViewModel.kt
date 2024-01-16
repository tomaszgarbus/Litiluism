package com.tgarbus.litiluism.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgarbus.litiluism.data.LessonStatesRepository
import com.tgarbus.litiluism.data.StaticContentRepository
import kotlinx.coroutines.launch

class LessonViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val staticContentRepository = StaticContentRepository.getInstance()
    private val lessonNumber: Int = savedStateHandle["lessonNumber"]!!
    val lesson =
        staticContentRepository.lessons[lessonNumber]

    fun markAsComplete(context: Context) {
        viewModelScope.launch {
            LessonStatesRepository.getInstance(context).markLessonAsCompleted(lesson.id)
        }
    }
}