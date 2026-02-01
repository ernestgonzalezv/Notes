package com.egcoding.notes.di

import com.egcoding.notes.core.analytics.AnalyticsHub
import com.egcoding.notes.core.analytics.AnalyticsTracker
import com.egcoding.notes.core.analytics.provider.AnalyticsProvider
import com.egcoding.notes.core.analytics.provider.firebase.FirebaseAnalyticsProvider
import com.egcoding.notes.core.analytics.provider.firebase.FirebaseEventMapper
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import org.koin.dsl.module

val analyticsModule = module {

    // Firebase Analytics instance
    single<FirebaseAnalytics> {
        Firebase.analytics
    }

    // Firebase Event Mapper
    single { FirebaseEventMapper() }

    // Firebase Provider (only Firebase for now, prepared for multiple providers)
    single<AnalyticsProvider> {
        FirebaseAnalyticsProvider(
            firebaseAnalytics = get(),
            eventMapper = get<FirebaseEventMapper>()
        )
    }

    // Analytics Hub (dispatcher for multiple providers)
    single {
        AnalyticsHub(
            providers = listOf(get<AnalyticsProvider>())
        )
    }

    // Analytics Tracker (new facade for ViewModels)
    single {
        AnalyticsTracker(get())
    }
}
