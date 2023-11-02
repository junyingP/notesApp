package com.example.notesapp


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@OptIn(ExperimentalMaterial3Api::class)
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
    var showBackButton by rememberSaveable {
        mutableStateOf(false)
    }
    var topBarTitle by rememberSaveable {
        mutableStateOf("Home")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                Text(text = topBarTitle) },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    } else {
                        null
                    }
                }
            )
        }
    ) {innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
                composable(route = Screen.MainScreen.route) {
                    showBackButton = false
                    topBarTitle = "Home"
                    MainScreen(list = list, navController = navController)
                }
                composable(
                    route = Screen.DetailScreen.route + "/{noteIndex}",
                    arguments = listOf(navArgument("noteIndex") { type = NavType.IntType })
                ) { backStackEntry ->
                    showBackButton = true
                    topBarTitle = "Detail"
                    val noteIndex = backStackEntry.arguments?.getInt("noteIndex")
                    if (noteIndex != null && noteIndex >= 0 && noteIndex < list.size) {
                        val noteToShow = list[noteIndex]
                        DetailScreen(noteToShow)
                    }
                }
                composable(
                    route = Screen.CreateNoteScreen.route
                ) {
                    showBackButton = true
                    topBarTitle = "Create Notes"
                    CreateNoteScreen(list = list)
                }
                composable(
                    route = Screen.EditNoteScreen.route + "/{noteIndex}",
                    arguments = listOf(navArgument("noteIndex") { type = NavType.IntType })
                ) { backStackEntry ->
                    showBackButton = true
                    topBarTitle = "Edit Notes"
                    val noteIndex = backStackEntry.arguments?.getInt("noteIndex")
                    if (noteIndex != null) {
                        val noteToEdit = list.getOrNull(noteIndex)
                        if (noteToEdit != null) {
                            EditNoteScreen(note = noteToEdit) { title, content, validationResult ->
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
    }
}
