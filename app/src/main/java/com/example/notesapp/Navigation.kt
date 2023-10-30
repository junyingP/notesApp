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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun MainScreen(list: MutableList<Note>, navController: NavController) {
    var editingNote: Note? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Button(
            onClick = {
                navController.navigate(Screen.CreateNoteScreen.route)
            },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(text = "Create Notes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        ListView(list = list, onEditClick = { note ->
            editingNote = note
            navController.navigate(Screen.EditNoteScreen.route + "/${list.indexOf(note)}")
        })

        Spacer(modifier = Modifier.height(16.dp))

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
fun CreateNoteScreen(list: MutableList<Note>, modifier: Modifier = Modifier, navController: NavController) {
    
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

@Composable
fun EditNoteScreen(
    note: Note,
    onSaveClick: (String, String, ValidationResults) -> Unit
) {
    var titleText by rememberSaveable { mutableStateOf(note.title) }
    var contentText by rememberSaveable { mutableStateOf(note.content) }
    var validationResults by remember { mutableStateOf(ValidationResults()) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = titleText,
            onValueChange = { titleText = it },
            label = { Text("Title") }
        )
        if (!validationResults.isTitleValid) {
            Text(
                text = validationResults.titleErrorMessage,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
        OutlinedTextField(
            value = contentText,
            onValueChange = { contentText = it },
            label = { Text("Content") }
        )
        if (!validationResults.isContentLengthValid) {
            Text(
                text = validationResults.contentErrorMessage,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
        Button(
            onClick = {
                val validationResult = validateNoteInput(titleText, contentText)
                if (validationResult.isTitleValid && validationResult.isContentLengthValid) {
                    onSaveClick(titleText, contentText, validationResult)
                } else {
                    // Display validation error messages
                    validationResults = validationResult
                }
            }
        ) {
            Text("Save")
        }
    }
}





