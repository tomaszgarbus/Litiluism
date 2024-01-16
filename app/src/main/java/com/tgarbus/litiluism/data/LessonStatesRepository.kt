package com.tgarbus.litiluism.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.lessonStatesDataStore: DataStore<Preferences> by preferencesDataStore("lessonStates")
class LessonStatesRepository(private val context: Context) {
    private val isCompletedKey = { l: String -> booleanPreferencesKey("${l}#completed") }
    private val staticContentRepo = StaticContentRepository.getInstance()

    fun getCompletedLessonsAsFlow(): Flow<Set<String>> {
        return context.lessonStatesDataStore.data.map { preferences ->
            val completedLessons = HashSet<String>()
            for (lesson in staticContentRepo.lessons) {
                val isCompleted = preferences[isCompletedKey(lesson.id)] ?: false
                if (isCompleted) {
                    completedLessons.add(lesson.id)
                }
            }
            completedLessons
        }
    }

    suspend fun markLessonAsCompleted(lessonId: String) {
        context.lessonStatesDataStore.edit { preferences ->
            preferences[isCompletedKey(lessonId)] = true
        }
    }

    companion object {
        fun getInstance(context: Context): LessonStatesRepository {
            return LessonStatesRepository(context)
        }
    }
}