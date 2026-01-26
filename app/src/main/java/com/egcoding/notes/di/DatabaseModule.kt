package com.egcoding.notes.di


import androidx.room.Room
import com.egcoding.notes.data.data_source.NoteDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    single { get<NoteDatabase>().noteDao }
}