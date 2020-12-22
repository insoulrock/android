package com.example.androidtestapp.ui.recyclerview.viewmodel

import com.example.androidtestapp.models.TickerModel

data class ViewModel(
    val isLoading: Boolean,
    val tickers: List<TickerModel>
)