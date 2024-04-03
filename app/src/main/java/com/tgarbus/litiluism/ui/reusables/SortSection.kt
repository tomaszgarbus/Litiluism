package com.tgarbus.litiluism.ui.reusables

import android.content.res.Resources
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.baseRuneRowToString
import com.tgarbus.litiluism.ui.Fonts

enum class SortOrder {
    DEFAULT,
    LENGTH,
    ALPHABETICAL
}

fun SortOrder.toString(resources: Resources): String {
    return when (this) {
        SortOrder.DEFAULT -> resources.getString(R.string.sort_order_default)
        SortOrder.LENGTH -> resources.getString(R.string.sort_order_length)
        SortOrder.ALPHABETICAL -> resources.getString(R.string.sort_order_alphabetical)
    }
}

@Composable
fun SortSection(
    onValueChange: (SortOrder) -> Unit,
    activeOrder: SortOrder,
) {
    val scrollState = rememberScrollState()
    Column {
        Text(
            stringResource(R.string.sort_by),
            fontFamily = Fonts.sarabunFontFamily,
            color = colorResource(R.color.dark_grey)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            for (value in SortOrder.entries) {
                // TODO: Rename to "FilterOrSortButton"?
                FilterButton(
                    buttonValue = value,
                    onClick = { onValueChange(value) },
                    activeValue = activeOrder
                ) {
                    Text(
                        text = value.toString(LocalContext.current.resources),
                        fontFamily = Fonts.sarabunFontFamily,
                        // DO NOT SET COLOR. COLOR INHERITED FROM BUTTON.
//                color = colorResource(R.color.dark_grey)
                    )
                }
            }
        }
    }
}