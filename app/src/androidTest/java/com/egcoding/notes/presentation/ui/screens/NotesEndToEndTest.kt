package com.egcoding.notes.presentation.ui.screens


import android.app.Application
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.core.app.ApplicationProvider
import com.egcoding.notes.core.navigation.Screen
import com.egcoding.notes.core.utils.TestTags
import com.egcoding.notes.di.testAppModule
import com.egcoding.notes.presentation.ui.screens.edit.AddEditNoteScreen
import com.egcoding.notes.presentation.ui.screens.main.NotesScreen
import com.ernestgonzalezv.cleanarchitecturenoteapp.ui.theme.NotesTheme
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class NotesEndToEndTest : KoinTest {

    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalAnimationApi::class)
    @Before
    fun setUp() {
        stopKoin()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext<Application>())
            modules(testAppModule)
        }

        composeRule.setContent {
            NotesTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                    composable(
                        route = Screen.AddEditNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument("noteId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument("noteColor") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                        )
                    ) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = color
                        )
                    }
                }
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun saveNewNote_editAfterwards() {
        composeRule.onNodeWithContentDescription("Add").performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("test-title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput("test-content")
        composeRule.onNodeWithContentDescription("Save").performClick()
        composeRule.waitUntilAtLeastOneExists(hasText("test-title"), 5000)
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-title").performClick()
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).assertTextEquals("test-title")
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextClearance()
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("test-title2")
        composeRule.onNodeWithContentDescription("Save").performClick()
        composeRule.waitUntilAtLeastOneExists(hasText("test-title2"), 5000)
        composeRule.onNodeWithText("test-title2").assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        for(i in 1..3) {
            composeRule.onNodeWithContentDescription("Add").performClick()
            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput(i.toString())
            composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput(i.toString())
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithText("Title").performClick()
        composeRule.onNodeWithText("Descending").performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0].assertTextContains("3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1].assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2].assertTextContains("1")
    }
}