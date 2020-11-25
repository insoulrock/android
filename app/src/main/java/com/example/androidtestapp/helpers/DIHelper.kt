package com.example.androidtestapp.helpers

import org.koin.dsl.module

val applicationModule = module {
    single { Router() }
    single { DataProvider() }
}