package com.egcoding.notes.presentation.ui.screens.main

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.egcoding.notes.core.navigation.Screen
import com.egcoding.notes.core.utils.TestTags
import com.egcoding.notes.di.testAppModule
import com.ernestgonzalezv.cleanarchitecturenoteapp.ui.theme.NotesTheme
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class NotesScreenTest : KoinTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        stopKoin()
        startKoin {
           val context = ApplicationProvider.getApplicationContext<Application>()
            androidContext(context)
            modules(testAppModule)
        }

        composeRule.setContent {
            val navController = rememberNavController()
            NotesTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }
}