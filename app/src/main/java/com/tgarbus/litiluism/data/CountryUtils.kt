package com.tgarbus.litiluism.data

import android.content.Context
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

fun countryToName(country: Country, context: Context): String {
    return context.getString(when (country) {
        Country.ANY -> R.string.country_all
        Country.FO -> R.string.country_faroe_islands
        Country.DA -> R.string.country_denmark
        Country.IS -> R.string.country_iceland
        Country.NO -> R.string.country_norway
        Country.SE -> R.string.country_sweden
    })
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