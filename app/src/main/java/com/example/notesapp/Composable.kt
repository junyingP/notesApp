package com.example.notesapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ListView(list: MutableList<Note>, onEditClick: (Note) -> Unit, onDetailClick: (Note) -> Unit) {
    LazyColumn {
        itemsIndexed(list) { index, note ->
            RowView(
                note = note,
                onDeleteClick = {
                    deleteNote(list, index)
                },
                onEditClick = { onEditClick(note) },
                onDetailClick = { onDetailClick(note) }
            )
    }
}
}

fun deleteNote(list: MutableList<Note>, position: Int) {
    if (position >= 0 && position < list.size) {
        list.removeAt(position)
    }
}

@Composable
fun RowView(note: Note, onDeleteClick: () -> Unit, onEditClick: () -> Unit, onDetailClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
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

    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row {
            Button(
                onClick = onDeleteClick,
                content = { Text("Delete") }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onEditClick,
                content = { Text("Edit") }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onDetailClick,
                content = { Text("Detail") }
            )
        }
    }

}
