package com.example.androidtestapp.helpers

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import kotlin.concurrent.thread

class HttpRequester {
    private var client: OkHttpClient = OkHttpClient()

    fun Get(url: String) : Single<String>
    {
        return Single.fromCallable  {
            try {
                var request: Request = Request.Builder().url(url).build()
                var resp = client.newCall(request).execute()
                return@fromCallable resp?.body()?.string()
            }
            catch (exc: Exception)
            {
                Log.e("123", exc.toString())
                return@fromCallable null
            }
        }
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