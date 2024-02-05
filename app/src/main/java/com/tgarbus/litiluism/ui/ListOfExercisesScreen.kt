package com.tgarbus.litiluism.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.BaseRuneRow
import com.tgarbus.litiluism.data.Country
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.baseRuneRowToString
import com.tgarbus.litiluism.data.countryToName
import com.tgarbus.litiluism.data.maybeCountryFlagResource
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.BackButton
import com.tgarbus.litiluism.ui.reusables.BalloonsQueue
import com.tgarbus.litiluism.ui.reusables.FiltersSection
import com.tgarbus.litiluism.ui.reusables.FiltersToggle
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header
import com.tgarbus.litiluism.ui.reusables.IntroTooltip
import com.tgarbus.litiluism.ui.reusables.TransliterationExercisesListItem
import com.tgarbus.litiluism.viewmodel.ListOfExercisesViewModel

data class ExerciseFilters(
    val countries: List<Country> = Country.entries,
    val runeRows: List<BaseRuneRow> = BaseRuneRow.entries,
    val activeCountry: Country = Country.ANY,
    val activeRuneRow: BaseRuneRow = BaseRuneRow.ANY
)

@Composable
fun CountryButtonContent(country: Country, exercisesByCountryCount: HashMap<Country, Int>) {
    val buttonText = countryToName(country)
    val flagResource = maybeCountryFlagResource(country)
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
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(buttonText)
                    }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append(countriesCountText)
                    }
                }
            }
        )
    }
}

@Composable
fun Filters(
    filters: MutableState<ExerciseFilters>,
    exercisesByCountryCount: HashMap<Country, Int>,
) {
    Column {
        FiltersSection(
            values = filters.value.countries,
            activeValue = filters.value.activeCountry,
            onValueChange = { c -> filters.value = filters.value.copy(activeCountry = c) },
            categoryName = "Countries",
        ) {
            CountryButtonContent(it, exercisesByCountryCount)
        }
        FiltersSection(
            values = filters.value.runeRows,
            activeValue = filters.value.activeRuneRow,
            onValueChange = { r -> filters.value = filters.value.copy(activeRuneRow = r) },
            categoryName = "Runic alphabets"
        ) {
            Text(
                text = baseRuneRowToString(it),
                fontFamily = sarabunFontFamily
            )
        }
    }
}

fun showCountry(country: Country, filters: ExerciseFilters): Boolean {
    return country == filters.activeCountry || filters.activeCountry == Country.ANY
}

fun showRuneRow(baseRuneRow: BaseRuneRow, filters: ExerciseFilters): Boolean {
    return baseRuneRow == filters.activeRuneRow || filters.activeRuneRow == BaseRuneRow.ANY
}

fun showExercise(exercise: TransliterationExercise, filters: ExerciseFilters): Boolean {
    return showCountry(exercise.country, filters) && showRuneRow(
        exercise.runeRow.baseRuneRow,
        filters
    )
}

@Composable
fun ListOfExercisesScreen(
    navController: NavController,
    viewModel: ListOfExercisesViewModel = viewModel()
) {
    val transliterationExercises = viewModel.transliterationExercises()
    val balloonsQueue = BalloonsQueue()

    val filters = remember { mutableStateOf(ExerciseFilters()) }
    val showFiltersDialog = remember { mutableStateOf(false) }

    FullScreenPaddedColumn {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BackButton(navController)
            Header(
                "Transliteration exercises",
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth()
                    .weight(2f)
            )
            IntroTooltip(id = "filter_exercises", text = "Click here to filter exercises.", queue = balloonsQueue) {
                FiltersToggle(showFiltersDialog)
            }
        }
        AnimatedVisibility(
            visible = showFiltersDialog.value,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Filters(
                filters,
                viewModel.exercisesByCountryCount(),
            )
        }
        for (exercise in transliterationExercises) {
            AnimatedVisibility(
                visible = showExercise(exercise, filters.value),
                enter = slideInHorizontally(initialOffsetX = { - 2 * it }),
                exit = slideOutHorizontally(targetOffsetX = { 2 * it })
            ) {
                TransliterationExercisesListItem(
                    exercise,
                    navController
                )
            }
        }
    }
}