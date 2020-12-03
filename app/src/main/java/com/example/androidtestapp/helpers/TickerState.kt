package com.example.androidtestapp.helpers

import com.example.androidtestapp.models.TickerModel
import io.reactivex.subjects.PublishSubject

class TickerState  {
    public val subject :PublishSubject<List<TickerModel>> = PublishSubject.create()
}