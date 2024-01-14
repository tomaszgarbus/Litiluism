package com.tgarbus.litiluism.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.Location
import com.tgarbus.litiluism.ui.reusables.ChoiceDialog
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: Extract to a common component
            IconButton(onClick = { navController.navigate("practice") }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_backarrow),
                    contentDescription = "back",
                    tint = colorResource(R.color.secondary)
                )
            }
            Header("Pick from map")
        }
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