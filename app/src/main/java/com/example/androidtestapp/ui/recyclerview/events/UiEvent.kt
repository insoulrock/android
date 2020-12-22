package com.example.androidtestapp.ui.recyclerview.events

sealed class UiEvent {
    data class RefreshList(val filter:String, val needSort: Boolean): UiEvent()
}