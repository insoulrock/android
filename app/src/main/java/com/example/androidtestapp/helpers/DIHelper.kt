package com.example.androidtestapp.helpers

import com.example.androidtestapp.fragments.FragmentNotification
import org.koin.dsl.module

val applicationModule = module {
    single { Router() }
    single { DataProvider() }
    single { HttpRequester() }
    single { TickerCache() }
    single { RecyclerViewHelper() }
    single { FragmentNotification() }
}