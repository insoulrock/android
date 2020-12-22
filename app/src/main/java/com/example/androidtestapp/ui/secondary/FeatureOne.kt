package com.example.androidtestapp.ui.secondary

import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ReducerFeature
import java.util.*

class FeatureOne: ReducerFeature<FeatureOne.Wish, FeatureOne.State, FeatureOne.News>(
    initialState = State(),
    reducer = ReducerImpl(),
    newsPublisher = NewsPublisherImpl()
) {

    data class State(
        val counter: Int = 0,
        val lastTimeReset: Date = Calendar.getInstance().time,
        val lastTimeIncrease: Date = Calendar.getInstance().time
    )

    sealed class Wish {
        object IncreaseCounter : Wish()
        object ResetCounter : Wish()
    }

    sealed class News {
        data class ToastOnClick(val message:String) : News()
    }

    class ReducerImpl : Reducer<State, Wish> {
        override fun invoke(state: State, wish: Wish): State =
            when (wish) {
                is Wish.IncreaseCounter -> state.copy(counter = state.counter + 1, lastTimeIncrease = Calendar.getInstance().time)
                is Wish.ResetCounter -> state.copy(counter = 0, lastTimeReset = Calendar.getInstance().time)
            }
    }

    class NewsPublisherImpl : SimpleNewsPublisher<Wish, State, News>() {
        override fun invoke(wish: Wish, state: State): News? = when(wish){
                is Wish.ResetCounter -> News.ToastOnClick(
                    "Counter reset!"
                )
                is Wish.IncreaseCounter -> News.ToastOnClick(
                    "Counter increased!"
                )
        }
    }
}
