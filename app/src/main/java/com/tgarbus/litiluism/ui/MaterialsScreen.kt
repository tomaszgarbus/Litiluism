package com.tgarbus.litiluism.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.Language
import com.tgarbus.litiluism.data.Material
import com.tgarbus.litiluism.data.MaterialType
import com.tgarbus.litiluism.data.toDisplayableString
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.FiltersSection
import com.tgarbus.litiluism.ui.reusables.FiltersToggle
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header

// TODO: put all this in an embedded file
// TODO: Add shows from DRTV and SVT
val materials = listOf(
    Material(
        id = "runes_barnes",
        type = MaterialType.BOOK,
        name = "Runes",
        author = "Michael P. Barnes",
        description = "This book provides an accessible, general account of runes and runic writing from their inception to their final demise.",
        link = "https://www.goodreads.com/book/show/14445363-runes",
    ),
    Material(
        id = "nmp",
        type = MaterialType.PODCAST,
        name = "Nordic Mythology Podcast",
        author = "Daniel Farrand and Mathias Nordvig",
        description = "Excellent podcast featuring guests from world-class scholars to celebrities.",
        link = "https://www.nordicmythologypodcast.com",
    ),
    Material(
        id = "runes_findell",
        type = MaterialType.BOOK,
        name = "Runes",
        author = "Martin Findell",
        description = "Short but information-packed book about runes.",
        link = "https://www.goodreads.com/book/show/22011888-runes"
    ),
    Material(
        id = "poetic_edda",
        type = MaterialType.BOOK,
        name = "The Poetic Edda",
        author = "",
        description = "Arguably the most comprehensive and reliable literary source about Norse myths. Translated from 13th century's manuscript, but the poems are dated centuries earlier.",
        link = "https://www.goodreads.com/book/show/123002139-the-poetic-edda"
    ),
    Material(
        id = "prose_edda",
        type = MaterialType.BOOK,
        name = "The Prose Edda",
        author = "Snorri Sturluson",
        description = "Renowned introduction to Norse mythology, written down by a famous Icelander Snorri Sturluson, a century after the Viking Age.",
        link = "https://www.goodreads.com/book/show/24658.The_Prose_Edda"
    ),
    Material(
        id = "volsungs_saga",
        type = MaterialType.BOOK,
        name = "Saga of the Volsungs",
        author = "",
        description = "One of the key texts about Norse heroes. Covers some of the same plot as the heroic part of The Prose Edda.",
        link = "https://www.goodreads.com/book/show/593109.The_Saga_of_the_Volsungs"
    ),
    Material(
        id = "outbreak_viking_age",
        type = MaterialType.BOOK,
        name = "Outbreak of the Viking Age",
        author = "Torgrim Titlestad",
        description = "Easy-read book about the viking history. Available in English and Norwegian.",
        link = "https://www.goodreads.com/book/show/40613026-outbreak-of-the-viking-age"
    ),
    Material(
        id = "hamborggaardstenen",
        type = MaterialType.ONLINE_ARTICLE,
        name = "Article about Hamborggaardstenen",
        author = "De Nationale Geologiske Undersøgelser for Danmark og Grønland (GEUS)",
        description = "An article about Hamborggaardstenen. See also a lesson in the Learn section.",
        link = "https://www.geus.dk/udforsk-geologien/ture-i-naturen/kaempesten/hamborggaardsstenen/",
        language = Language.DA
    )
)

fun maybeLanguageFlagResource(language: Language): Int? {
    return when (language) {
        Language.EN -> R.drawable.flag_gb
        Language.DA -> R.drawable.flag_dk
        else -> null
    }
}

@Composable
fun MaterialTypeMarker(type: MaterialType, modifier: Modifier = Modifier) {
    Box(
        modifier
            .border(
                width = 1.dp,
                color = colorResource(R.color.primary),
                shape = RoundedCornerShape(size = 20.dp)
            )
            .background(colorResource(R.color.primary), RoundedCornerShape(size = 20.dp))
            .padding(start = 10.dp, end = 10.dp)
    ) {
        Text(
            text = type.toDisplayableString(),
            fontFamily = sarabunFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun LanguageFlag(language: Language) {
    val flagResource = maybeLanguageFlagResource(language)
    if (flagResource != null) {
        Image(
            painterResource(flagResource),
            language.toDisplayableString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .requiredSize(18.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun MaterialsListItem(material: Material) {
    val context = LocalContext.current
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
            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(material.link)
            )
            context.startActivity(urlIntent)
        }
        .padding(vertical = 10.dp, horizontal = 10.dp)) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = material.name,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 18.72.sp,
                        fontFamily = sarabunFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.dark_grey),
                    ),
                    modifier = Modifier.weight(1f)
                )
                Row(
                    Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LanguageFlag(material.language)
                    Spacer(Modifier.width(5.dp))
                    MaterialTypeMarker(material.type)
                }
            }
            if (material.author.isNotEmpty()) {
                Text(
                    text = material.author,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 18.72.sp,
                        fontFamily = sarabunFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.dim_grey_text),
                    )
                )
            }
            Text(
                text = material.description,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 16.38.sp,
                    fontFamily = sarabunFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(R.color.dim_grey_text)
                ),
            )
        }
    }
}

data class MaterialFilters(
    val types: List<MaterialType> = MaterialType.entries,
    val languages: List<Language> = Language.entries,
    val activeType: MaterialType = MaterialType.ANY,
    val activeLanguage: Language = Language.ANY,
)

@Composable
fun Filters(
    filters: MutableState<MaterialFilters>,
) {
    Column {
        FiltersSection(
            values = filters.value.types,
            activeValue = filters.value.activeType,
            onValueChange = { t -> filters.value = filters.value.copy(activeType = t) },
            categoryName = "Material types"
        ) {
            Text(
                text = it.toDisplayableString(),
                fontFamily = sarabunFontFamily
            )
        }
        FiltersSection(
            values = filters.value.languages,
            activeValue = filters.value.activeLanguage,
            onValueChange = { l -> filters.value = filters.value.copy(activeLanguage = l) },
            categoryName = "Material types"
        ) {
            Text(
                text = it.toDisplayableString(),
                fontFamily = sarabunFontFamily
            )
        }
    }
}

fun showMaterialType(materialType: MaterialType, filters: MaterialFilters): Boolean {
    return materialType == filters.activeType || filters.activeType == MaterialType.ANY
}

fun showLanguage(language: Language, filters: MaterialFilters): Boolean {
    return language == filters.activeLanguage || filters.activeLanguage == Language.ANY
}

fun showMaterial(material: Material, filters: MaterialFilters): Boolean {
    return showMaterialType(material.type, filters) && showLanguage(material.language, filters)
}

@Composable
fun MaterialsScreen(navController: NavController) {
    val filters = remember { mutableStateOf(MaterialFilters()) }
    val showFiltersDialog = remember { mutableStateOf(false) }
    FullScreenPaddedColumn {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Header(
                "Materials", modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            )
            FiltersToggle(showFiltersDialog)
        }
        AnimatedVisibility(visible = showFiltersDialog.value) {
            Filters(filters)
        }
        for (material in materials) {
            AnimatedVisibility(visible = showMaterial(material, filters.value)) {
                MaterialsListItem(material)
            }
        }
    }
    Dock(ButtonType.MATERIALS, navController)
}