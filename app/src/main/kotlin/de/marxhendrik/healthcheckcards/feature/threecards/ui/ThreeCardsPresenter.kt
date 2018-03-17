package de.marxhendrik.healthcheckcards.feature.threecards.ui

import java.util.concurrent.TimeUnit

class ThreeCardsPresenter(val view: ThreeCardsContract.View) : ThreeCardsContract.Presenter {
    override fun start() {

        //manage subcscription (try AAC) FIXME
        view.clicks
                .throttleFirst(ANIMATION_DURATION_MS, TimeUnit.MILLISECONDS)
                .subscribe {
                    view.showFullScreen(it)
                }
    }
}