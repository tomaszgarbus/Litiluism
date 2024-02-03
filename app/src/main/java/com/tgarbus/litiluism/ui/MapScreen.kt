package com.tgarbus.litiluism.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.ui.reusables.ChoiceDialog
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.HeaderWithBackButton
import com.tgarbus.litiluism.ui.reusables.MapPreview
import com.tgarbus.litiluism.ui.reusables.TransliterationExercisesListItem
import com.tgarbus.litiluism.viewmodel.MapScreenStateViewModel

@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapScreenStateViewModel = viewModel(),
) {
    val showLocationDialog = remember { mutableStateOf(viewModel.locationFromSavedStateHandle) }
    FullScreenPaddedColumn(scrollable = false) {
        HeaderWithBackButton("Pick from map", navController)
        Box(modifier = Modifier.clip(RoundedCornerShape(size = 21.dp))) {
            MapPreview(navController, onLocationClick = { l ->
                run {
                    showLocationDialog.value = l
                }
            })
            ChoiceDialog(
                title = showLocationDialog.value?.description.orEmpty(),
                visible = (showLocationDialog.value != null),
                onClose = { showLocationDialog.value = null }) {
                for (exercise in viewModel.exercisesByLocation.getOrDefault(
                    showLocationDialog.value, listOf()
                )) {
                    TransliterationExercisesListItem(exercise, navController)
                }
            }
        }
    }
}