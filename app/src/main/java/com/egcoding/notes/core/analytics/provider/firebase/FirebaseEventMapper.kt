package com.egcoding.notes.core.analytics.provider.firebase

import com.egcoding.notes.core.analytics.events.AnalyticsEvent
import com.egcoding.notes.core.analytics.provider.EventMapper
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Concrete implementation of [EventMapper] specifically tailored for Firebase Analytics.
 * * This class acts as a translator between the application's internal [AnalyticsEvent]
 * hierarchy and the string-based requirements of the Firebase SDK.
 */
class FirebaseEventMapper : EventMapper {

    /**
     * Maps an internal [AnalyticsEvent] to its corresponding Firebase event string.
     * * Custom events are mapped to snake_case strings, while standard interactions
     * use [FirebaseAnalytics.Event] constants where applicable to enable
     * automatic reporting features in the Firebase Console.
     * * @param event The source [AnalyticsEvent] triggered by the system.
     * @return A string representing the event name recognized by Firebase.
     */
    override fun mapEventName(event: AnalyticsEvent): String = when (event) {
        is AnalyticsEvent.NoteCreated -> "note_created"
        is AnalyticsEvent.NoteEdited -> "note_edited"
        is AnalyticsEvent.NoteDeleted -> "note_deleted"
        is AnalyticsEvent.NoteRestored -> "note_restored"
        is AnalyticsEvent.OrderChanged -> "order_changed"
        is AnalyticsEvent.Error -> "validation_error"
        is AnalyticsEvent.ScreenView -> FirebaseAnalytics.Event.SCREEN_VIEW
    }

    /**
     * Extracts the metadata parameters from the event for Firebase logging.
     * * @param event The source [AnalyticsEvent].
     * @return A map of parameters extracted via [AnalyticsEvent.toParameterMap].
     */
    override fun mapParameters(event: AnalyticsEvent): Map<String, Any> = event.toParameterMap()
}