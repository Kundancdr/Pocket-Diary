package com.example.pocketdiary.Presentation

import android.icu.text.CaseMap.Title
import com.example.pocketdiary.data.Note

sealed interface NotesEvent {
    object SortNotes: NotesEvent


    data class DeleteNote(val note: Note): NotesEvent

    data class SaveNote(
        val title: String,
        val description: String
    ): NotesEvent
}