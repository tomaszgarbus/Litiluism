package com.tgarbus.litiluism.data

import android.content.Context
import com.tgarbus.litiluism.toHashMap

class StaticContentRepository private constructor(
    val transliterationExercises: List<TransliterationExercise>,
    val runeRowsMap: RuneRowsMap
) {
    val exercisesMap: ExercisesMap = transliterationExercises.toHashMap()
    val exercisesByCountryCount = buildExerciseByCountryCountMap(transliterationExercises)

    companion object {
        private var instance_: StaticContentRepository? = null

        // TODO: Try to remove context reference from here
        fun init(context: Context) {
            val runeRowsMap = FromJson.loadCanonicalRuneRows(context)
            instance_ = StaticContentRepository(
                runeRowsMap = runeRowsMap,
                transliterationExercises =
                FromJson.loadExercises(context, runeRowsMap)
            )
        }

        fun getInstance(): StaticContentRepository {
            return instance_!!
        }
    }
}