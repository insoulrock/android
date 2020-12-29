package com.example.androidtestapp.ui.recyclerview

import android.content.Context
import android.widget.Toast
import io.reactivex.functions.Consumer

class NewsListener(
    private val context: Context?
) : Consumer<FeatureRecyclerView.News> {

    override fun accept(news: FeatureRecyclerView.News) {
        when (news) {
            is FeatureRecyclerView.News.Notice -> sendToast(news.message)
        }
    }

    private fun sendToast(message:String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}