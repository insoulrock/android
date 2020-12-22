package com.example.androidtestapp.ui.secondary.events

sealed class UiEvent {
    object LeftButtonClicked : UiEvent()
    object CenterButtonClicked : UiEvent()
    object RightButtonClicked : UiEvent()
    object ResetButtonClicked: UiEvent()
}