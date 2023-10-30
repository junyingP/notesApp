package com.example.notesapp

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object DetailScreen : Screen("detail_screen")
    object CreatNoteScreen : Screen("creat_note_screen")

}
