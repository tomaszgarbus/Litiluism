package com.tgarbus.litiluism.ui.reusables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.tgarbus.litiluism.R

@Composable
fun FiltersToggle(
    showFiltersDialog: MutableState<Boolean>
) {
    val containerColor =
        (if (showFiltersDialog.value)
            animateColorAsState(Color.White)
        else animateColorAsState(colorResource(R.color.secondary))).value
    val contentColor =
        (if (showFiltersDialog.value)
            animateColorAsState(colorResource(R.color.secondary))
        else animateColorAsState(Color.White)).value
    val rotation = animateFloatAsState(if (showFiltersDialog.value) 0f else 180f)
    FilledIconButton(
        onClick = { showFiltersDialog.value = !showFiltersDialog.value },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = Modifier.rotate(rotation.value)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_filter),
            contentDescription = "filters",
        )
    }
}