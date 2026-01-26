package com.egcoding.notes.di


import com.egcoding.notes.data.repository.NoteRepositoryImpl
import com.egcoding.notes.domain.repository.NoteRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<NoteRepository> { NoteRepositoryImpl(get()) }
}