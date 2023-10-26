package com.example.notesapp

import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val creationDate: Long = System.currentTimeMillis()
)