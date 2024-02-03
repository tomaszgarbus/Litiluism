package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
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
    unownedScrollState: ScrollState? = null,
    content: @Composable () -> Unit
) {
    var modifier = Modifier
        .fillMaxSize()
        // TODO: set bg color through theme
        .background(colorResource(R.color.light_bg))
    if (scrollable) {
        val scrollState = unownedScrollState ?: rememberScrollState()
        modifier = modifier.verticalScroll(scrollState)
    }
    modifier = modifier
        .padding(horizontal = 20.dp)
    Column(
        modifier = modifier.safeDrawingPadding(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        content()
        Spacer(modifier = Modifier.height(70.dp))
    }
}