package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.Country
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.TransliterationExerciseStatesRepository
import com.tgarbus.litiluism.data.countryToName
import com.tgarbus.litiluism.data.maybeCountryFlagResource
import com.tgarbus.litiluism.ui.getThumbnailResourceId

@Composable
fun TransliterationExercisesListItem(
    exercise: TransliterationExercise,
    navController: NavController
) {
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
    Box(modifier = Modifier
        .fillMaxWidth()
        .shadow(
            elevation = 1.dp, shape = RoundedCornerShape(size = 21.dp)
        )
        .background(
            color = colorResource(id = R.color.white),
            shape = RoundedCornerShape(size = 21.dp)
        )
        // TODO: add ripple effect
        .clickable { navController.navigate("exercise/${exercise.id}") }
        .padding(vertical = 10.dp, horizontal = 10.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(2f)
            ) {
                Image(
                    painter = painterResource(
                        id = getThumbnailResourceId(
                            exercise.imgResourceName,
                            LocalContext.current
                        )
                    ),
                    contentDescription = exercise.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(CircleShape)
                )
                if (exercise.country != Country.ANY) {
                    Image(
                        painter = painterResource(
                            maybeCountryFlagResource(exercise.country)!!
                        ),
                        countryToName(exercise.country),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .requiredSize(24.dp)
                            .clip(CircleShape)
                            .padding(0.dp)
                    )
                }
            }
            Column(
                modifier = Modifier.weight(5f),
                verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            ) {
                Text(
                    text = exercise.title,
                    fontFamily = sarabunFontFamily,
                    fontWeight = FontWeight(700),
                    fontSize = 16.sp,
                )
                Text(
                    text = exercise.runeRow.name,
                    fontFamily = sarabunFontFamily,
                    fontWeight = FontWeight(700),
                    fontSize = 16.sp,
                    maxLines = 1,
                    color = colorResource(R.color.rune_row_type_text),
                )
                Text(
                    text = exercise.runes,
                    fontFamily = sarabunFontFamily,
                    fontWeight = FontWeight(300),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                // TODO: move this to a ViewModel
                if (TransliterationExerciseStatesRepository.getInstance()
                        .getExerciseState(exercise.id).complete
                ) {
                    Box(
                        Modifier
                            .border(
                                width = 1.dp,
                                color = colorResource(R.color.primary),
                                shape = RoundedCornerShape(size = 20.dp)
                            )
                            .padding(start = 10.dp, end = 10.dp)
                    ) {
                        Text(
                            text = "done",
                            fontFamily = sarabunFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight(600),
                            color = colorResource(R.color.primary)
                        )
                    }
                }
            }
        }
    }
}
