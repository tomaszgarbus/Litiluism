package com.tgarbus.litiluism.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.AboutRepository
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.CloseButton
import com.tgarbus.litiluism.ui.reusables.PageIndicator
import com.tgarbus.litiluism.ui.reusables.PrimaryButton
import com.tgarbus.litiluism.viewmodel.AboutViewModel
import kotlin.math.absoluteValue
import kotlin.math.max


@Composable
fun Page1() {
    Column {
        Text(
            text = LocalContext.current.getString(R.string.about_screen_welcome),
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.primary),
        )
        Text(
            text = LocalContext.current.getString(R.string.about_screen_first_things_first),
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dark_grey),
        )
        Text(
            text = buildAnnotatedString {
                append("${LocalContext.current.getString(R.string.about_screen_what)} ")
                withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                    append(LocalContext.current.getString(R.string.about_screen_is))
                }
                append(" Litiluism?")
            },
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.dark_grey),
        )
        Text(
            text = LocalContext.current.getString(R.string.about_screen_swipe_left),
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
                append(
                    LocalContext.current.getString(
                        R.string.about_screen_while_it_sounds
                    )
                )
                append(" ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(
                        LocalContext.current.getString(
                            R.string.about_screen_transliteraion
                        )
                    )
                }
                append(" ")
                append(
                    LocalContext.current.getString(
                        R.string.about_screen_of_a_runic_inscription
                    )
                )
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
            text = LocalContext.current.getString(
                R.string.about_screen_it_is_sometimes
            ),
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dark_grey),
        )
        Text(
            text = "lítilvíss maðr - ${
                LocalContext.current.getString(
                    R.string.about_screen_man_knows_little
                )
            }",
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = colorResource(R.color.primary),
        )
        Text(
            text = LocalContext.current.getString(
                R.string.about_screen_it_may_be_a_statement
            ),
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dark_grey),
        )
    }
}

@Composable
fun Page4(navController: NavController, alreadyCompleted: Boolean, onButtonClick: () -> Unit) {
    Column {
        Text(
            text = LocalContext.current.getString(
                R.string.about_screen_personally
            ),
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dark_grey),
        )
        Text(
            text = LocalContext.current.getString(
                R.string.about_screen_join_us
            ),
            fontSize = 20.sp,
            fontFamily = sarabunFontFamily,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.primary),
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (!alreadyCompleted) {
            PrimaryButton(
                LocalContext.current.getString(
                    R.string.about_screen_get_started
                )
            ) {
                onButtonClick()
                navController.navigate("practice")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AboutSliderCards(
    navController: NavController,
    viewModel: AboutViewModel = viewModel(),
    alreadyCompleted: Boolean
) {
    val pageCount = 4
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val context = LocalContext.current
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
                    .background(Color.White)
                    .padding(28.dp)
            ) {
                when (page) {
                    0 -> Page1()
                    1 -> Page2()
                    2 -> Page3()
                    3 -> Page4(navController, alreadyCompleted) { viewModel.markComplete(context) }
                }
            }
        }
        PageIndicator(pageCount, currentPage = pagerState.currentPage)
    }
}

@Composable
fun AboutScreen(navController: NavController) {
    val aboutRepo = AboutRepository()
    val alreadyCompleted = aboutRepo.getCompletedAsFlow(LocalContext.current).collectAsState(false)
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.safeDrawingPadding()
        ) {
            if (alreadyCompleted.value) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    CloseButton(
                        description = LocalContext.current.getString(
                            R.string.content_description_close
                        ),
                        brightTint = true
                    ) { navController.popBackStack() }
                }
            }
            Image(
                painterResource(R.drawable.banner_about_screen),
                contentDescription = LocalContext.current.getString(
                    R.string.content_description_app_logo
                )
            )
            Text(
                "Litiluism",
                fontFamily = sarabunFontFamily,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            AboutSliderCards(navController, alreadyCompleted = alreadyCompleted.value)
        }
    }
}