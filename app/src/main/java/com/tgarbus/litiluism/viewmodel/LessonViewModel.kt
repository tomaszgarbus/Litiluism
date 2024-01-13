package com.tgarbus.litiluism.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.StaticContentRepository

class LessonViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val staticContentRepository = StaticContentRepository.getInstance()
    private val lessonNumberStr: String = savedStateHandle["lessonNumber"]!!
    private val lessonNumber: Int = lessonNumberStr.toInt()
    val lesson =
        staticContentRepository.lessons[lessonNumber]
}