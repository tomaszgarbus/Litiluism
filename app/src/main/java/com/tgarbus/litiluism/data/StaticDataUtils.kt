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
        val symbols: HashMap<Char, List<Char>>,
        val overrideSymbols: HashMap<Char, List<Char>>,
        val name: String,
        val inheritsFrom: String,
    )

    companion object {

        private fun readCharOrList(jsonReader: JsonReader): List<Char> {
            if (jsonReader.peek() == JsonToken.STRING) {
                return listOf(jsonReader.nextString()[0])
            }
            jsonReader.beginArray()
            val result = arrayListOf<Char>()
            while (jsonReader.hasNext()) {
                result.add(jsonReader.nextString()[0])
            }
            jsonReader.endArray()
            return result.toList()
        }

        private fun readRuneRowSymbol(jsonReader: JsonReader): Pair<Char, List<Char>> {
            jsonReader.beginObject()
            var rune = ' ';
            var latin = listOf<Char>()
            while (jsonReader.hasNext()) {
                val property = jsonReader.nextName()
                if (property == "rune") {
                    rune = jsonReader.nextString()[0]
                }
                if (property == "latin") {
                    latin = readCharOrList(jsonReader)
                }
            }
            jsonReader.endObject()
            return Pair(rune, latin)
        }

        private fun readRuneRowSymbols(jsonReader: JsonReader): HashMap<Char, List<Char>> {
            jsonReader.beginArray()
            var symbols = HashMap<Char, List<Char>>()
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
            var symbols = HashMap<Char, List<Char>>()
            var overrideSymbols = HashMap<Char, List<Char>>()
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
            val canonicalFormRuneRows = RuneRowsMapImpl()
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

        private fun readLocation(jsonReader: JsonReader): Location {
            jsonReader.beginObject()
            var id = ""
            var lat = 0.0
            var long = 0.0
            var description = ""
            while (jsonReader.hasNext()) {
                when (jsonReader.nextName()) {
                    "id" -> id = jsonReader.nextString()
                    "lat" -> lat = jsonReader.nextDouble()
                    "long" -> long = jsonReader.nextDouble()
                    "description" -> description = jsonReader.nextString()
                }
            }
            jsonReader.endObject()
            return Location(id, lat, long, description)
        }

        private fun readLocations(jsonReader: JsonReader): List<Location> {
            val locations = arrayListOf<Location>()
            jsonReader.beginArray()
            while (jsonReader.hasNext()) {
                locations.add(readLocation(jsonReader))
            }
            jsonReader.endArray()
            return locations.toList()
        }

        fun loadLocations(
            context: Context,
        ): List<Location> {
            val rawStringData = context.resources.openRawResource(R.raw.locations).bufferedReader()
                .use { it.readText() }
            return readLocations(
                JsonReader(StringReader(rawStringData)),
            )
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
            runeRowsMap: RuneRowsMap,
            locationsMap: LocationsMap
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
                    "location" -> location = locationsMap[jsonReader.nextString()]
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
            runeRowsMap: RuneRowsMap,
            locations: List<Location>
        ): List<TransliterationExercise> {
            val transliterationExercises = arrayListOf<TransliterationExercise>()
            jsonReader.beginArray()
            while (jsonReader.hasNext()) {
                transliterationExercises.add(
                    readExercise(
                        jsonReader,
                        runeRowsMap,
                        locations.associateBy { l -> l.id })
                )
            }
            jsonReader.endArray()
            return transliterationExercises.toList()
        }

        fun loadExercises(
            context: Context,
            runeRowsMap: RuneRowsMap,
            locations: List<Location>
        ): List<TransliterationExercise> {
            val rawStringData = context.resources.openRawResource(R.raw.exercises).bufferedReader()
                .use { it.readText() }
            return readExercises(
                JsonReader(StringReader(rawStringData)),
                runeRowsMap,
                locations
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