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
    return exercise.solution.map { c ->
        listOf(
            Pair(c, true),
            Pair(randomLetter(), false),
            Pair(randomLetter(), false)
        )
    }
}

fun imgResourceForId(exerciseId: String): Int {
    when (exerciseId) {w
        "example" -> R.drawable.gripsholmstone179
    }
    return -1
}

fun exampleExercise(): Exercise {
    val id: String = "example"
    val title: String = "Gripsholm stone Sö 179"
    val description: String =
        "The Gripsholm Stone - which can to this day be visited near the Gripsholm Castle not far from Stockholm - is one of the \\\"Ingvarr stones\\\". This group of stones is dated soon after 1041, when the Icelandic annals record the death of Yngvarr the Far-Travelled in the south. Note how the inscriber forgot one word and added it later!"
    val runes: String =
        "ᛏᚢᛚᛅ:ᛚᛁᛏ:ᚱᛅᛁᛋᛅ:ᛋᛏᛅᛁᚾ:ᚦᛁᚾᛋᛅᛏ:ᛋᚢᚾ:ᛋᛁᚾ:ᚼᛅᚱᛅᛚᛏ:ᛒᚱᚢᚦᚢᚱ:ᛁᚾᚴᚢᛅᚱᛋ:ᚦᛅᛁᛦ|ᚠᚢᚱᚢ:ᛏᚱᛁᚴᛁᛚᛅ:ᚠᛁᛅᚱᛁ:ᛅᛏ:ᚴᚢᛚᛁ:ᛅᚢᚴ:ᛅ:ᚢᛋᛏᛅᚱᛚᛅᚱ:ᚾᛁ:ᚴᛅᚠᚢ:ᛏᚢᚢ:ᛋᚢᚾᛅᚱ:ᛚᛅ:ᛅᛋᛁᚱᚴ:ᛚᛅᚾ:ᛏᛁ"
    val solution: String = "talu:lat:raisa:stain:dinsat:sun:sin:haralt:brudur"
    val imgResource = R.drawable.gripsholmstone179
    return Exercise(
        id = id,
        title = title,
        description = description,
        runes = runes,
        solution = solution,
        imgResource = imgResource
    )
}