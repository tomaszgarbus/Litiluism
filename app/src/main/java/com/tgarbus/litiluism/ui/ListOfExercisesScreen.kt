package com.tgarbus.litiluism.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.data.Country
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.countryToName
import com.tgarbus.litiluism.data.ListOfExercisesStateViewModel
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.TransliterationExerciseStatesRepository
import com.tgarbus.litiluism.maybeCountryFlagResource

data class ExerciseFilters(
    val countries: List<Country> = Country.entries,
    val runeRows: List<String> = listOf("", "Younger Futhark"),
    val activeCountry: Country = Country.ANY,
    val activeRuneRow: String = ""
)

@Composable
fun CountryFilterButton(
    country: Country,
    activeCountry: Country,
    onClick: (Country) -> Unit,
    content: @Composable () -> Unit
) {
    if (country == activeCountry) {
        Button(
            onClick = { onClick(country) }, colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.secondary),
                contentColor = colorResource(R.color.white)
            )
        ) {
            content()
        }
    } else {
        OutlinedButton(
            modifier = Modifier.padding(0.dp),
            onClick = { onClick(country) },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = colorResource(R.color.white),
                contentColor = colorResource(R.color.secondary),
            )
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FiltersSection(
    filters: MutableState<ExerciseFilters>,
    exercisesByCountryCount: HashMap<Country, Int>,
    onDismissRequest: () -> Unit
) {
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
    Column {
        Text("Countries", fontFamily = sarabunFontFamily)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (country in filters.value.countries) {
                val onClick: ((Country) -> Unit) =
                    { c -> filters.value = filters.value.copy(activeCountry = c) }
                val buttonText = countryToName(country)
                val flagResource = maybeCountryFlagResource(country)
                CountryFilterButton(
                    country = country,
                    activeCountry = filters.value.activeCountry,
                    onClick = { onClick(country) }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (flagResource != null) {
                            Image(
                                painterResource(flagResource),
                                countryToName(country),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .requiredSize(24.dp)
                                    .clip(CircleShape)
                            )
                        }
                        val countriesCountText = "(${exercisesByCountryCount[country]})"
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(fontFamily = sarabunFontFamily)
                                ) {
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight(600)
                                        )
                                    ) {
                                        append(buttonText)
                                    }
                                    append(" ")
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight(400)
                                        )
                                    ) {
                                        append(countriesCountText)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

fun showCountry(country: Country, filters: ExerciseFilters): Boolean {
    return country == filters.activeCountry || filters.activeCountry == Country.ANY
}

@Composable
fun ListOfExercisesScreen(
    navController: NavController,
    viewModel: ListOfExercisesStateViewModel = viewModel()
) {
    val transliterationExercises = StaticContentRepository.getInstance().transliterationExercises
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
    val scrollState = rememberScrollState()

    val filters = remember { mutableStateOf(ExerciseFilters()) }
    val showFiltersDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(bottom = 10.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigate("practice") }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_backarrow),
                    contentDescription = "back",
                    tint = colorResource(R.color.secondary)
                )
            }
            Text(
                text = "Transliteration exercises",
                textAlign = TextAlign.Justify,
                color = colorResource(R.color.secondary),
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth()
                    .weight(2f),
                fontFamily = sarabunFontFamily,
                fontSize = 32.sp,
                fontWeight = FontWeight(700),
                lineHeight = 37.sp,
            )
            FilledIconButton(
                onClick = { showFiltersDialog.value = !showFiltersDialog.value },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = colorResource(R.color.secondary),
                    contentColor = colorResource(R.color.white)
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_filter),
                    contentDescription = "filters",
                )
            }
        }
        AnimatedVisibility(visible = showFiltersDialog.value) {
            FiltersSection(
                filters,
                StaticContentRepository.getInstance().exercisesByCountryCount
            ) { showFiltersDialog.value = false }
        }
        for (exercise in transliterationExercises) {
            AnimatedVisibility(visible = showCountry(exercise.country, filters.value)) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 1.dp, shape = RoundedCornerShape(size = 21.dp)
                    )
                    .background(
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(size = 21.dp)
                    )
                    .clickable {
                        navController.navigate("exercise/${exercise.id}")
                    }  // TODO: add ripple effect
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
                                contentDescription = "Image goes brr",
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
        }
    }
}