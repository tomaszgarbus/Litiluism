package com.tgarbus.litiluism

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val runeRowsMap: RuneRowsMap = FromJson.loadCanonicalRuneRows()
        val exercises: List<Exercise> = FromJson.loadExercises(runeRowsMap, this)
        val content = Content(exercises, runeRowsMap)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                MaterialTheme(
                    colorScheme = lightColorScheme().copy(
                        primary = colorResource(R.color.primary),
                        secondary = colorResource(R.color.secondary),
                    ),
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "exerciseslist") {
                        composable("exercise/{exerciseId}") { backStackEntry ->
                            ExerciseScreenFromId(
                                backStackEntry.arguments!!.getString("exerciseId")!!,
                                content,
                                navController
                            )
                        }
                        composable("exerciseslist") {
                            ListOfExercisesScreen(
                                exercises = exercises,
                                navController
                            )
                        }
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewExerciseView() {
//    val state =
//        remember { ExerciseState(inputs = "tula:lat:r", position = 10) }
//    ExerciseView(
//        ExerciseStateViewModel(state),
//        exampleExercise()
//    )
//}