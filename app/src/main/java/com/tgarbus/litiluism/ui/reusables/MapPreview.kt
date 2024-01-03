package com.tgarbus.litiluism.ui.reusables

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.viewmodel.MapPreviewViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

@Composable
fun MapPreview(
    navController: NavController,
    viewModel: MapPreviewViewModel = viewModel()
) {
    // Adds view to Compose
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.map_preview, null)
            val mapView = view.findViewById<MapView>(R.id.map)
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            for (exercise in viewModel.getExercisesWithLocations()) {
                val exerciseMarker = Marker(mapView)
                exerciseMarker.position = GeoPoint(
                    exercise.location!!.lat,
                    exercise.location.long
                )
                exerciseMarker.setOnMarkerClickListener { _, _ ->
                    navController.navigate(
                        "exercise/${exercise.id}"
                    )
                    true
                }
//                exerciseMarker.icon =
//                    context.getDrawable(R.drawable.logotype_litiluism_square_no_text)
                exerciseMarker.title = exercise.title
                mapView.overlays.add(exerciseMarker)
                mapView.setMultiTouchControls(true)
                Log.d("debug", exercise.toString())
            }
            view
        },
        update = { view ->
            // View's been inflated or state read in this block has been updated
            // Add logic here if necessary.
            val mapView = view.findViewById<MapView>(R.id.map)
            mapView.controller.animateTo(GeoPoint(60.0, -15.0))
//            mapView.controller.animateTo(GeoPoint(59.25712104244483, 17.21712304851343))
            mapView.controller.zoomTo(4.0)
            Log.d("mapview", "update")
        }
    )
}
