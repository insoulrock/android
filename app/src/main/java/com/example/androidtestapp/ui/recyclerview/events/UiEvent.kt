package com.example.androidtestapp.ui.recyclerview.events

sealed class UiEvent {
    data class SortingSwitched(val enabled: Boolean): UiEvent()
    data class FilterEntered(val filter:String?): UiEvent()
}