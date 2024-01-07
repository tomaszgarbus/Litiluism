package com.tgarbus.litiluism.data

import com.tgarbus.litiluism.isSeparator

enum class Country {
    ANY,
    SE,
    NO,
    FO,
    DA,
    IS,
}

enum class BaseRuneRow {
    ANY,
    OLDER_FUTHARK,
    YOUNGER_FUTHARK_SHORT_TWIG,
    YOUNGER_FUTHARK_LONG_BRANCH,
    ANGLO_SAXON,
    MEDIEVAL,
}

data class Location(
    val id: String,
    val lat: Double,
    val long: Double,
    val description: String
)

data class TransliterationExercise(
    val id: String,
    // Object description
    val title: String,
    val description: String,
    val explanation: String,
    val country: Country,
    val sources: List<String>,
    val location: Location?,
    // Exercise
    val runes: String,
    val runeRow: RuneRow,
    // Images
    val imgResourceName: String,
)

data class TransliterationExerciseState(
    val inputs: String = "",
    val position: Int = 0,
    val complete: Boolean = false,
)

data class RuneRow(
    val id: String,
    val name: String,
    val mapping: Map<Char, List<Char>>,
    val baseRuneRow: BaseRuneRow,
)

typealias RuneRowsMap = Map<String, RuneRow>
typealias RuneRowsMapImpl = HashMap<String, RuneRow>
typealias LocationsMap = Map<String, Location>
typealias ExercisesMap = Map<String, TransliterationExercise>
typealias ExercisesMapImpl = HashMap<String, TransliterationExercise>
typealias ThreeButtonOptions = List<Pair<List<Char>, Boolean>>