package com.tgarbus.litiluism.data

import com.tgarbus.litiluism.R

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

fun maybeCountryFlagResource(country: Country): Int? {
    return when (country) {
        Country.SE -> R.drawable.flag_se
        Country.NO -> R.drawable.flag_no
        Country.IS -> R.drawable.flag_is
        Country.DA -> R.drawable.flag_dk
        Country.FO -> R.drawable.flag_fo
        else -> null
    }
}