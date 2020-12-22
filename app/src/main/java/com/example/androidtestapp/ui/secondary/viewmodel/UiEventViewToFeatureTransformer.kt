package com.example.androidtestapp.ui.secondary.viewmodel

import com.example.androidtestapp.ui.secondary.FeatureOne
import com.example.androidtestapp.ui.secondary.events.UiEvent

class UiEventViewToFeatureTransformer: (UiEvent) -> FeatureOne.Wish? {
    override fun invoke(event: UiEvent): FeatureOne.Wish? = when(event){
        is UiEvent.LeftButtonClicked -> FeatureOne.Wish.IncreaseCounter
        is UiEvent.CenterButtonClicked -> FeatureOne.Wish.IncreaseCounter
        is UiEvent.RightButtonClicked -> FeatureOne.Wish.IncreaseCounter
        is UiEvent.ResetButtonClicked -> FeatureOne.Wish.ResetCounter
    }
}