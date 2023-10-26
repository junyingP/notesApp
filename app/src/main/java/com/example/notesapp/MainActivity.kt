package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.ui.theme.NotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val list = remember {
                mutableStateListOf(Note(title = "Monday",content = "Shopping"))
            }

            NotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(list = list)
                }
            }
        }
    }
}

@Composable
fun MainScreen(list: MutableList<Note>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TextInputView(list = list)
        ListView(list = list)
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

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(value = titleText, onValueChange = {
            titleText = it
        },
            label = { Text("Title") }
        )
        OutlinedTextField(value = contentText, onValueChange = {
            contentText = it
        },
            label = { Text("Content") }
        )
        Button(onClick = {
            list.add(Note(title = titleText, content = contentText))
            titleText = ""
            contentText = ""
        }) {
            Text("Add")
        }
    }
}

@Composable
fun ListView(list: List<Note>) {
    LazyColumn {
        items(list) { note ->
            RowView(note)
        }
    }
}

@Composable
fun RowView(note: Note) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = note.title)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = note.content)
    }
}

@Preview(showBackground = true)
@Composable
fun RowViewPreview() {
    NotesAppTheme {
        RowView(Note(title = "Monday", content = "Shopping"))
    }
}