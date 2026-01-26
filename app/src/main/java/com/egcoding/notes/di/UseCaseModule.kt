package com.egcoding.notes.di

import com.egcoding.notes.domain.usecase.AddNotesUseCase
import com.egcoding.notes.domain.usecase.DeleteNoteUseCase
import com.egcoding.notes.domain.usecase.GetNoteUseCase
import com.egcoding.notes.domain.usecase.GetNotesUseCase
import com.egcoding.notes.domain.usecase.NoteUseCases
import org.koin.dsl.module

val useCaseModule = module {
    single { GetNotesUseCase(get()) }
    single { DeleteNoteUseCase(get()) }
    single { AddNotesUseCase(get()) }
    single { GetNoteUseCase(get()) }
    single {
        NoteUseCases(
            getNotes = get(),
            deleteNote = get(),
            addNote = get(),
            getNote = get()
        )
    }
}