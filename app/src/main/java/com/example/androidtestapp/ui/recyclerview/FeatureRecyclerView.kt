package com.example.androidtestapp.ui.recyclerview

import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ReducerFeature
import com.example.androidtestapp.models.TickerModel

class FeatureRecyclerView :
    ReducerFeature<FeatureRecyclerView.Wish, FeatureRecyclerView.State, FeatureRecyclerView.News>(
        initialState = State(),
        reducer = ReducerImpl(),
        newsPublisher = NewsPublisherImpl()
    ) {
    //TODO: Разобраться, нужно ли инитить переменные значениями
    data class State(
        val tickersCount: Int = 0,
        val tickers: List<TickerModel> = ArrayList<TickerModel>(),
        val needSort: Boolean = false,
        val filter: String? = ""
    )

    sealed class Wish {
        data class SortTickers(val needSort: Boolean) : Wish()
        data class FilterTickers(val filter: String?) : Wish()
    }

    class ReducerImpl : Reducer<State, Wish> {
        override fun invoke(state: State, wish: Wish): State =
            when (wish) {
                is Wish.SortTickers -> state.copy(
                    tickersCount = 0,
                    tickers = ArrayList<TickerModel>(),
                    filter = state.filter,
                    needSort = wish.needSort
                )
                is Wish.FilterTickers -> state.copy(
                    tickersCount = 0,
                    tickers = ArrayList<TickerModel>(),
                    filter = wish.filter,
                    needSort = state.needSort
                )
            }
    }
    class NewsPublisherImpl: SimpleNewsPublisher<Wish, State, News>(){
        override fun invoke(wish: Wish, state: State): News? = when(wish){
            is Wish.SortTickers -> News.ToastOnClick("Need sort: ${wish.needSort}")
            is Wish.FilterTickers -> News.ToastOnClick("Need sort: ${wish.filter}")
        }
    }

    sealed class News {
        data class ToastOnClick(val message: String) : News()
    }

}