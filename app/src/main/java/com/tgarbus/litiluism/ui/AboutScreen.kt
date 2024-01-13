package com.tgarbus.litiluism.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.colorResource
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
import kotlin.math.absoluteValue
import kotlin.math.max


@Composable
fun Page1() {
    Column() {
        Text(
            text = buildAnnotatedString {
                append("What ")
                withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                    append("is")
                }
                append(" Litiluism?")
            },
            fontSize = 20.sp,
            fontFamily = Fonts.sarabunFontFamily,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.primary),
        )
    }
}

@Composable
fun Page2() {
    Column() {
        Text(
            text = buildAnnotatedString {
                append("While it sounds like an English word, it is in fact a ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("transliteration")
                }
                append(" of a runic inscription found in Oseberg, Norway:")
            },
            fontSize = 20.sp,
            fontFamily = Fonts.sarabunFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dark_grey),
            textAlign = TextAlign.Center
        )
        Text(
            text = "ᛚᛁᛏᛁᛚᚢᛁᛌᛙ",
            fontSize = 20.sp,
            fontFamily = Fonts.sarabunFontFamily,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.dark_grey),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AboutView() {
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
        val pagerState = rememberPagerState(pageCount = {
            3
        })
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp),
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
                    .fillMaxWidth()
                    .scale(max(0.75, 1.0 - distanceFromCurrentPage).toFloat())
                    .alpha(max(0.5, 1.0 - distanceFromCurrentPage).toFloat())
                    .clip(RoundedCornerShape(28.dp))
                    .background(colorResource(R.color.white))
                    .padding(28.dp)
            ) {
                when (page) {
                    0 -> Page1()
                    1 -> Page2()
                    2 -> Page1()
                }
            }
        }
    }
}

@Composable
fun AboutScreen(navController: NavController) {
    AboutView()
}