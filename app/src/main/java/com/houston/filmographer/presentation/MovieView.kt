package com.houston.filmographer.presentation

import com.houston.filmographer.ui.MovieState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MovieView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(state: MovieState)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast(message: String)

}