package com.example.androidtestapp.models

class TickerModel {
    public var instrument: String? = null
    public var last: Double? = null
    var percentChange: Double? = null
    var low: Double? = null
    var high: Double? = null
    var baseVolume: Double? = null
    var quoteVolume: Double? = null
    var volumeInBtc: Double? = null
    var volumeInUsd: Double? = null
    var ask: Double? = null
    var bid: Double? = null
    var timestamp: String? = null
}