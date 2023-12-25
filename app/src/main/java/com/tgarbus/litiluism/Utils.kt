package com.tgarbus.litiluism

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

fun generateOptions(exercise: Exercise): List<List<Pair<Char, Boolean>>> {
    return exercise.solution().map { c ->
        listOf(
            Pair(c, true),
            Pair(randomLetter(), false),
            Pair(randomLetter(), false)
        ).shuffled()
    }
}

fun List<Exercise>.toHashMap(): ExercisesMap {
    val map = ExercisesMap()
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