package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.data.ThreeButtonOptions
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily

@Composable
fun InlineManualInput(
    options: ThreeButtonOptions,
    onCorrectAnswer: (Char, Boolean) -> Unit
) {
    var input by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val correctAnswers = options.find { it.second }?.first
    val showFeedback = rememberSaveable { mutableStateOf(false) }
    TextField(
        value = input,
        onValueChange = {
            if (it.length <= 1) {
                input = it
            }
            if (input.length == 1) {
                if (correctAnswers?.contains(input[0]) == true) {
                    onCorrectAnswer(input[0], !showFeedback.value)
                    showFeedback.value = false
                    input = ""
                } else {
                    showFeedback.value = true
                }
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        modifier = Modifier
            .width(42.dp)
            .clip(RoundedCornerShape(21.dp))
            .focusRequester(focusRequester)
            .onGloballyPositioned { focusRequester.requestFocus() },
        textStyle = TextStyle(
            fontFamily = sarabunFontFamily,
            fontSize = 12.sp
        )
    )
}