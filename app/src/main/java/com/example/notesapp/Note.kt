package com.example.notesapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    var isChecked: MutableState<Boolean> = mutableStateOf(false)
)
