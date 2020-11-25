package com.example.androidtestapp.helpers

import com.example.androidtestapp.models.TickerModel
import com.google.gson.GsonBuilder
import okhttp3.*
import org.koin.core.component.KoinComponent
import java.io.IOException
import java.util.concurrent.CountDownLatch

class DataProvider : KoinComponent {
    private val URL:String = "https://api.crex24.com/v2/public/tickers"
    private var okHttpClient: OkHttpClient = OkHttpClient()
    private  lateinit var tickers: List<TickerModel>

    public fun getTickers() : List<TickerModel>
    {
        val request: Request = Request.Builder().url(URL).build()
        val countDownLatch = CountDownLatch(1)

        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) { countDownLatch.countDown() }

            override fun onResponse(call: Call?, response: Response?) {
                var json = response?.body()?.string()
                val gson = GsonBuilder().create()
                tickers = gson.fromJson(json,Array<TickerModel>::class.java).toList()
                countDownLatch.countDown()
            }
        })

        countDownLatch.await()

        return tickers;
    }
}