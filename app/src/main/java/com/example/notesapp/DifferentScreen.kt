package com.example.notesapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(list: MutableList<Note>, navController: NavController) {
    var editingNote: Note? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(Screen.CreateNoteScreen.route)
            },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(text = "Create Notes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        ListView(
            list = list,
            onEditClick = { note ->
                editingNote = note
                navController.navigate(Screen.EditNoteScreen.route + "/${list.indexOf(note)}")
            },
            onDetailClick = { note ->
                navController.navigate(Screen.DetailScreen.route + "/${list.indexOf(note)}")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun DetailScreen(note: Note, modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(text = note.title, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = note.content)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End)
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
