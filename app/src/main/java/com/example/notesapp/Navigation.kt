package com.example.notesapp


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val list = remember {
        mutableStateListOf(
            Note(title = "Monday",content = "Shopping"),
            Note(title = "Tuesday",content = "Playing basketball"),
            Note(title = "Wednesday",content = "Hiking"),
            Note(title = "Thursday",content = "Swimming")
        )
    }
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(list = list, navController = navController)
        }
        composable(
            route = Screen.DetailScreen.route + "/{noteIndex}",
            arguments = listOf(navArgument("noteIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteIndex = backStackEntry.arguments?.getInt("noteIndex")
            if (noteIndex != null && noteIndex >= 0 && noteIndex < list.size) {
                val noteToShow = list[noteIndex]
                DetailScreen(noteToShow, navController = navController)
            }
        }
        composable(
            route = Screen.CreateNoteScreen.route
        ) {
            CreateNoteScreen(list = list, navController = navController)
        }
        composable(
            route = Screen.EditNoteScreen.route + "/{noteIndex}",
            arguments = listOf(navArgument("noteIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteIndex = backStackEntry.arguments?.getInt("noteIndex")
            if (noteIndex != null) {
                val noteToEdit = list.getOrNull(noteIndex)
                if (noteToEdit != null) {
                    EditNoteScreen(note = noteToEdit) { title, content, validationResult ->
                        // Handle saving the edited note here
                        // You can use noteIndex to update the list if needed
                        if (validationResult.isTitleValid && validationResult.isContentLengthValid) {
                            noteToEdit.title = title
                            noteToEdit.content = content
                        }
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}





