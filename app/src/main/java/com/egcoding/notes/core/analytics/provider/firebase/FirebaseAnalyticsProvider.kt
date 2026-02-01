package com.egcoding.notes.core.analytics.provider.firebase

import android.os.Bundle
import com.egcoding.notes.core.analytics.events.AnalyticsEvent
import com.egcoding.notes.core.analytics.provider.AnalyticsProvider
import com.egcoding.notes.core.analytics.provider.EventMapper
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticsProvider(
    private val firebaseAnalytics: FirebaseAnalytics,
    private val eventMapper: EventMapper = FirebaseEventMapper()
) : AnalyticsProvider {

    override val providerId: String = PROVIDER_ID

    private var _isEnabled: Boolean = true
    override val isEnabled: Boolean get() = _isEnabled

    override fun trackEvent(event: AnalyticsEvent) {
        if (!_isEnabled) return

        val eventName = eventMapper.mapEventName(event) ?: return

        val parameters = eventMapper.mapParameters(event)
        val bundle = parametersToBundle(parameters)

        firebaseAnalytics.logEvent(eventName, bundle)
    }

    override fun trackScreenView(screenName: String, screenClass: String?) {
        if (!_isEnabled) return

        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            screenClass?.let { putString(FirebaseAnalytics.Param.SCREEN_CLASS, it) }
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    override fun setEnabled(enabled: Boolean) {
        _isEnabled = enabled
        firebaseAnalytics.setAnalyticsCollectionEnabled(enabled)
    }

    override fun reset() = firebaseAnalytics.resetAnalyticsData()

    private fun parametersToBundle(parameters: Map<String, Any>): Bundle {
        return Bundle().apply {
            parameters.forEach { (key, value) ->
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putLong(key, value.toLong())
                    is Long -> putLong(key, value)
                    is Double -> putDouble(key, value)
                    is Boolean -> putString(key, value.toString())
                }
            }
        }
    }

    companion object {
        const val PROVIDER_ID = "firebase"
    }
}