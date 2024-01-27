package com.tgarbus.litiluism.ui.reusables

import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.tgarbus.litiluism.R

@Composable
fun FiltersToggle(
    showFiltersDialog: MutableState<Boolean>
) {
    FilledIconButton(
        onClick = { showFiltersDialog.value = !showFiltersDialog.value },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = colorResource(R.color.secondary),
            contentColor = Color.White
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_filter),
            contentDescription = "filters",
        )
    }
}