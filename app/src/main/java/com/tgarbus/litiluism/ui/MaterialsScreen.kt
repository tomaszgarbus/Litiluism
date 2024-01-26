package com.tgarbus.litiluism.ui

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.Material
import com.tgarbus.litiluism.data.MaterialType
import com.tgarbus.litiluism.data.toDisplayableString
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
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
        description = "Very comprehensive introduction to runes.",
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
        name = "[Danish only] Article about Hamborggaardstenen",
        author = "De Nationale Geologiske Undersøgelser for Danmark og Grønland (GEUS)",
        description = "An article about Hamborggaardstenen. See also a lesson in the Learn section.",
        link = "https://www.geus.dk/udforsk-geologien/ture-i-naturen/kaempesten/hamborggaardsstenen/"
    )
)

@Composable
fun MaterialTypeMarker(type: MaterialType) {
    Box(
        Modifier
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
                Spacer(Modifier.width(10.dp))
                MaterialTypeMarker(material.type)
            }
            Text(
                text = material.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 18.72.sp,
                    fontFamily = sarabunFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.dark_grey),
                )
            )
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
            Text(
                text = material.description,
                maxLines = 3,
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

@Composable
fun MaterialsScreen(navController: NavController) {
    FullScreenPaddedColumn {
        Header("Materials")
        for (material in materials) {
            MaterialsListItem(material)
        }
    }
    Dock(ButtonType.MATERIALS, navController)
}