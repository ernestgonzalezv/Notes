package com.egcoding.notes.core.analytics

import com.egcoding.notes.core.analytics.events.AnalyticsEvent

/**
 * A high-level Facade for analytics tracking within the Notes App.
 * * This class provides a type-safe, developer-friendly API for ViewModels and other
 * components to track user behavior without interacting directly with the lower-level
 * event classes or providers.
 *
 * @property hub The [AnalyticsHub] responsible for orchestrating multiple analytics providers.
 */
class AnalyticsTracker(
    private val hub: AnalyticsHub
) {

    // region Note Lifecycle Events

    /**
     * Records the successful creation of a new note.
     * * @param color The ARGB integer value of the note's background color.
     * @param titleLength The character count of the title, used to measure user engagement levels.
     */
    fun trackNoteCreated(color: Int, titleLength: Int) {
        hub.track(
            AnalyticsEvent.NoteCreated(
                color = color,
                titleLength = titleLength
            )
        )
    }

    /**
     * Records updates made to an existing note.
     * * @param titleLength The current length of the note's title after editing.
     * @param color The ARGB color value of the note.
     */
    fun trackNoteEdited(titleLength: Int, color: Int) {
        hub.track(
            AnalyticsEvent.NoteEdited(
                titleLength = titleLength,
                color = color
            )
        )
    }

    /**
     * Records when a note is moved to the trash or deleted.
     * * @param color The color of the note being deleted (used to identify trends in note usage).
     */
    fun trackNoteDeleted(color: Int) {
        hub.track(AnalyticsEvent.NoteDeleted(
            color = color
        ))
    }

    /**
     * Records the restoration of a previously deleted note.
     * * @param color The color of the restored note.
     */
    fun trackNoteRestored(color: Int) {
        hub.track(AnalyticsEvent.NoteRestored(
            color = color
        ))
    }
    // endregion

    // region Organization Events

    /**
     * Records changes in the user's preferred list sorting.
     * * @param orderType The direction of the sort (e.g., "Ascending", "Descending").
     * @param orderBy The field being sorted (e.g., "Date", "Title", "Color").
     */
    fun trackOrderChanged(orderType: String, orderBy: String) {
        hub.track(
            AnalyticsEvent.OrderChanged(
                orderType = orderType,
                orderBy = orderBy
            )
        )
    }

    /**
     * Tracks whether the order selection panel is shown or hidden.
     * * Note: This currently uses the [AnalyticsEvent.Error] type as a temporary
     * implementation for logging UI visibility states.
     * * @param isVisible The new visibility state of the order section.
     */
    fun trackOrderSectionToggled(isVisible: Boolean) {
        hub.track(AnalyticsEvent.Error("order_section_visible: $isVisible"))
    }
    // endregion

    // region Screen Views

    /**
     * Records a screen transition/navigation event.
     * * @param screenName A unique identifier for the screen (e.g., "notes_list").
     * @param screenClass The optional technical name of the Fragment or Activity class.
     */
    fun trackScreenView(screenName: String, screenClass: String? = null) {
        hub.trackScreenView(screenName, screenClass)
    }
    // endregion

    // region System Events

    /**
     * Records validation failures, exceptions, or unexpected behaviors.
     * * @param message A descriptive message or error code explaining the failure.
     */
    fun trackError(message: String) {
        hub.track(AnalyticsEvent.Error(message))
    }

    /**
     * Enables or disables all underlying analytics providers.
     * Use this to respect user privacy settings (Opt-out/Opt-in).
     * * @param enabled Set to true to resume tracking, false to pause.
     */
    fun setEnabled(enabled: Boolean) {
        hub.setEnabled(enabled)
    }

    /**
     * Clears all local analytics data and identifiers across all providers.
     * Should be invoked during logout or data reset.
     */
    fun reset() {
        hub.reset()
    }
    // endregion
}