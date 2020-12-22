package com.example.androidtestapp.ui.secondary.viewmodel

import java.util.*

data class ViewModel(val counter: Int,
                     val resetIsAvailable: Boolean,
                     val lastDtIncrease: Date,
                     val lastDtReset: Date)
