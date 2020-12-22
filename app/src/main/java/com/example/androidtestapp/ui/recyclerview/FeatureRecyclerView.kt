package com.example.androidtestapp.ui.recyclerview

import android.util.Log
import com.badoo.mvicore.element.*
import com.badoo.mvicore.feature.ActorReducerFeature
import com.example.androidtestapp.helpers.RecyclerViewHelper
import com.example.androidtestapp.helpers.TickerCache
import com.example.androidtestapp.models.TickerModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FeatureRecyclerView :
    ActorReducerFeature<FeatureRecyclerView.Wish, FeatureRecyclerView.Effect, FeatureRecyclerView.State, FeatureRecyclerView.News>(
        initialState = State(),
        bootstrapper = BootStrapperImpl(),
        actor = ActorImpl(),
        reducer = ReducerImpl(),
        newsPublisher = NewsPublisherImpl()
    ), KoinComponent {

    data class State(
        val isLoading: Boolean = false,
        val tickers: List<TickerModel> = ArrayList<TickerModel>()
    )

    sealed class Wish {
        data class LoadTickers(val filter: String, val needSort: Boolean) : Wish()
    }

    sealed class Effect {
        object StartLoading : Effect()
        data class SuccessfullyLoaded(val tickers: List<TickerModel>) : Effect()
        data class ErrorLoading(val throwable: Throwable) : Effect()
    }

    sealed class News {
        data class ToastOnClick(val message: String) : News()
    }

    class BootStrapperImpl : Bootstrapper<Wish> {
        override fun invoke(): Observable<Wish> = Observable.just(Wish.LoadTickers("", false))
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State =
            when (effect) {
                is Effect.StartLoading -> state.copy(
                    isLoading = true
                )
                is Effect.SuccessfullyLoaded -> state.copy(
                    isLoading = false,
                    tickers = effect.tickers
                )
                is Effect.ErrorLoading -> state.copy(
                    isLoading = false
                )
            }
    }

    class ActorImpl : Actor<State, Wish, Effect>, KoinComponent {
        private val tickerCache: TickerCache by inject()
        override fun invoke(state: State, wish: Wish): Observable<Effect> = when (wish) {
            is Wish.LoadTickers -> {
                tickerCache.getTickers()
                    .map {
                        var tickers = it
                        if (!wish.filter.isNullOrEmpty())
                            tickers = tickers.filter { x -> x.instrument?.contains(wish.filter, true) == true }
                        if (wish.needSort)
                            tickers = tickers.sortedByDescending { x -> x.percentChange }

                        Log.d("ActorImpl", "tickerCount = ${tickers.count()}, filter=${wish.filter}, needSort=${wish.needSort}")
                        Effect.SuccessfullyLoaded(tickers) as Effect
                    }
                    .startWith(Observable.just(Effect.StartLoading))
                    .onErrorReturn { Effect.ErrorLoading(it) }
            }
        }
    }

    class NewsPublisherImpl : NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(action: Wish, effect: Effect, state: State): News? = when (effect) {
            is Effect.ErrorLoading -> News.ToastOnClick("ErrorLoading: ${effect.throwable.localizedMessage}")
            else -> null
        }
    }
}