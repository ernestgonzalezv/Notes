package com.egcoding.notes.core.analytics.events

import java.util.UUID

/**
 * Base hierarchy for all analytics events within the application.
 * * This sealed class enforces a consistent structure for event tracking, requiring
 * each implementation to define its own set of tracking parameters.
 */
sealed class AnalyticsEvent {

    /** * Unique identifier for the specific event instance.
     * Useful for de-duplication and cross-referencing logs.
     */
    val eventId: String = UUID.randomUUID().toString()

    /** * The system time in milliseconds at which the event occurred.
     */
    val timestamp: Long = System.currentTimeMillis()

    /**
     * Converts the event properties into a key-value map.
     * * @return A [Map] containing the event's specific parameters,
     * where keys are predefined constants and values are primitive types.
     */
    abstract fun toParameterMap(): Map<String, Any>

    /**
     * Triggered when a user successfully creates a new note.
     * * @property color The integer representation of the selected note color.
     * @property titleLength The number of characters in the title (used for engagement analysis).
     */
    data class NoteCreated(val color: Int, val titleLength: Int) : AnalyticsEvent() {
        override fun toParameterMap() = mapOf(
            PARAM_NOTE_COLOR to color,
            PARAM_TITLE_LENGTH to titleLength
        )
    }

    /**
     * Triggered when a user modifies and saves an existing note.
     */
    data class NoteEdited(val color: Int, val titleLength: Int) : AnalyticsEvent() {
        override fun toParameterMap() = mapOf(
            PARAM_NOTE_COLOR to color,
            PARAM_TITLE_LENGTH to titleLength
        )
    }

    /**
     * Triggered when a note is deleted or moved to the trash.
     */
    data class NoteDeleted(val color: Int) : AnalyticsEvent() {
        override fun toParameterMap() = mapOf(PARAM_NOTE_COLOR to color)
    }

    /**
     * Triggered when a user restores a previously deleted note.
     */
    data class NoteRestored(val color: Int) : AnalyticsEvent() {
        override fun toParameterMap() = mapOf(PARAM_NOTE_COLOR to color)
    }

    /**
     * Captures changes in the user's preferred sorting or filtering of the note list.
     * * @property orderType The direction of the sort (e.g., "ASC", "DESC").
     * @property orderBy The attribute used for sorting (e.g., "DATE", "TITLE").
     */
    data class OrderChanged(val orderType: String, val orderBy: String) : AnalyticsEvent() {
        override fun toParameterMap() = mapOf(
            PARAM_ORDER_TYPE to orderType,
            PARAM_ORDER_BY to orderBy
        )
    }

    /**
     * Records critical errors or caught exceptions that impact the user experience.
     * * @property message A brief description or error code for debugging.
     */
    data class Error(val message: String) : AnalyticsEvent() {
        override fun toParameterMap() = mapOf(PARAM_ERROR_MESSAGE to message)
    }

    /**
     * Essential event for tracking user navigation between screens.
     * * @property screenName The human-readable name of the screen (e.g., "settings_screen").
     * @property screenClass (Optional) The technical class name of the Activity or Fragment.
     */
    data class ScreenView(val screenName: String, val screenClass: String? = null) : AnalyticsEvent() {
        override fun toParameterMap() = buildMap {
            put(PARAM_SCREEN_NAME, screenName)
            screenClass?.let { put(PARAM_SCREEN_CLASS, it) }
        }
    }

    /**
     * Constant keys for analytics parameters to ensure naming consistency
     * across different tracking modules.
     */
    companion object {
        const val PARAM_NOTE_COLOR = "note_color"
        const val PARAM_TITLE_LENGTH = "title_length"
        const val PARAM_ORDER_TYPE = "order_type"
        const val PARAM_ORDER_BY = "order_by"
        const val PARAM_SCREEN_NAME = "screen_name"
        const val PARAM_SCREEN_CLASS = "screen_class"
        const val PARAM_ERROR_MESSAGE = "error_message"
    }
}