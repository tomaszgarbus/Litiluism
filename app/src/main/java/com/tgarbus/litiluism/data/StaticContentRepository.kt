package com.tgarbus.litiluism.data

import android.content.Context
import com.tgarbus.litiluism.toHashMapById

class StaticContentRepository private constructor(
    val transliterationExercises: List<TransliterationExercise>,
    val locations: List<Location>,
    val runeRowsMap: RuneRowsMap
) {
    val exercisesMap: ExercisesMap = transliterationExercises.toHashMapById()
    val exercisesByCountryCount = buildExerciseByCountryCountMap(transliterationExercises)

    companion object {
        private var instance_: StaticContentRepository? = null

        // TODO: Try to remove context reference from here
        fun init(context: Context) {
            val runeRowsMap = FromJson.loadCanonicalRuneRows(context)
            val locations = FromJson.loadLocations(context)
            instance_ = StaticContentRepository(
                runeRowsMap = runeRowsMap,
                locations = locations,
                transliterationExercises =
                FromJson.loadExercises(context, runeRowsMap, locations)
            )
        }

        fun getInstance(): StaticContentRepository {
            return instance_!!
        }
    }
}