package com.tgarbus.litiluism.ui.reusables

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.Location
import com.tgarbus.litiluism.viewmodel.MapPreviewViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

@Composable
fun MapPreview(
    navController: NavController,
    viewModel: MapPreviewViewModel = viewModel(),
    onLocationClick: (Location) -> Unit,
) {
    // Adds view to Compose
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.map_preview, null)
            val mapView = view.findViewById<MapView>(R.id.map)
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            for (location in viewModel.getLocations()) {
                val locationMarker = Marker(mapView)
                // TODO: Use Litiluism icon for the markers
                locationMarker.position = GeoPoint(
                    location.lat,
                    location.long
                )
                locationMarker.setOnMarkerClickListener { _, _ ->
                    onLocationClick(location)
                    true
                }
                locationMarker.title = location.description
                mapView.overlays.add(locationMarker)
            }
            mapView.setMultiTouchControls(true)
            mapView.minZoomLevel = 4.0
            view
        },
        update = { view ->
            // View's been inflated or state read in this block has been updated
            // Add logic here if necessary.
            val mapView = view.findViewById<MapView>(R.id.map)
            mapView.controller.animateTo(GeoPoint(60.0, -15.0))
            mapView.controller.zoomTo(4.0)
            Log.d("mapview", "update")
        }
    )
}
