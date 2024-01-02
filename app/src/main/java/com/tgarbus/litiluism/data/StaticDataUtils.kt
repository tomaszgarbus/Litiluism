package com.tgarbus.litiluism.data

import android.content.Context
import android.util.JsonReader
import android.util.JsonToken
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.removeScandinavianLetters
import com.tgarbus.litiluism.stripFileExtension
import java.io.StringReader

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

        fun loadCanonicalRuneRows(
            context: Context
        ): RuneRowsMap {
            val rawStringData = context.resources.openRawResource(R.raw.rune_rows).bufferedReader()
                .use { it.readText() }
            val runeRows = readRuneRows(
                JsonReader(
                    StringReader(
                        rawStringData
                    )
                )
            )
            val canonicalFormRuneRows = RuneRowsMap()
            for ((id, runeRow) in runeRows) {
                if (runeRow.inheritsFrom.isEmpty()) {
                    val canonicalFormRuneRow = RuneRow(
                        id = id,
                        name = runeRow.name,
                        mapping = runeRow.symbols,
                        baseRuneRow = maybeBaseRuneRowFromId(id)!!
                    )
                    canonicalFormRuneRows[id] = canonicalFormRuneRow
                }
            }
            for ((id, runeRow) in runeRows) {
                if (runeRow.inheritsFrom.isNotEmpty()) {
                    val mapping = runeRow.symbols
                    mapping.putAll(runeRows[runeRow.inheritsFrom]!!.symbols)
                    mapping.putAll(runeRow.overrideSymbols)
                    val baseRuneRow = canonicalFormRuneRows[runeRow.inheritsFrom]!!.baseRuneRow
                    val canonicalFormRuneRow = RuneRow(
                        id = id, name = runeRow.name, mapping = mapping, baseRuneRow = baseRuneRow!!
                    )
                    canonicalFormRuneRows[id] = canonicalFormRuneRow
                }
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

        private fun readSources(jsonReader: JsonReader): List<String> {
            val sources = arrayListOf<String>()
            jsonReader.beginArray()
            while (jsonReader.hasNext()) {
                sources.add(jsonReader.nextString())
            }
            jsonReader.endArray()
            return sources.toList()
        }

        private fun readLocation(jsonReader: JsonReader): Location {
            jsonReader.beginObject()
            var lat = 0.0
            var long = 0.0
            while (jsonReader.hasNext()) {
                when (jsonReader.nextName()) {
                    "lat" -> lat = jsonReader.nextDouble()
                    "long" -> long = jsonReader.nextDouble()
                }
            }
            jsonReader.endObject()
            return Location(lat, long)
        }

        private fun readExercise(
            jsonReader: JsonReader,
            runeRowsMap: RuneRowsMap
        ): TransliterationExercise {
            var id = ""
            var title = ""
            var runes = ""
            var description = ""
            var runeRowId = ""
            var explanation = ""
            var imgResource = ""
            var sources = listOf<String>()
            var country = Country.ANY
            var location: Location? = null
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
                        imgResource = stripFileExtension(removeScandinavianLetters(fname))
                    }

                    "explanationAfter" -> explanation = jsonReader.nextString()
                    "country" -> country = countryFromCode(jsonReader.nextString())
                    "sources" -> sources = readSources(jsonReader)
                    "location" -> location = readLocation(jsonReader)
                    else -> jsonReader.skipValue()
                }
            }
            jsonReader.endObject()
            return TransliterationExercise(
                id = id,
                title = title,
                runes = runes,
                description = description,
                imgResourceName = imgResource,
                runeRow = runeRowsMap[runeRowId]!!,
                country = country,
                explanation = explanation,
                sources = sources,
                location = location
            )
        }

        private fun readExercises(
            jsonReader: JsonReader,
            runeRowsMap: RuneRowsMap
        ): List<TransliterationExercise> {
            var transliterationExercises = arrayListOf<TransliterationExercise>()
            jsonReader.beginArray()
            while (jsonReader.hasNext()) {
                transliterationExercises.add(readExercise(jsonReader, runeRowsMap))
            }
            jsonReader.endArray()
            return transliterationExercises.toList()
        }

        fun loadExercises(
            context: Context,
            runeRowsMap: RuneRowsMap
        ): List<TransliterationExercise> {
            val rawStringData = context.resources.openRawResource(R.raw.exercises).bufferedReader()
                .use { it.readText() }
            return readExercises(
                JsonReader(StringReader(rawStringData)),
                runeRowsMap
            )
        }
    }
}

fun buildExerciseByCountryCountMap(exercises: List<TransliterationExercise>): HashMap<Country, Int> {
    val result = HashMap<Country, Int>()
    for (e in exercises) {
        result[e.country] = result.getOrDefault(e.country, 0) + 1
        if (e.country != Country.ANY) {
            result[Country.ANY] = result.getOrDefault(Country.ANY, 0) + 1
        }
    }
    return result
}