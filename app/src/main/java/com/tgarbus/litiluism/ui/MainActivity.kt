package com.tgarbus.litiluism.ui

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.StaticContentRepository
import org.osmdroid.config.Configuration


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ctx = applicationContext
        Configuration.getInstance().load(ctx, getPreferences(Context.MODE_PRIVATE))
        StaticContentRepository.init(this)

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme().copy(
                    primary = colorResource(R.color.primary),
                    secondary = colorResource(R.color.secondary),
                    background = colorResource(R.color.light_bg),
                    surface = colorResource(R.color.light_bg),
                ),
            ) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "practice") {
                    composable("practice") {
                        PracticeScreen(navController)
                    }
                    composable("exercise/{exerciseId}") { backStackEntry ->
                        ExerciseScreenFromId(
                            backStackEntry.arguments!!.getString("exerciseId")!!,
                            navController
                        )
                    }
                    composable("exerciseslist") {
                        ListOfExercisesScreen(
                            navController
                        )
                    }
                    composable("afterexercise") {
                        AfterExerciseScreen(navController)
                    }
                    composable("mapscreen") {
                        MapScreen(navController)
                    }
                    composable("runetolatin/{runeRowId}") {
                        RuneToLatinExerciseScreen(navController)
                    }
                }
            }
        }

        var permissions = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermissions(permissions, 1)
    }
}
