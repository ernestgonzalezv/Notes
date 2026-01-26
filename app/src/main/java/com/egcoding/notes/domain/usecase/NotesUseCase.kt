package com.egcoding.notes.domain.usecase


data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val deleteNote: DeleteNoteUseCase,
    val addNote: AddNotesUseCase,
    val getNote: GetNoteUseCase
)
