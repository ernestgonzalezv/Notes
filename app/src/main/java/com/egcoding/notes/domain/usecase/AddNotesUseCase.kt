package com.egcoding.notes.domain.usecase

import com.egcoding.notes.domain.model.InvalidNoteException
import com.egcoding.notes.domain.model.Note
import com.egcoding.notes.domain.repository.NoteRepository

class AddNotesUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}