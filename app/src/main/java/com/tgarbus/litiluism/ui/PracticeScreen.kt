package com.tgarbus.litiluism.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.maybeBaseRuneRowToId
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header
import com.tgarbus.litiluism.ui.reusables.ListOfRuneRowsDialog
import com.tgarbus.litiluism.ui.reusables.MapPreview

@Composable
fun PracticeTypeButtonText(
    name: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(10.dp)
            .background(
                color = colorResource(R.color.primary),
                shape = RoundedCornerShape(27.dp)
            )
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name.uppercase(),
                fontFamily = sarabunFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.white),
            )
            Image(
                painter = painterResource(R.drawable.icon_forward),
                name,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Composable
fun PracticeTypeButton(
    name: String,
    bgResource: Int,
    boxModifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = boxModifier
            .shadow(
                elevation = 1.dp, shape = RoundedCornerShape(size = 21.dp)
            )
            .fillMaxSize()
            .clip(RoundedCornerShape(size = 21.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(bgResource),
            name,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        PracticeTypeButtonText(name)
    }
}

@Composable
fun PracticeScreen(navController: NavController) {
    var runeRowDialogDestination = remember { mutableStateOf<String?>(null) }
    Box(modifier = Modifier.fillMaxSize()) {
        FullScreenPaddedColumn() {
            Header("Practice")
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .shadow(
                        elevation = 1.dp, shape = RoundedCornerShape(size = 21.dp)
                    )
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(21.dp))
                    .clickable { navController.navigate("mapscreen") }
            ) {
                MapPreview(navController, onLocationClick = {})
                PracticeTypeButtonText(
                    name = "Pick from map",
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate("mapscreen") }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PracticeTypeButton(
                    "Rune to Latin",
                    R.drawable.button_bg_rune_to_latin,
                    Modifier.weight(1f),
                    onClick = { runeRowDialogDestination.value = "runetolatin" }
                )
                PracticeTypeButton(
                    "Latin to rune",
                    R.drawable.button_bg_latin_to_rune,
                    Modifier.weight(1f),
                    onClick = { runeRowDialogDestination.value = "latintorune" }
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                PracticeTypeButton(
                    "Text transliteration",
                    R.drawable.button_bg_transliteration_exercises,
                    onClick = { navController.navigate("exerciseslist") }
                )
            }
        }
        Dock(ButtonType.PRACTICE, navController)
        ListOfRuneRowsDialog(
            visible = runeRowDialogDestination.value != null,
            onClose = { runeRowDialogDestination.value = null },
            title = "Select runic alphabet",
            onSelectItem = { baseRuneRow ->
                val destination = runeRowDialogDestination.value
                if (destination != null) {
                    navController.navigate(
                        "${destination}/${maybeBaseRuneRowToId(baseRuneRow)!!}"
                    )
                }
            })
    }
}