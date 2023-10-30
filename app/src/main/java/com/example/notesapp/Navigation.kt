package com.example.notesapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.Navigation
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
            route = Screen.DetailScreen.route
        ) {
            DetailScreen(navController = navController)
        }

        composable(
            route = Screen.CreatNoteScreen.route
        ) {
            CreatNoteScreen(list = list, navController = navController)
        }
    }
}

@Composable
fun MainScreen(list: MutableList<Note>, navController: NavController) {
    var editingNote: Note? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Button(
            onClick = {
                navController.navigate(Screen.CreatNoteScreen.route)
            },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(text = "Create Notes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        ListView(list = list, onEditClick = { note ->
            editingNote = note})

        Spacer(modifier = Modifier.height(16.dp))

        if (editingNote != null) {
            val noteIndex = list.indexOf(editingNote!!)
            EditNoteView(
                list = list,
                noteIndex = noteIndex,
                onSaveClick = { title, content ->
                    // Update the existing note with the new values
                    list[noteIndex].title = title
                    list[noteIndex].content = content
                    editingNote = null
                }
            )
        }
        Button(
            onClick = {
                navController.navigate(Screen.DetailScreen.route)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Detail Screen")
        }

    }
}

@Composable
fun DetailScreen(modifier: Modifier = Modifier, navController: NavController) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Back to Main Screen")
        }
    }
}

@Composable
fun CreatNoteScreen(list: MutableList<Note>, modifier: Modifier = Modifier, navController: NavController) {
    
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TextInputView(list = list)

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Back to Main Screen")
        }

    }
}




