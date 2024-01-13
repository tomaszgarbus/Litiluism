package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.tgarbus.litiluism.R

@Composable
fun FullScreenPaddedColumn(
    scrollable: Boolean = true,
    content: @Composable () -> Unit
) {
    var modifier = Modifier
        .fillMaxSize()
        // TODO: set bg color through theme
        .background(colorResource(R.color.light_bg))
    if (scrollable) {
        val scrollState = rememberScrollState()
        modifier = modifier.verticalScroll(scrollState)
    }
    modifier = modifier
        .padding(vertical = 20.dp)
        .padding(horizontal = 20.dp)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        content()
    }
}