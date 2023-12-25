package com.tgarbus.litiluism

fun countryFromCode(code: String): Country {
    return when (code) {
        "SE" -> Country.SE
        "NO" -> Country.NO
        "DA" -> Country.DA
        "FO" -> Country.FO
        "IS" -> Country.IS
        else -> Country.ANY
    }
}

fun countryToName(country: Country): String {
    return when (country) {
        Country.ANY -> "All countries"
        Country.FO -> "Faroe Islands"
        Country.DA -> "Denmark"
        Country.IS -> "Iceland"
        Country.NO -> "Norway"
        Country.SE -> "Sweden"
    }
}

fun nameToCountry(name: String): Country {
    for (ct in Country.entries) {
        if (countryToName(ct) == name) {
            return ct
        }
    }
    return Country.ANY
}