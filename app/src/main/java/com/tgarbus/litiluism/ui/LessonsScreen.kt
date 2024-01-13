package com.tgarbus.litiluism.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.Lesson
import com.tgarbus.litiluism.data.LessonTextBlock
import com.tgarbus.litiluism.data.LessonTextModifier
import com.tgarbus.litiluism.data.LessonXmlParser
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.Header


val lessonText = """
    <title>piersza lekcja</title>
    <body>
    hey <i>you</i>
    
    look at this magnificent stone:
    
    <img src="image_kylverstone">kylverstone <b>massive <i>stone</i></b></img>
    
    did you <b><i> like </i></b> it?
    </body>
""".trimIndent()

@Composable
fun LessonTextBlockView(textBlock: LessonTextBlock) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontFamily = Fonts.sarabunFontFamily)) {
                for (span in textBlock.spans) {
                    val isItalic = span.modifiers.contains(LessonTextModifier.ITALIC)
                    val isBold = span.modifiers.contains(LessonTextModifier.BOLD)
                    withStyle(
                        style = SpanStyle(
                            fontStyle = if (isItalic) FontStyle.Italic else FontStyle.Normal,
                            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
                        )
                    ) {
                        append(span.text)
                    }
                }
            }
        }
    )
}

@Composable
fun LessonView(lesson: Lesson) {
    Column {
        Text(lesson.title)
        for (block in lesson.body) {
            if (block.imageResourceId != null) {
                Box() {
                    Image(
                        painterResource(block.imageResourceId),
                        block.textBlock.toString()
                    )
                    LessonTextBlockView(block.textBlock)
                }
            } else {
                LessonTextBlockView(block.textBlock)
            }
        }
    }
}

@Composable
fun LessonsScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                // TODO: set bg color through theme
                .background(colorResource(R.color.light_bg))
                .verticalScroll(scrollState)
                .padding(vertical = 20.dp)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Header("Lessons")
            val lesson = LessonXmlParser(lessonText).parse(LocalContext.current)
            Log.d("debug", lesson.toString())
            LessonView(lesson)
        }
        Dock(ButtonType.LEARNING, navController)
    }
}