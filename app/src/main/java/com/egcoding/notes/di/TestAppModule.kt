package com.egcoding.notes.di


import androidx.room.Room
import com.egcoding.notes.data.data_source.NoteDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val testAppModule = module {
    single {
        Room.inMemoryDatabaseBuilder(
            androidApplication(),
            NoteDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }
    single { get<NoteDatabase>().noteDao }
    includes(listOf(repositoryModule, useCaseModule, viewModelModule))
}