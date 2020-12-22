package com.example.androidtestapp.ui.recyclerview.viewmodel

import com.example.androidtestapp.ui.recyclerview.FeatureRecyclerView
import com.example.androidtestapp.ui.recyclerview.events.UiEvent

class UiEventViewToFeatureTransformer : (UiEvent) -> FeatureRecyclerView.Wish {
    override fun invoke(event: UiEvent): FeatureRecyclerView.Wish =
        when (event) {
            is UiEvent.FilterEntered -> FeatureRecyclerView.Wish.FilterTickers(event.filter)
            is UiEvent.SortingSwitched -> FeatureRecyclerView.Wish.SortTickers(event.enabled)
        }
}