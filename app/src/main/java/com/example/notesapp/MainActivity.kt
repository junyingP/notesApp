package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.ui.theme.NotesAppTheme
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun TextInputView(list: MutableList<Note>) {
    var titleText by rememberSaveable {
        mutableStateOf("")
    }
    var contentText by rememberSaveable {
        mutableStateOf("")
    }
    var validationResults by remember {
        mutableStateOf(ValidationResults())
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(value = titleText, onValueChange = {
            titleText = it
        },
            label = { Text("Title") }
        )
        if (!validationResults.isTitleValid) {
            Text(
                text = validationResults.titleErrorMessage,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
        OutlinedTextField(value = contentText, onValueChange = {
            contentText = it
        },
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
            if (validationResult.isTitleValid && validationResult.isTitleLengthValid && validationResult.isContentLengthValid) {
                list.add(Note(title = titleText, content = contentText))
                titleText = ""
                contentText = ""
            } else {
                validationResults = validationResult
            }
        },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Add")
        }
    }
}

fun validateNoteInput(title: String, content: String): ValidationResults {
    val results = ValidationResults()

    if (title.length < 3) {
        results.isTitleValid = false
        results.titleErrorMessage = "Title must contain at least 3 characters."
    }

    if (title.length > 50) {
        results.isTitleLengthValid = false
        results.titleErrorMessage = "Title must not exceed 50 characters."
    }

    if (content.length > 120) {
        results.isContentLengthValid = false
        results.contentErrorMessage = "Content must not exceed 120 characters."
    }
    return results
}

@Preview(showBackground = true)
@Composable
fun RowViewPreview() {
    NotesAppTheme {
        val note = Note(title = "Monday", content = "Shopping")
        RowView(note = note, onDeleteClick = {}, onEditClick = {}, onDetailClick = {})
    }
}