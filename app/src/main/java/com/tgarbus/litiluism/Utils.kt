package com.tgarbus.litiluism

import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.ExercisesMap
import com.tgarbus.litiluism.data.ExercisesMapImpl
import kotlin.math.abs
import kotlin.random.Random

fun randomLetter(): Char {
    val letters = "qwertyuiopasdfghjklzxcvbnm"
    val index = abs(Random.nextInt()) % letters.length
    return letters[index]
}

fun isSeparator(rune: Char): Boolean {
    return charArrayOf(':', '᛫', '…', '|', ' ', '+', '-', '(', ')', '|', 'x').contains(rune);
}

fun generateOptions(transliterationExercise: TransliterationExercise): List<List<Pair<Char, Boolean>>> {
    return transliterationExercise.solution().map { c ->
        listOf(
            Pair(c, true),
            Pair(randomLetter(), false),
            Pair(randomLetter(), false)
        ).shuffled()
    }
}

fun List<TransliterationExercise>.toHashMapById(): ExercisesMap {
    val map = ExercisesMapImpl()
    for (e in this) {
        map[e.id] = e
    }
    return map
}

fun stripFileExtension(str: String): String {
    return str.replace(".jpg", "").replace(".png", "")
}

fun removeScandinavianLetters(str: String): String {
    return str.replace('æ', 'a').replace('á', 'a').replace('ø', 'o').replace('í', 'i')
        .replace('ö', 'o')
        .replace('ä', 'a').replace('å', 'a')
}
