package com.egcoding.notes.presentation.ui.screens.edit

import androidx.lifecycle.SavedStateHandle
import com.egcoding.notes.domain.usecase.NoteUseCases
import com.egcoding.notes.presentation.ui.screens.edit.AddEditNoteEvent
import com.egcoding.notes.presentation.ui.screens.edit.AddEditNoteViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class AddEditNoteViewModelTest {

    private lateinit var viewModel: AddEditNoteViewModel
    private lateinit var noteUseCases: NoteUseCases
    private lateinit var savedStateHandle: SavedStateHandle

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        noteUseCases = mockk(relaxed = true)
        savedStateHandle = mockk(relaxed = true)
        every { savedStateHandle.get<Int>(any()) } returns -1

        viewModel = AddEditNoteViewModel(noteUseCases, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun enteredTitleEventUpdatesTitleState() {
        val title = "Mi nueva nota"
        viewModel.onEvent(AddEditNoteEvent.EnteredTitle(title))
        assertEquals(title, viewModel.noteTitle.value.text)
    }

    @Test
    fun saveNoteEventCallsAddNoteUseCase() = runTest {
        viewModel.onEvent(AddEditNoteEvent.EnteredTitle("Test Title"))
        viewModel.onEvent(AddEditNoteEvent.EnteredContent("Test Content"))
        coEvery { noteUseCases.addNote(any()) } returns Unit
        viewModel.onEvent(AddEditNoteEvent.SaveNote)
        advanceUntilIdle()
        coVerify(exactly = 1) { noteUseCases.addNote(any()) }
    }
}