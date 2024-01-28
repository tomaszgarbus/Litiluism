package com.tgarbus.litiluism.ui

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.StaticContentRepository
import org.osmdroid.config.Configuration


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

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
                NavHost(navController = navController,
                    startDestination = "homeorabout",
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None },
                    modifier = Modifier.fillMaxSize()) {
                    composable("homeorabout") {
                        HomeOrAboutScreen(navController)
                    }
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
                    composable("afterexercise/{correct}/{total}",
                        arguments = listOf(
                            navArgument("correct") { type = NavType.IntType },
                            navArgument("total") { type = NavType.IntType }
                        )
                    ) {
                        AfterExerciseScreen(navController)
                    }
                    composable("mapscreen") {
                        MapScreen(navController)
                    }
                    composable("mapscreen/{locationId}") {
                        MapScreen(navController)
                    }
                    composable("runetolatin/{runeRowId}") {
                        RuneToLatinExerciseScreen(navController)
                    }
                    composable("latintorune/{runeRowId}") {
                        LatinToRuneExerciseScreen(navController)
                    }
                    composable("learning") {
                        LessonsScreen(navController)
                    }
                    composable("lesson/{lessonNumber}",
                        arguments = listOf(
                            navArgument("lessonNumber") { type = NavType.IntType }
                        )) {
                        LessonScreen(navController)
                    }
                    composable("home") {
                        HomeScreen(navController)
                    }
                    composable("about") {
                        AboutScreen(navController)
                    }
                    composable("settings") {
                        SettingsScreen(navController)
                    }
                    composable("materials") {
                        MaterialsScreen(navController)
                    }
                    composable("statistics") {
                        StatisticsScreen(navController)
                    }
                }
            }
        }

        var permissions = arrayOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        requestPermissions(permissions, 1)
    }
}
