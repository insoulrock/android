package com.example.androidtestapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.MainThread
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

private  val LOG_TAG: String = "myLogs";
private val EXTRA_KEY: String = "key1";

class SecondActivity : AppCompatActivity() {
    var disBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
//        var extraValue = intent.getStringExtra(EXTRA_KEY)
//        Log.d(LOG_TAG, "SecondActivity onCreate $extraValue")
//        var textView = findViewById<TextView>(R.id.textViewMain)
//        textView.text = extraValue


        var ss = arrayOf("123", "22233", "231", "r313", "09")
        var dis0 = Observable.fromArray(ss)
            .debounce(5, TimeUnit.SECONDS)
//            .flatMap {
//                var ss: String = ""
//                it?.forEach { x -> ss += x  }
//                Observable.just(ss.trim())
//            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("+++", "-> $it")
                }
            )
        disBag.add(dis0)

        button_sec_test.setOnClickListener({Log.d(LOG_TAG, "Listener set!")})
        var dis1 = Observable.just("Сергей", "Андрей", "Александр")
//            .map {
//                if(it.contains("ей"))
//                {
//                    it + " (Классный кекс)"
//                }
//                else
//                    it + ""
//            }
            .flatMap {
                var delay = Random().nextInt(10).toLong();
                Observable.just(it).delay(delay, TimeUnit.SECONDS)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(LOG_TAG, "-> $it")
            },
            {
                Log.d(LOG_TAG, "Error! -> ${it.localizedMessage}")
            })



        disBag.add(dis1)
    }

    override fun onPause() {
        super.onPause()
        disBag.dispose()
    }

    fun getDataSource(): Flowable<Int>
    {
        return Flowable.create ({ subscriber ->
            for(i in 0..100)
            {
                Thread.sleep(1000)
                subscriber.onNext(i)
            }
            subscriber.onComplete()
        }, BackpressureStrategy.DROP)
    }
}
