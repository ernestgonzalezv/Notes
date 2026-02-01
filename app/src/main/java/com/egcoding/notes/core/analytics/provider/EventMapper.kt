package com.egcoding.notes.core.analytics.provider

import com.egcoding.notes.core.analytics.events.AnalyticsEvent

/**
 * Interface for mapping analytics events to provider-specific formats.
 * Each provider can have its own mapper to translate event names and parameters.
 */
interface EventMapper {
    /**
     * Maps an analytics event to the provider-specific event name.
     * @param event The event to map
     * @return The provider-specific event name, or null if the event should not be tracked
     */
    fun mapEventName(event: AnalyticsEvent): String?

    /**
     * Maps an analytics event to provider-specific parameters.
     * @param event The event to map
     * @return The provider-specific parameter map
     */
    fun mapParameters(event: AnalyticsEvent): Map<String, Any>
}