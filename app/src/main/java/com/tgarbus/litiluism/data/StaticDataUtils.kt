package com.tgarbus.litiluism.data

import android.util.JsonReader
import android.util.JsonToken
import com.tgarbus.litiluism.countryFromCode
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

        private fun readSources(jsonReader: JsonReader): List<String> {
            val sources = arrayListOf<String>()
            jsonReader.beginArray()
            while (jsonReader.hasNext()) {
                sources.add(jsonReader.nextString())
            }
            jsonReader.endArray()
            return sources.toList()
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
                sources = sources
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

        fun loadExercises(runeRowsMap: RuneRowsMap): List<TransliterationExercise> {
            return readExercises(
                JsonReader(StringReader(StaticData.jsonExercises)),
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