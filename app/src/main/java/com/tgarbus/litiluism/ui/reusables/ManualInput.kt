package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.tgarbus.litiluism.data.ThreeButtonOptions
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily

@Composable
fun ManualInput(
    options: ThreeButtonOptions,
    onCorrectAnswer: (Char, Boolean) -> Unit
) {
    var input by remember { mutableStateOf("") }
    val correctAnswers = options.find { it.second }?.first
    val showFeedback = rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            InputFeedbackText(
                "Correct answer is: ${
                    correctAnswers?.joinToString(separator = "/") { c -> c.toString() }
                }. Input below to continue.",
                showFeedback.value
            )
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
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(21.dp)),
                label = { Text("Input 1 letter") },
                textStyle = TextStyle(
                    fontFamily = sarabunFontFamily
                )
            )
        }
    }
}