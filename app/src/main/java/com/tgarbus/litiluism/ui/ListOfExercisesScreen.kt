package com.tgarbus.litiluism.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.data.Country
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.countryToName
import com.tgarbus.litiluism.data.StaticContentRepository
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
fun FiltersSection(filters: MutableState<ExerciseFilters>, onDismissRequest: () -> Unit) {
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
                // TODO: Deduplicate by extracting button to a custom component.
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
                                    .padding(0.dp)
                            )
                        }
                        Text(buttonText, fontFamily = sarabunFontFamily)
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
    navController: NavController
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
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.backarrow_icon),
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
            )
            FilledIconButton(
                onClick = { showFiltersDialog.value = !showFiltersDialog.value },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = colorResource(R.color.secondary),
                    contentColor = colorResource(R.color.white)
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = "filters",
                )
            }
        }
        AnimatedVisibility(visible = showFiltersDialog.value) {
            FiltersSection(filters) { showFiltersDialog.value = false }
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
                        Column(
                            modifier = Modifier.weight(5f)
                        ) {
                            Text(
                                text = exercise.title,
                                fontFamily = sarabunFontFamily,
                                fontWeight = FontWeight(700),
                                fontSize = 20.sp
                            )
                            Text(
                                text = exercise.runeRow.name,
                                fontFamily = sarabunFontFamily,
                                fontWeight = FontWeight(400),
                                fontSize = 16.sp
                            )
                            Text(
                                text = exercise.runes,
                                fontFamily = sarabunFontFamily,
                                fontWeight = FontWeight(300),
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
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
                                .clip(AbsoluteRoundedCornerShape(21.dp))
                                .weight(2f)
                        )
                    }
                }
            }
        }
    }
}