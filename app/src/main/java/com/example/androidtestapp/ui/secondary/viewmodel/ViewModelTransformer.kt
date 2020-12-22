package com.example.androidtestapp.ui.secondary.viewmodel

import com.example.androidtestapp.ui.secondary.FeatureOne

class ViewModelTransformer: (FeatureOne.State) -> ViewModel {
    override fun invoke(state: FeatureOne.State): ViewModel {
        return ViewModel(state.counter, state.counter > 0, state.lastTimeIncrease, state.lastTimeReset)
    }
}