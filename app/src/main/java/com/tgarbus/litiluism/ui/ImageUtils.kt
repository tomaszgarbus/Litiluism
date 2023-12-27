package com.tgarbus.litiluism.ui

import android.content.Context

fun getThumbnailResourceId(imgResourceName: String, context: Context): Int {
    return context.resources.getIdentifier(
        "thumbnail_" + imgResourceName,
        "drawable",
        context.packageName
    )
}

fun getImageResourceId(imgResourceName: String, context: Context): Int {
    return context.resources.getIdentifier(
        "image_" + imgResourceName,
        "drawable",
        context.packageName
    )
}