package com.tgarbus.litiluism.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.LessonStatesRepository
import kotlinx.coroutines.flow.Flow

class LearningViewModel: ViewModel() {
    fun getCompletedLessons(context: Context): Flow<Set<String>> {
        return LessonStatesRepository.getInstance(context).getCompletedLessonsAsFlow()
    }

}