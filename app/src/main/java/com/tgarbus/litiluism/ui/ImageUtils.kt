package com.tgarbus.litiluism.ui

import android.content.Context

fun getDrawableResourceId(imgResourceName: String, context: Context): Int {
    return context.resources.getIdentifier(
        imgResourceName,
        "drawable",
        context.packageName
    )
}

fun getThumbnailResourceId(imgResourceName: String, context: Context): Int {
    return getDrawableResourceId("thumbnail_$imgResourceName", context)
}

fun getImageResourceId(imgResourceName: String, context: Context): Int {
    return getDrawableResourceId("image_$imgResourceName", context)
}