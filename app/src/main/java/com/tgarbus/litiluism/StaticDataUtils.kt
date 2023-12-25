package com.tgarbus.litiluism

import android.content.Context
import android.util.JsonReader
import android.util.JsonToken
import java.io.StringReader

fun loadThumbnail(imgId: String, context: Context): Int {
    return context.resources.getIdentifier(
        "thumbnail_" + stripFileExtension(removeScandinavianLetters(imgId)),
        "drawable",
        context.packageName
    )
}

fun loadImage(imgId: String, context: Context): Int {
    return context.resources.getIdentifier(
        "image_" + stripFileExtension(removeScandinavianLetters(imgId)),
        "drawable",
        context.packageName
    )
}

interface FromJson {

    data class RuneRow(
        val symbols: HashMap<String, List<String>>,
        val overrideSymbols: HashMap<String, List<String>>,
        val name: String,
        val inheritsFrom: String,
    )

    companion object {

        private fun readStringOrList(jsonReader: JsonReader): List<String> {
            if (jsonReader.peek() == JsonToken.STRING) {
                return listOf(jsonReader.nextString())
            }
            jsonReader.beginArray()
            var result = arrayListOf<String>()
            while (jsonReader.hasNext()) {
                result.add(jsonReader.nextString())
            }
            jsonReader.endArray()
            return result.toList()
        }

        private fun readRuneRowSymbol(jsonReader: JsonReader): Pair<String, List<String>> {
            jsonReader.beginObject()
            var name = "";
            var latin = listOf<String>()
            while (jsonReader.hasNext()) {
                val property = jsonReader.nextName()
                if (property == "rune") {
                    name = jsonReader.nextString()
                }
                if (property == "latin") {
                    latin = readStringOrList(jsonReader)
                }
            }
            jsonReader.endObject()
            return Pair(name, latin)
        }

        private fun readRuneRowSymbols(jsonReader: JsonReader): HashMap<String, List<String>> {
            jsonReader.beginArray()
            var symbols = HashMap<String, List<String>>()
            while (jsonReader.hasNext()) {
                val symbol = readRuneRowSymbol(jsonReader)
                symbols[symbol.first] = symbol.second
            }
            jsonReader.endArray()
            return symbols
        }

        private fun readRuneRow(jsonReader: JsonReader): RuneRow {
            jsonReader.beginObject()
            var inheritsFrom = ""
            var name = ""
            var symbols = HashMap<String, List<String>>()
            var overrideSymbols = HashMap<String, List<String>>()
            while (jsonReader.hasNext()) {
                val property = jsonReader.nextName()
                if (property == "name") {
                    name = jsonReader.nextString()
                } else if (property == "inherit_from") {
                    inheritsFrom = jsonReader.nextString()
                } else if (property == "symbols") {
                    symbols = readRuneRowSymbols(jsonReader)
                } else if (property == "override_symbols") {
                    overrideSymbols = readRuneRowSymbols(jsonReader)
                }
            }
            jsonReader.endObject()
            return RuneRow(
                symbols = symbols,
                overrideSymbols = overrideSymbols,
                name = name,
                inheritsFrom = inheritsFrom
            )
        }

        private fun readRuneRows(jsonReader: JsonReader): HashMap<String, RuneRow> {
            jsonReader.beginObject()
            var result = HashMap<String, RuneRow>()
            while (jsonReader.hasNext()) {
                result[jsonReader.nextName()] = readRuneRow(jsonReader)
            }
            jsonReader.endObject()
            return result
        }

        fun loadCanonicalRuneRows(): RuneRowsMap {
            val runeRows = readRuneRows(
                JsonReader(
                    StringReader(
                        StaticData.jsonRuneRows
                    )
                )
            )
            val canonicalFormRuneRows = RuneRowsMap()
            for ((id, runeRow) in runeRows) {
                val canonicalFormRuneRow = RuneRow(
                    id = id, name = runeRow.name, mapping = runeRow.symbols,
                )
                if (runeRow.inheritsFrom.isNotEmpty()) {
                    canonicalFormRuneRow.mapping.putAll(runeRows[runeRow.inheritsFrom]!!.symbols)
                    canonicalFormRuneRow.mapping.putAll(runeRow.overrideSymbols)
                }
                canonicalFormRuneRows[id] = canonicalFormRuneRow
            }
            return canonicalFormRuneRows
        }

        private fun readRunes(jsonReader: JsonReader): String {
            return if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                var runes = ""
                jsonReader.beginArray()
                while (jsonReader.hasNext()) {
                    runes += jsonReader.nextString()
                }
                jsonReader.endArray()
                runes
            } else {
                jsonReader.nextString()
            }
        }

        private fun readExercise(
            jsonReader: JsonReader,
            runeRowsMap: RuneRowsMap,
            context: Context
        ): Exercise {
            var id = ""
            var title = ""
            var runes = ""
            var description = ""
            var runeRowId = ""
            var explanation = ""
            var thmbResource = -1
            var imgResource = -1
            var country = Country.ANY
            jsonReader.beginObject()
            while (jsonReader.hasNext()) {
                val property = jsonReader.nextName()
                when (property) {
                    "id" -> id = jsonReader.nextString()
                    "title" -> title = jsonReader.nextString()
                    "runes" -> runes = readRunes(jsonReader)
                    "description" -> description = jsonReader.nextString()
                    "rowType" -> runeRowId = jsonReader.nextString()
                    "img" -> {
                        val fname = jsonReader.nextString()
                        imgResource = loadImage(fname, context)
                        thmbResource = loadThumbnail(fname, context)
                    }
                    "explanationAfter" -> explanation = jsonReader.nextString()
                    "country" -> country = countryFromCode(jsonReader.nextString())
                    else -> jsonReader.skipValue()
                }
            }
            jsonReader.endObject()
            return Exercise(
                id = id,
                title = title,
                runes = runes,
                description = description,
                imgResource = imgResource,
                thumbnailResource = thmbResource,
                runeRow = runeRowsMap[runeRowId]!!,
                country = country,
                explanation = explanation,
            )
        }

        private fun readExercises(
            jsonReader: JsonReader,
            runeRowsMap: RuneRowsMap,
            context: Context
        ): List<Exercise> {
            var exercises = arrayListOf<Exercise>()
            jsonReader.beginArray()
            while (jsonReader.hasNext()) {
                exercises.add(readExercise(jsonReader, runeRowsMap, context))
            }
            jsonReader.endArray()
            return exercises.toList()
        }

        fun loadExercises(runeRowsMap: RuneRowsMap, context: Context): List<Exercise> {
            return readExercises(
                JsonReader(StringReader(StaticData.jsonExercises)),
                runeRowsMap,
                context
            )
        }
    }
}
