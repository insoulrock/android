package com.example.androidtestapp.ui.recyclerview.viewmodel

import com.example.androidtestapp.ui.recyclerview.FeatureRecyclerView

class ViewModelTransformer : (FeatureRecyclerView.State) -> ViewModel {
    override fun invoke(state: FeatureRecyclerView.State): ViewModel = ViewModel(
            filter = state.filter,
            needSort = state.needSort
        )
}