package com.egcoding.notes.presentation.ui.screens.main



import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egcoding.notes.core.analytics.AnalyticsTracker
import com.egcoding.notes.core.navigation.Screen
import com.egcoding.notes.domain.model.Note
import com.egcoding.notes.domain.usecase.NoteUseCases
import com.egcoding.notes.domain.util.NoteOrder
import com.egcoding.notes.domain.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteUseCases: NoteUseCases,
    private val tracker: AnalyticsTracker
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
        tracker.trackScreenView(screenName = Screen.NotesScreen.route)
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
                val orderBy = when(event.noteOrder) {
                    is NoteOrder.Title -> "title"
                    is NoteOrder.Date -> "date"
                    is NoteOrder.Color -> "color"
                }
                val orderType = if(event.noteOrder.orderType is OrderType.Ascending) "ascending" else "descending"
                tracker.trackOrderChanged(orderType = orderType, orderBy = orderBy)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                    tracker.trackNoteDeleted(event.note.color)
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    val color = recentlyDeletedNote?.color
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                    tracker.trackNoteRestored(color = color ?: 0)
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
                tracker.trackOrderSectionToggled(isVisible = !state.value.isOrderSectionVisible)
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
