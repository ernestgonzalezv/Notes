package com.egcoding.notes.di

import com.egcoding.notes.presentation.ui.screens.edit.AddEditNoteViewModel
import com.egcoding.notes.presentation.ui.screens.main.NotesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::NotesViewModel)
    viewModelOf(::AddEditNoteViewModel)
}