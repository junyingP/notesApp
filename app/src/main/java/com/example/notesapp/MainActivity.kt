package com.example.notesapp

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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

fun deleteNote(list: MutableList<Note>, position: Int) {
    if (position >= 0 && position < list.size) {
        list.removeAt(position)
    }
}

@Composable
fun ListView(list: MutableList<Note>) {
    LazyColumn {
        itemsIndexed(list) {index, note ->
            RowView(note = note, onDeleteClick = {
                deleteNote(list, index)
            })
        }
    }
}

@Composable
fun RowView(note: Note, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = note.isChecked.value,
            onCheckedChange = {
                note.isChecked.value = !note.isChecked.value
            }
        )
        Text(
            text = note.title,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = note.content)
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = onDeleteClick,
            content = { Text("Delete")}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RowViewPreview() {
    NotesAppTheme {
        val note = Note(title = "Monday", content = "Shopping")
        RowView(note = note, onDeleteClick = {})
    }
}