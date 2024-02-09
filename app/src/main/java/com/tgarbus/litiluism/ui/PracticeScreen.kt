package com.tgarbus.litiluism.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.maybeBaseRuneRowToId
import com.tgarbus.litiluism.ui.reusables.BalloonsQueue
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header
import com.tgarbus.litiluism.ui.reusables.IntroTooltip
import com.tgarbus.litiluism.ui.reusables.ListOfRuneRowsDialog
import com.tgarbus.litiluism.ui.reusables.MapPreview
import com.tgarbus.litiluism.ui.reusables.PracticeTypeButton
import com.tgarbus.litiluism.ui.reusables.PracticeTypeButtonText

enum class DestinationType {
    LATIN_TO_RUNE,
    RUNE_TO_LATIN
}

fun DestinationType.toNavigationString(): String {
    return when (this) {
        DestinationType.LATIN_TO_RUNE -> "latintorune"
        DestinationType.RUNE_TO_LATIN -> "runetolatin"
    }
}

fun DestinationType.toDisplayableString(): String {
    return when (this) {
        DestinationType.LATIN_TO_RUNE -> "Latin to rune"
        DestinationType.RUNE_TO_LATIN -> "Rune to latin"
    }
}

@Composable
fun PracticeScreen(navController: NavController) {
    var runeRowDialogDestination = remember { mutableStateOf<DestinationType?>(null) }
    Box(modifier = Modifier.fillMaxSize()) {
        FullScreenPaddedColumn {
            Header("Practice")
            val balloonsQueue = BalloonsQueue()
            IntroTooltip(
                id = "map",
                text = "Explore the runic objects on the map.",
                queue = balloonsQueue
            ) {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .shadow(
                            elevation = 2.dp, shape = RoundedCornerShape(size = 21.dp)
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
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                IntroTooltip(
                    id = "runetolatin",
                    text = "Practice transliteration, one rune at a time.",
                    queue = balloonsQueue,
                    Modifier.weight(1f)
                ) {
                    PracticeTypeButton(
                        "Rune to Latin",
                        R.drawable.button_bg_rune_to_latin,
                        Modifier.weight(1f),
                        onClick = { runeRowDialogDestination.value = DestinationType.RUNE_TO_LATIN }
                    )
                }
                IntroTooltip(
                    id = "latintorune",
                    text = "Practice writing in runes, one rune at a time.",
                    queue = balloonsQueue,
                    Modifier.weight(1f)
                ) {
                    PracticeTypeButton(
                        "Latin to rune",
                        R.drawable.button_bg_latin_to_rune,
                        Modifier.weight(1f),
                        onClick = { runeRowDialogDestination.value = DestinationType.LATIN_TO_RUNE }
                    )
                }
            }
            IntroTooltip(
                id = "transliteration",
                text = "Practice transliteration of full inscriptions!",
                queue = balloonsQueue,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    PracticeTypeButton(
                        "Text transliteration",
                        R.drawable.button_bg_transliteration_exercises,
                        onClick = { navController.navigate("exerciseslist") }
                    )
                }
            }
        }
        Dock(ButtonType.PRACTICE, navController)
        ListOfRuneRowsDialog(
            visible = runeRowDialogDestination.value != null,
            onClose = { runeRowDialogDestination.value = null },
            title = runeRowDialogDestination.value?.toDisplayableString() ?: "",
            onSelectItem = { baseRuneRow ->
                val destination = runeRowDialogDestination.value
                if (destination != null) {
                    navController.navigate(
                        "${destination.toNavigationString()}/${maybeBaseRuneRowToId(baseRuneRow)!!}"
                    )
                }
            })
    }
}