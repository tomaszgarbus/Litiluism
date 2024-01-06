package com.tgarbus.litiluism.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.data.Country
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.countryToName
import com.tgarbus.litiluism.data.BaseRuneRow
import com.tgarbus.litiluism.viewmodel.ListOfExercisesViewModel
import com.tgarbus.litiluism.data.TransliterationExercise
import com.tgarbus.litiluism.data.baseRuneRowToString
import com.tgarbus.litiluism.data.maybeCountryFlagResource
import com.tgarbus.litiluism.ui.reusables.Header
import com.tgarbus.litiluism.ui.reusables.TransliterationExercisesListItem

data class ExerciseFilters(
    val countries: List<Country> = Country.entries,
    val runeRows: List<BaseRuneRow> = BaseRuneRow.entries,
    val activeCountry: Country = Country.ANY,
    val activeRuneRow: BaseRuneRow = BaseRuneRow.ANY
)

@Composable
fun <T> FilterButton(
    buttonValue: T,
    activeValue: T,
    onClick: (T) -> Unit,
    content: @Composable () -> Unit
) {
    if (buttonValue == activeValue) {
        Button(
            onClick = { onClick(buttonValue) }, colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.secondary),
                contentColor = colorResource(R.color.white)
            )
        ) {
            content()
        }
    } else {
        OutlinedButton(
            modifier = Modifier.padding(0.dp),
            onClick = { onClick(buttonValue) },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = colorResource(R.color.white),
                contentColor = colorResource(R.color.secondary),
            )
        ) {
            content()
        }
    }
}

@Composable
fun <T> FiltersSection(
    values: List<T>,
    activeValue: T,
    onValueChange: (T) -> Unit,
    categoryName: String,
    valueDisplay: @Composable (T) -> Unit
) {
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
    val scrollState = rememberScrollState()
    Column() {
        Text(categoryName, fontFamily = sarabunFontFamily)
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            for (value in values) {
                FilterButton(
                    buttonValue = value,
                    onClick = { onValueChange(value) },
                    activeValue = activeValue
                ) {
                    valueDisplay(value)
                }
            }
        }
    }
}

@Composable
fun CountryButtonContent(country: Country, exercisesByCountryCount: HashMap<Country, Int>) {
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
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

@Composable
fun Filters(
    filters: MutableState<ExerciseFilters>,
    exercisesByCountryCount: HashMap<Country, Int>,
    viewModel: ListOfExercisesViewModel,
    onDismissRequest: () -> Unit
) {
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
    Column() {
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
            // TODO: Extract to a common component
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_backarrow),
                    contentDescription = "back",
                    tint = colorResource(R.color.secondary)
                )
            }
            Header(
                "Transliteration exercises",
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth()
                    .weight(2f)
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
            Filters(
                filters,
                viewModel.exercisesByCountryCount(),
                viewModel
            ) { showFiltersDialog.value = false }
        }
        for (exercise in transliterationExercises) {
            AnimatedVisibility(visible = showExercise(exercise, filters.value)) {
                TransliterationExercisesListItem(
                    exercise,
                    navController
                )
            }
        }
    }
}