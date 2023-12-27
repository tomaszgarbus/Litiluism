package com.tgarbus.litiluism.data

import com.tgarbus.litiluism.toHashMap

class StaticContentRepository private constructor(
    val transliterationExercises: List<TransliterationExercise>,
    val runeRowsMap: RuneRowsMap
) {
    val exercisesMap: ExercisesMap = transliterationExercises.toHashMap()

    companion object {
        private var instance_: StaticContentRepository? = null

        fun getInstance(): StaticContentRepository {
            if (instance_ == null) {
                val runeRowsMap = FromJson.loadCanonicalRuneRows()
                instance_ = StaticContentRepository(
                    runeRowsMap = runeRowsMap,
                    transliterationExercises =
                    FromJson.loadExercises(runeRowsMap)
                )
            }
            return instance_!!
        }
    }
}