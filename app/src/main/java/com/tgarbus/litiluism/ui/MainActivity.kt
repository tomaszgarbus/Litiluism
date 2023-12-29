package com.tgarbus.litiluism.ui

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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme().copy(
                    primary = colorResource(R.color.primary),
                    secondary = colorResource(R.color.secondary),
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