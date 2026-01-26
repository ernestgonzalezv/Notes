package com.egcoding.notes.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.egcoding.notes.presentation.ui.screens.edit.AddEditNoteScreen
import com.egcoding.notes.presentation.ui.screens.main.NotesScreen


@Composable
fun NavigationStack(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        }
    ) {
        composable(route = Screen.NotesScreen.route) {
            NotesScreen(navController = navController)
        }

        composable(
            route = Screen.AddEditNoteScreen.route +
                    "?${Screen.PARAM_NOTE_ID}={${Screen.PARAM_NOTE_ID}}&" +
                    "${Screen.PARAM_NOTE_COLOR}={${Screen.PARAM_NOTE_COLOR}}",
            arguments = listOf(
                navArgument(Screen.PARAM_NOTE_ID) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(Screen.PARAM_NOTE_COLOR) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val color = backStackEntry.arguments?.getInt(Screen.PARAM_NOTE_COLOR) ?: -1
            AddEditNoteScreen(
                navController = navController,
                noteColor = color
            )
        }
    }
}