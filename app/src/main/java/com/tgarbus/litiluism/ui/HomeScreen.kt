package com.tgarbus.litiluism.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.SemiCircularProgressBar
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header
import com.tgarbus.litiluism.ui.reusables.PracticeTypeButton
import com.tgarbus.litiluism.ui.reusables.PracticeTypeButtonText
import com.tgarbus.litiluism.ui.reusables.StatisticsLineChart

@Composable
fun GreenBanner(navController: NavController) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(
            painterResource(R.drawable.banner_home),
            "banner",
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Text(
                "Hej! How about taking a peek into Historiska Museet in Stockholm?",
                fontFamily = sarabunFontFamily,
                color = colorResource(R.color.primary),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
            )
            PracticeTypeButtonText(
                "Take me there!",
                modifier = Modifier.clickable { navController.navigate("mapscreen/historiska") })
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    FullScreenPaddedColumn {
        Header("Litiluism")
//        GreenBanner(navController)
        Row(modifier = Modifier.fillMaxWidth()) {
            PracticeTypeButton(
                "Practice",
                R.drawable.button_bg_practice,
                onClick = { navController.navigate("practice") }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PracticeTypeButton(
                "Learn",
                R.drawable.button_bg_lessons,
                Modifier.weight(1f),
                onClick = { navController.navigate("learning") }
            )
            PracticeTypeButton(
                "Materials",
                R.drawable.button_bg_materials,
                Modifier.weight(1f),
                onClick = { navController.navigate("materials") }
            )
        }
    }
    Dock(ButtonType.HOME, navController)
}