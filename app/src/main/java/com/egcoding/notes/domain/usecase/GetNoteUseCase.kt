package com.egcoding.notes.domain.usecase

import com.egcoding.notes.domain.model.Note
import com.egcoding.notes.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}