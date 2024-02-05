package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.tgarbus.litiluism.R

@Composable
fun <T> FilterButton(
    buttonValue: T,
    activeValue: T,
    onClick: (T) -> Unit,
    content: @Composable () -> Unit
) {
    val padding = PaddingValues(horizontal = 8.dp)
    if (buttonValue == activeValue) {
        Button(
            onClick = { onClick(buttonValue) }, colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.secondary),
                contentColor = Color.White
            ),
            contentPadding = padding,
        ) {
            content()
        }
    } else {
        OutlinedButton(
            onClick = { onClick(buttonValue) },
            contentPadding = padding,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = colorResource(R.color.secondary),
            )
        ) {
            content()
        }
    }
}