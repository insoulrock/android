package com.example.androidtestapp.helpers

import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

class HttpRequester {
    private var client: OkHttpClient = OkHttpClient()

    fun Get(url: String):String?
    {
        val countDownLatch = CountDownLatch(1)

        var request: Request = Request.Builder().url(url).build()
        var resp:String? = ""
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) { countDownLatch.countDown() }

            override fun onResponse(call: Call?, response: Response?) {
                resp = response?.body()?.string()
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()

        return resp;
    }

    fun GetAsync(url: String):String?
    {
        var request: Request = Request.Builder().url(url).build()
        var resp:String? = ""
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) { }

            override fun onResponse(call: Call?, response: Response?) {
                resp = response?.body()?.string()
            }
        })

        return resp;
    }
}