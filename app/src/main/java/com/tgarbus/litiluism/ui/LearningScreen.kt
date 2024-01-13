package com.tgarbus.litiluism.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.Lesson
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.Header

@Composable
fun LessonListItem(lesson: Lesson, lessonNumber: Int, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 1.dp, RoundedCornerShape(21.dp))
            .background(colorResource(R.color.white), RoundedCornerShape(21.dp))
            .clickable { navController.navigate("lesson/$lessonNumber") }
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = "Lesson ${lessonNumber + 1}",
                fontFamily = sarabunFontFamily,
                color = colorResource(R.color.primary),
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
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
            Header("Learn")
            StaticContentRepository.getInstance().lessons.forEachIndexed { i, lesson ->
                LessonListItem(lesson, i, navController)
            }
        }
        Dock(ButtonType.LEARNING, navController)
    }
}