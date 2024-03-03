package com.tgarbus.litiluism.data

import android.content.Context
import com.tgarbus.litiluism.R

fun maybeBaseRuneRowFromId(id: String): BaseRuneRow? {
    return when (id) {
        "older_futhark" -> BaseRuneRow.OLDER_FUTHARK
        "younger_futhark_long_branch" -> BaseRuneRow.YOUNGER_FUTHARK_LONG_BRANCH
        "younger_futhark_short_twig" -> BaseRuneRow.YOUNGER_FUTHARK_SHORT_TWIG
        "anglo_saxon" -> BaseRuneRow.ANGLO_SAXON
        "medieval" -> BaseRuneRow.MEDIEVAL
        else -> null
    }
}

fun maybeBaseRuneRowToId(baseRuneRow: BaseRuneRow): String? {
    return when (baseRuneRow) {
        BaseRuneRow.ANGLO_SAXON -> "anglo_saxon"
        BaseRuneRow.OLDER_FUTHARK -> "older_futhark"
        BaseRuneRow.MEDIEVAL -> "medieval"
        BaseRuneRow.YOUNGER_FUTHARK_SHORT_TWIG -> "younger_futhark_short_twig"
        BaseRuneRow.YOUNGER_FUTHARK_LONG_BRANCH -> "younger_futhark_long_branch"
        else -> null
    }
}

fun baseRuneRowToString(baseRuneRow: BaseRuneRow, context: Context): String {
    return context.getString(when (baseRuneRow) {
        BaseRuneRow.ANY -> R.string.alphabet_all
        BaseRuneRow.MEDIEVAL -> R.string.alphabet_medieval
        BaseRuneRow.ANGLO_SAXON -> R.string.alphabet_anglo_saxon
        BaseRuneRow.YOUNGER_FUTHARK_SHORT_TWIG -> R.string.alphabet_younger_futhark_short_twig
        BaseRuneRow.YOUNGER_FUTHARK_LONG_BRANCH -> R.string.alphabet_younger_futhark_long_branch
        BaseRuneRow.OLDER_FUTHARK -> R.string.alphabet_older_futhark
    })
}

fun BaseRuneRow.toDisplayableString(context: Context): String {
    return baseRuneRowToString(this, context)
}

fun BaseRuneRow.getIconResourceId(): Int {
    return when (this) {
        BaseRuneRow.ANGLO_SAXON -> R.drawable.icon_runerow_anglo_saxon
        BaseRuneRow.OLDER_FUTHARK -> R.drawable.icon_runerow_older_futhark
        BaseRuneRow.YOUNGER_FUTHARK_SHORT_TWIG -> R.drawable.icon_runerow_short_twig
        BaseRuneRow.YOUNGER_FUTHARK_LONG_BRANCH -> R.drawable.icon_runerow_long_branch
        BaseRuneRow.MEDIEVAL -> R.drawable.icon_runerow_medieval
        BaseRuneRow.ANY -> 0
    }
}