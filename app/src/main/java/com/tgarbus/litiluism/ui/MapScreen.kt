package com.tgarbus.litiluism.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.Location
import com.tgarbus.litiluism.ui.reusables.ChoiceDialog
import com.tgarbus.litiluism.ui.reusables.Header
import com.tgarbus.litiluism.ui.reusables.MapPreview
import com.tgarbus.litiluism.ui.reusables.TransliterationExercisesListItem
import com.tgarbus.litiluism.viewmodel.MapScreenStateViewModel

@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapScreenStateViewModel = viewModel()
) {
    val showLocationDialog = remember { mutableStateOf<Location?>(null) }
    // TODO: Extract common column to a custom composable
    Column(
        modifier = Modifier
            .fillMaxWidth()
            // TODO: set bg color through theme
            .background(colorResource(R.color.light_bg))
            .padding(vertical = 20.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
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