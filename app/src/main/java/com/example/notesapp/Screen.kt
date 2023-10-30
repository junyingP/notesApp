package com.example.notesapp

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object DetailScreen : Screen("detail_screen")
    object CreateNoteScreen : Screen("create_note_screen")
    object EditNoteScreen : Screen("edit_note_screen")

}
