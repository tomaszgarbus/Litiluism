package com.tgarbus.litiluism.data

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

fun baseRuneRowToString(baseRuneRow: BaseRuneRow): String {
    return when (baseRuneRow) {
        BaseRuneRow.ANY -> "All alphabets"
        BaseRuneRow.MEDIEVAL -> "Medieval"
        BaseRuneRow.ANGLO_SAXON -> "Anglo-Saxon"
        BaseRuneRow.YOUNGER_FUTHARK_SHORT_TWIG -> "Younger Futhark short-twig"
        BaseRuneRow.YOUNGER_FUTHARK_LONG_BRANCH -> "Younger Futhark long-branch"
        BaseRuneRow.OLDER_FUTHARK -> "Older Futhark"
    }
}