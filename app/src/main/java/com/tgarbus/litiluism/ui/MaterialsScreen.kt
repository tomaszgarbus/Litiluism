package com.tgarbus.litiluism.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header

@Composable
fun SectionHeader(text: String) {
    Text(
        text,
        fontFamily = sarabunFontFamily,
        fontWeight = FontWeight.Bold,
        color = colorResource(R.color.primary),
        fontSize = 20.sp,
    )
}

@Composable
fun SubsectionHeader(text: String) {
    Text(
        text,
        fontFamily = sarabunFontFamily,
        fontWeight = FontWeight.Bold,
        color = colorResource(R.color.secondary),
        fontSize = 18.sp,
    )
}

@Composable
fun NormalText(text: String) {
    Text(
        text,
        fontFamily = sarabunFontFamily,
        fontWeight = FontWeight.Normal,
        color = colorResource(R.color.dark_grey),
        fontSize = 16.sp
    )
}

@Composable
fun MaterialsScreen(navController: NavController) {
    FullScreenPaddedColumn {
        Header("Materials")
        Text("Recommended materials about runes and related topics:")
        SectionHeader("Books")
        SubsectionHeader("Runes")
        NormalText("\"Runes\" by Michael P. Barnes")
        NormalText("\"Runes\" by Martin Findell")
        SubsectionHeader("Norse mythology")
        NormalText("\"The prose edda\" by Snorri Sturluson")
        NormalText("\"The poetic edda\"")
        NormalText("\"Saga of the Volsungs")
        SubsectionHeader("Scandinavian history")
        NormalText("\"Outbreak of the Viking Age\" by Torgrim Titlestad")
        SectionHeader("Podcasts")
        NormalText("\"Nordic Mythology Podcast\"")
    }
    Dock(ButtonType.MATERIALS, navController)
}