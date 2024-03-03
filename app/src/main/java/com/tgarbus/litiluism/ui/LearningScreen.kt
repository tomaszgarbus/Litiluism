package com.tgarbus.litiluism.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.Lesson
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.DoneMarker
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header
import com.tgarbus.litiluism.viewmodel.LearningViewModel

@Composable
fun LessonListItem(
    lesson: Lesson,
    lessonNumber: Int,
    isCompleted: Boolean,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 1.dp, RoundedCornerShape(21.dp))
            .background(Color.White, RoundedCornerShape(21.dp))
            .clickable { navController.navigate("lesson/$lessonNumber") }
            .padding(20.dp)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${
                        LocalContext.current.getString(
                            R.string.learning_lesson
                        )
                    } ${lessonNumber + 1}",
                    fontFamily = sarabunFontFamily,
                    color = colorResource(R.color.primary),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                if (isCompleted) {
                    DoneMarker()
                }
            }
            Text(
                text = lesson.title,
                fontFamily = sarabunFontFamily,
                color = colorResource(R.color.dark_grey),
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = lesson.stringPreview(),
                fontFamily = sarabunFontFamily,
                color = colorResource(R.color.dim_grey_text),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 3,
            )
        }
    }
}

@Composable
fun LessonsScreen(navController: NavController, viewModel: LearningViewModel = viewModel()) {
    val completedLessons =
        viewModel.getCompletedLessons(LocalContext.current).collectAsState(setOf())
    Box(modifier = Modifier.fillMaxSize()) {
        FullScreenPaddedColumn {
            Header(
                LocalContext.current.getString(
                    R.string.learning_learn
                )
            )
            StaticContentRepository.getInstance().lessons.forEachIndexed { i, lesson ->
                LessonListItem(lesson, i, completedLessons.value.contains(lesson.id), navController)
            }
        }
        Dock(ButtonType.LEARNING, navController)
    }
}