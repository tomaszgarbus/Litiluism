package com.tgarbus.litiluism.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.BalloonsQueue
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header
import com.tgarbus.litiluism.ui.reusables.IntroTooltip
import com.tgarbus.litiluism.ui.reusables.PageIndicator
import com.tgarbus.litiluism.ui.reusables.PracticeTypeButton
import com.tgarbus.litiluism.ui.reusables.PracticeTypeButtonText

@Composable
fun GreenBanner(
    navController: NavController,
    text: String,
    buttonText: String? = null,
    buttonLink: String? = null,
    pageCount: Int,
    currentPage: Int
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        val painter = painterResource(R.drawable.banner_home)
        Image(
            painter,
            "banner",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(painter.intrinsicSize.width / painter.intrinsicSize.height),
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Text(
                text,
                fontFamily = sarabunFontFamily,
                color = colorResource(R.color.primary),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
            )
            if (buttonText != null && buttonLink != null) {
                PracticeTypeButtonText(
                    buttonText,
                    modifier = Modifier.clickable { navController.navigate(buttonLink) })
            }
        }
        PageIndicator(pageCount, currentPage, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GreenBannersGallery(navController: NavController) {
    val pageCount = 4
    val pagerState = rememberPagerState(pageCount = { pageCount })
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        pageSpacing = 10.dp
    ) { page ->
        when (page) {
            0 -> GreenBanner(
                navController,
                text = "Welcome! Take a look around and explore.",
                pageCount = pageCount,
                currentPage = page
            )
            1 -> GreenBanner(
                navController,
                text = "How about taking a peek into Historiska Museet in Stockholm?",
                buttonText = "Take me there!",
                buttonLink = "mapscreen/historiska",
                pageCount,
                page
            )
            2 -> GreenBanner(
                navController,
                text = "Or do you prefer to visit Nationalmuseet in Denmark?",
                buttonText = "Take me there!",
                buttonLink = "mapscreen/da_nationalmuseet",
                pageCount,
                page
            )
            3 -> GreenBanner(navController,
                text = "After completing some exercises, you're welcome to check your stats.",
                buttonText = "Take me there!",
                buttonLink = "statistics",
                pageCount = pageCount,
                currentPage = page)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val balloonsQueue = BalloonsQueue()
    FullScreenPaddedColumn {
        Header("Home")
//        GreenBannersGallery(navController)
        IntroTooltip(
            id = "practice",
            dependencies = arrayListOf("learn", "materials"),
            text = "Apply your knowledge here.",
            queue = balloonsQueue
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                PracticeTypeButton(
                    "Practice",
                    R.drawable.button_bg_practice,
                    Modifier.weight(1f),
                    onClick = { navController.navigate("practice") }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IntroTooltip(
                id = "learn",
                text = "Learn more here.",
                queue = balloonsQueue,
                Modifier.weight(1f),
            ) {
                PracticeTypeButton(
                    "Learn",
                    R.drawable.button_bg_lessons,
                    Modifier.weight(1f),
                    onClick = { navController.navigate("learning") }
                )
            }
            IntroTooltip(
                id = "materials",
                text = "Check out the recommended materials section.",
                queue = balloonsQueue,
                Modifier.weight(1f),
            ) {
                PracticeTypeButton(
                    "Materials",
                    R.drawable.button_bg_materials,
                    Modifier.weight(1f),
                    onClick = { navController.navigate("materials") }
                )
            }
        }
    }
    Dock(ButtonType.HOME, navController)
}