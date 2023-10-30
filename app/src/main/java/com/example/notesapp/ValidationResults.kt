package com.example.notesapp

data class ValidationResults(
    var isTitleValid: Boolean = true,
    var isTitleLengthValid: Boolean = true,
    var isContentLengthValid: Boolean = true,
    var titleErrorMessage: String = "",
    var contentErrorMessage: String = ""
)