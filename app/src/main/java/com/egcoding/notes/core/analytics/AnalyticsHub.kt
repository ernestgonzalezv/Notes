package com.egcoding.notes.core.analytics

import com.egcoding.notes.core.analytics.events.AnalyticsEvent
import com.egcoding.notes.core.analytics.provider.AnalyticsProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Central hub for dispatching analytics events in the Notes App.
 * Dispatches events asynchronously to all registered providers (Firebase, etc.).
 */
class AnalyticsHub(
    private val providers: List<AnalyticsProvider>
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /**
     * Tracks a note-related event (create, delete, edit, order) to all enabled providers.
     */
    fun track(event: AnalyticsEvent) {
        scope.launch {
            providers.filter { it.isEnabled }.forEach { provider ->
                try {
                    provider.trackEvent(event)
                } catch (e: Exception) {
                    Timber.e(e, "Error tracking event ${event::class.simpleName} in ${provider.providerId}")
                }
            }
        }
    }

    /**
     * Tracks when the user navigates between Notes Screen and Add/Edit Screen.
     */
    fun trackScreenView(screenName: String, screenClass: String? = null) {
        scope.launch {
            providers.filter { it.isEnabled }.forEach { provider ->
                try {
                    provider.trackScreenView(screenName, screenClass)
                } catch (e: Exception) {
                    Timber.e(e, "Error tracking screen view $screenName in ${provider.providerId}")
                }
            }
        }
    }

    /**
     * Enables or disables all providers (useful for GDPR/Privacy settings).
     */
    fun setEnabled(enabled: Boolean) {
        providers.forEach { provider ->
            try {
                provider.setEnabled(enabled)
            } catch (e: Exception) {
                Timber.e(e, "Error setting enabled state in ${provider.providerId}")
            }
        }
    }

    /**
     * Resets analytics data for all providers.
     */
    fun reset() {
        providers.forEach { provider ->
            try {
                provider.reset()
            } catch (e: Exception) {
                Timber.e(e, "Error resetting analytics in ${provider.providerId}")
            }
        }
    }

    /**
     * Gets all registered providers.
     */
    fun getProviders(): List<AnalyticsProvider> = providers.toList()
}