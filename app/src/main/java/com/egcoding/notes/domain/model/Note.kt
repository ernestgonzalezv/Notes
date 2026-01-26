package com.egcoding.notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.egcoding.notes.presentation.theme.BabyBlue
import com.egcoding.notes.presentation.theme.LightGreen
import com.egcoding.notes.presentation.theme.RedOrange
import com.egcoding.notes.presentation.theme.RedPink
import com.egcoding.notes.presentation.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)