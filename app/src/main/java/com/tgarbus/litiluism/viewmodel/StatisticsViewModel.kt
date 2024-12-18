package com.tgarbus.litiluism.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.Country
import com.tgarbus.litiluism.data.LessonStatesRepository
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExerciseStatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlin.math.roundToInt

class StatisticsViewModel(): ViewModel() {
    private fun computePercentage(completed: Int, total: Int): Int {
        return (100f * completed.toFloat() / total.toFloat()).roundToInt()
    }

    private fun listAllExerciseIds(): List<String> {
        return StaticContentRepository.getInstance().exercisesMap.keys.toList()
    }

    fun getLessonsCompletedPercentage(context: Context): Flow<Int> {
        return LessonStatesRepository.getInstance(context).getCompletedLessonsAsFlow().map {
            completedLessonsSet ->
            val numCompleted = completedLessonsSet.size
            val allLessons = StaticContentRepository.getInstance().lessons.size
            computePercentage(numCompleted, allLessons)
        }
    }

    private fun getExercisesCompletedPercentageByIds(context: Context, exerciseIds: List<String>): Flow<Int> {
        return TransliterationExerciseStatesRepository.getInstance(context).getExerciseStatesAsFlow(exerciseIds).map {
                states ->
            val numCompleted = states.filterValues { v -> v.complete }.size
            val all = states.size
            computePercentage(numCompleted, all)
        }
    }

    fun getExercisesCompletedPercentage(context: Context): Flow<Int> {
        val exerciseIds = listAllExerciseIds()
        return getExercisesCompletedPercentageByIds(context, exerciseIds)
    }

    fun getExercisesCompletedPercentageByCountry(context: Context, country: Country): Flow<Int> {
        val repo = StaticContentRepository.getInstance()
        val exerciseIds = listAllExerciseIds().filter {
            exerciseId -> repo.exercisesMap[exerciseId]!!.country == country
        }
        return getExercisesCompletedPercentageByIds(context, exerciseIds)
    }
}