package com.example.androidtestapp.helpers

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class JsonConverter {
    companion object{
        inline fun <reified T> Convert(json: String):T{
            return GsonBuilder().create().fromJson<T>(json, object: TypeToken<T>() {}.type)
        }
    }
}