package com.egcoding.notes.core.analytics.provider

import com.egcoding.notes.core.analytics.events.AnalyticsEvent

/**
 * Defines the contract for an analytics service provider.
 * * Implementations of this interface handle the actual delivery of data to
 * specific third-party SDKs or internal logging systems.
 */
interface AnalyticsProvider {

    /**
     * A unique identifier for the provider (e.g., "Firebase", "Mixpanel").
     * Useful for debugging or filtering specific providers at runtime.
     */
    val providerId: String

    /**
     * Indicates whether the provider is currently active and allowed to capture data.
     * This should respect user privacy settings and GDPR/CCPA requirements.
     */
    val isEnabled: Boolean

    /**
     * Dispatches a structured [AnalyticsEvent] to the provider.
     * * @param event The domain event to be tracked, containing its own
     * metadata and parameters.
     */
    fun trackEvent(event: AnalyticsEvent)

    /**
     * Specific method for tracking screen transitions.
     * * While screen views can be sent via [trackEvent], this explicit method
     * allows providers to use specialized SDK functions (like Firebase's logScreenView).
     * * @param screenName The logical name of the screen being viewed.
     * @param screenClass The optional technical class name (Activity/Fragment) of the screen.
     */
    fun trackScreenView(screenName: String, screenClass: String? = null)

    /**
     * Dynamically enables or disables tracking for this specific provider.
     * * @param enabled True to start tracking, false to cease all data collection.
     */
    fun setEnabled(enabled: Boolean)

    /**
     * Clears all cached data, user identifiers, and resets the provider state.
     * Typically called during user logout or when privacy settings are revoked.
     */
    fun reset()
}