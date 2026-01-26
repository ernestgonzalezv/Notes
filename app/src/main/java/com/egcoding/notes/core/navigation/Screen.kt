package com.egcoding.notes.core.navigation

sealed class Screen(val route: String) {
    object NotesScreen : Screen("notes_screen")
    object AddEditNoteScreen : Screen("add_edit_note_screen")

    companion object {
        const val PARAM_NOTE_ID = "noteId"
        const val PARAM_NOTE_COLOR = "noteColor"
    }
}