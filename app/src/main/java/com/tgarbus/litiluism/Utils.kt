package com.tgarbus.litiluism

import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.ExercisesMap
import com.tgarbus.litiluism.data.ExercisesMapImpl
import com.tgarbus.litiluism.data.RuneRow
import com.tgarbus.litiluism.data.ThreeButtonOptions
import kotlin.math.abs
import kotlin.random.Random

fun randomLetter(): Char {
    val letters = "qwertyuiopasdfghjklzxcvbnm"
    val index = abs(Random.nextInt()) % letters.length
    return letters[index]
}

fun isSeparator(rune: Char): Boolean {
    return charArrayOf(':', '᛫', '…', '|', ' ', '+', '-', '(', ')', '|', 'x').contains(rune)
}

fun generateOptions(mapping: Map<Char, List<Char>>, symbol: Char): ThreeButtonOptions {
    fun areAnswersDistinctEnough(symbol1: Char, symbol2: Char): Boolean {
        return symbol1 != symbol2 && mapping[symbol1]!![0] != mapping[symbol2]!![0]
    }

    val symbols = mapping.keys.toList()
    var secondSymbol = symbols[abs(Random.nextInt()) % symbols.size]
    while (!areAnswersDistinctEnough(secondSymbol, symbol)) {
        secondSymbol = symbols[abs(Random.nextInt()) % symbols.size]
    }
    var thirdSymbol = symbols[abs(Random.nextInt()) % symbols.size]
    while (!areAnswersDistinctEnough(thirdSymbol, symbol) || !areAnswersDistinctEnough(
            thirdSymbol,
            secondSymbol
        )
    ) {
        thirdSymbol = symbols[abs(Random.nextInt()) % symbols.size]
    }
    return listOf(
        Pair(mapping[symbol]!!, true),
        Pair(mapping[secondSymbol]!!, false),
        Pair(mapping[thirdSymbol]!!, false),
    ).shuffled()
}

fun generateRuneToLatinOptions(runeRow: RuneRow, symbol: Char): ThreeButtonOptions {
    if (isSeparator(symbol)) {
        return listOf(
            Pair(listOf(symbol), false),
            Pair(listOf(symbol), false),
            Pair(listOf(symbol), false),
        )
    }
    return generateOptions(runeRow.mapping, symbol)
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
