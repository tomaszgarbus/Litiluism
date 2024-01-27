package com.tgarbus.litiluism.data

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

data class ExerciseScore(var correct: Int = 0, var total: Int = 0) {
    fun recordAnswer(isCorrect: Boolean) {
        total += 1
        if (isCorrect) {
            correct += 1
        }
    }
}

data class TransliterationExerciseState(
    val inputs: String = "",
    val position: Int = 0,
    val complete: Boolean = false,
    val score: ExerciseScore = ExerciseScore(0, 0)
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

enum class LessonTextModifier {
    BOLD,
    ITALIC
}

data class LessonTextSpan(
    val text: String,
    val modifiers: List<LessonTextModifier>
)

data class LessonTextBlock(
    val spans: List<LessonTextSpan>
) {
    override fun toString(): String {
        return spans.joinToString(separator = "") { s -> s.text }
    }
}

data class LessonBlock(
    val textBlock: LessonTextBlock,
    val imageResourceId: Int?
)

data class Lesson(
    val id: String,
    val title: String,
    val body: List<LessonBlock>
) {
    fun stringPreview(): String {
        return body.joinToString(separator = "") { b -> b.textBlock.toString() }
    }
}

enum class Language {
    ANY,
    EN,
    DA,
    SE
}

fun Language.toDisplayableString(): String {
    return when (this) {
        Language.ANY -> "any"
        Language.EN -> "English"
        Language.DA -> "Danish"
        Language.SE -> "Swedish"
    }
}

enum class MaterialType {
    ANY,
    BOOK,
    PODCAST,
    ONLINE_ARTICLE,
    TV
}

fun MaterialType.toDisplayableString(): String {
    return when (this) {
        MaterialType.ANY -> "all"
        MaterialType.BOOK -> "book"
        MaterialType.PODCAST -> "podcast"
        MaterialType.ONLINE_ARTICLE -> "article"
        MaterialType.TV -> "TV"
    }
}

data class Material(
    val id: String,
    val name: String,
    val author: String,
    val description: String,
    val link: String,
    val type: MaterialType,
    val language: Language = Language.EN
)