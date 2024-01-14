package com.tgarbus.litiluism.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.StaticContentRepository

class LessonViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val staticContentRepository = StaticContentRepository.getInstance()
    private val lessonNumber: Int = savedStateHandle["lessonNumber"]!!
    val lesson =
        staticContentRepository.lessons[lessonNumber]
}