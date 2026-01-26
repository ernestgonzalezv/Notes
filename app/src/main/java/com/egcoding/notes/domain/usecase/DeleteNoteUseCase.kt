package com.egcoding.notes.domain.usecase

import com.egcoding.notes.domain.model.Note
import com.egcoding.notes.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}