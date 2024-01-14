package com.tgarbus.litiluism.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.PrimaryButton
import kotlin.math.absoluteValue
import kotlin.math.max


@Composable
fun Page1() {
    Column {
        Text(
            text = "Welcome!",
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.primary),
        )
        Text(
            text = "First things first. You're probably wondering...",
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dark_grey),
        )
        Text(
            text = buildAnnotatedString {
                append("what ")
                withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                    append("is")
                }
                append(" Litiluism?")
            },
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.dark_grey),
        )
        Text(
            text = "Swipe left to find out.",
            fontSize = 16.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dim_grey_text),
        )
    }
}

@Composable
fun Page2() {
    Column {
        Text(
            text = "Litiluism",
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.primary),
        )
        Text(
            text = buildAnnotatedString {
                append("While it sounds like an English word, it is in fact a ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("transliteration")
                }
                append(" of a runic inscription found in Oseberg, Norway:")
            },
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dark_grey),
        )
        Text(
            text = "ᛚᛁᛏᛁᛚᚢᛁᛌᛙ",
            fontSize = 36.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Thin,
            color = colorResource(R.color.dark_grey),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun Page3() {
    Column {
        Text(
            text = "It is sometimes interpreted as ",
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dark_grey),
        )
        Text(
            text = "lítilvíss maðr - man knows little",
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = colorResource(R.color.primary),
        )
        Text(
            text = buildAnnotatedString {
                append("It may be a statement that human knowledge is limited, or perhaps some viking was just insulting their friend.")
            },
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dark_grey),
        )
    }
}

@Composable
fun Page4(navController: NavController) {
    Column {
        Text(
            text = buildAnnotatedString {
                append("Personally, we choose to take it as an invitation to learn and gain new knowledge.")
            },
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dark_grey),
        )
        Text(
            text = buildAnnotatedString {
                append("Join us on the journey through the history written in runes!")
            },
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.primary),
        )
        Spacer(modifier = Modifier.height(20.dp))
        PrimaryButton("Get started!") {
            navController.navigate("home")
        }
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int) {
    Row(
        Modifier
            .height(24.dp)
            .padding(start = 4.dp)
            .fillMaxWidth(0.5f),
        horizontalArrangement = Arrangement.Start
    ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPage == iteration) Color.White else Color.White.copy(alpha = 0.5f)
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(color)
                    .weight(1f)
                    .height(4.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AboutSliderCards(navController: NavController) {
    val pageCount = 4
    val pagerState = rememberPagerState(pageCount = { pageCount })
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(32.dp),
            beyondBoundsPageCount = 3,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            var distanceFromCurrentPage = (
                    (pagerState.currentPage - page) + pagerState
                        .currentPageOffsetFraction
                    ).absoluteValue
            // Our page content
            Box(
                modifier = Modifier
                    .scale(max(0.75, 1.0 - distanceFromCurrentPage).toFloat())
                    .alpha(max(0.5, 1.0 - distanceFromCurrentPage).toFloat())
                    .clip(RoundedCornerShape(28.dp))
                    .background(colorResource(R.color.white))
                    .padding(28.dp)
            ) {
                when (page) {
                    0 -> Page1()
                    1 -> Page2()
                    2 -> Page3()
                    3 -> Page4(navController)
                }
            }
        }
        PageIndicator(pageCount, currentPage = pagerState.currentPage)
    }
}

@Composable
fun AboutScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        colorResource(R.color.primary_gradient),
                        colorResource(R.color.primary),
                        colorResource(R.color.primary_gradient)
                    ),
                    tileMode = TileMode.Mirror
                )
            )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(R.drawable.banner_about_screen),
                "App logo")
            Text(
                "Litiluism",
                fontFamily = sarabunFontFamily,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = colorResource(R.color.white)
            )
            AboutSliderCards(navController)
        }
    }
}