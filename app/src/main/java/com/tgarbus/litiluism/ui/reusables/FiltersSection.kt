package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts

@Composable
fun <T> FiltersSection(
    values: List<T>,
    activeValue: T,
    onValueChange: (T) -> Unit,
    categoryName: String,
    valueDisplay: @Composable (T) -> Unit
) {
    val scrollState = rememberScrollState()
    Column {
        Text(
            categoryName,
            fontFamily = Fonts.sarabunFontFamily,
            color = colorResource(R.color.dark_grey)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            for (value in values) {
                FilterButton(
                    buttonValue = value,
                    onClick = { onValueChange(value) },
                    activeValue = activeValue
                ) {
                    valueDisplay(value)
                }
            }
        }
    }
}