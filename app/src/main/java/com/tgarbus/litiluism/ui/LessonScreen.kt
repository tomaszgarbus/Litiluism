package com.tgarbus.litiluism.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.Lesson
import com.tgarbus.litiluism.data.LessonTextBlock
import com.tgarbus.litiluism.data.LessonTextModifier
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.PrimaryButton
import com.tgarbus.litiluism.ui.reusables.closeButton
import com.tgarbus.litiluism.viewmodel.LessonViewModel

@Composable
fun LessonTextBlockView(textBlock: LessonTextBlock) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontFamily = Fonts.sarabunFontFamily,
                    fontSize = 14.sp,
                    color = colorResource(R.color.dark_grey)
                )
            ) {
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
fun LessonTextBlockOnImageView(textBlock: LessonTextBlock) {
    val hidden = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .padding(14.dp)
            .shadow(1.dp, RoundedCornerShape(size = 21.dp))
            .clip(RoundedCornerShape(size = 21.dp))
            .background(Color.White)
            .clickable { hidden.value = !hidden.value }
            .padding(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (hidden.value) {
                Icon(
                    painterResource(R.drawable.icon_question_mark),
                    LocalContext.current.getString(
                        R.string.content_description_show_description
                    ),
                    modifier = Modifier.size(14.dp),
                    tint = colorResource(R.color.black)
                )
            } else {
                Icon(
                    painterResource(R.drawable.icon_backarrow),
                    LocalContext.current.getString(
                        R.string.content_description_hide_description
                    ),
                    modifier = Modifier.size(14.dp),
                    tint = colorResource(R.color.black)
                )
                Text(" ")
                LessonTextBlockView(textBlock)
            }
        }
    }
}

@Composable
fun LessonView(lesson: Lesson) {
    Column {
        for (block in lesson.body) {
            if (block.imageResourceId != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 1.dp, RoundedCornerShape(size = 28.dp))
                        .clip(RoundedCornerShape(size = 28.dp)),
                ) {
                    val painter = painterResource(block.imageResourceId)
                    Image(
                        painter,
                        block.textBlock.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .aspectRatio(ratio = painter.intrinsicSize.width / painter.intrinsicSize.height),
                        contentScale = ContentScale.Fit,
                    )
                    LessonTextBlockOnImageView(block.textBlock)
                }
            } else {
                SelectionContainer {
                    LessonTextBlockView(block.textBlock)
                }
            }
        }
    }
}

@Composable
fun LessonHeaderFrame(lesson: Lesson, navController: NavController) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        closeButton(
            description = LocalContext.current.getString(
                R.string.content_description_close_lesson
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) { navController.popBackStack() }
        Text(
            text = LocalContext.current.getString(
                R.string.lesson_lesson
            ),
            modifier = Modifier
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = Fonts.sarabunFontFamily,
                fontWeight = FontWeight.ExtraBold,
                brush = Brush.linearGradient(
                    listOf(
                        colorResource(R.color.primary),
                        colorResource(R.color.primary_gradient)
                    )
                )
            ),
            color = colorResource(R.color.dark_grey)
        )
    }
    Text(
        text = lesson.title,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        fontFamily = Fonts.sarabunFontFamily,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        fontWeight = FontWeight.ExtraBold,
        color = colorResource(R.color.dark_grey)
    )
}

@Composable
fun LessonScreen(navController: NavController, viewModel: LessonViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    FullScreenPaddedColumn(unownedScrollState = scrollState) {
        val lesson = viewModel.lesson
        // TODO: deduplicate with exercise header frame
        LessonHeaderFrame(lesson, navController)
        LessonView(lesson)
        val context = LocalContext.current
        PrimaryButton(LocalContext.current.getString(R.string.complete)) {
            viewModel.markAsComplete(context)
            navController.popBackStack()
        }
    }
}