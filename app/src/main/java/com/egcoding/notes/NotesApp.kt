package com.egcoding.notes

import android.app.Application
import com.egcoding.notes.di.analyticsModule
import com.egcoding.notes.di.databaseModule
import com.egcoding.notes.di.repositoryModule
import com.egcoding.notes.di.useCaseModule
import com.egcoding.notes.di.viewModelModule
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NotesApp)
            modules(
                databaseModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                analyticsModule
            )
        }
        // Initialize Firebase Crashlytics
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true
    }
}
