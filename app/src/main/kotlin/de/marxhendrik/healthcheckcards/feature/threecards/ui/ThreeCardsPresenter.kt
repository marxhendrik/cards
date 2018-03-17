package de.marxhendrik.healthcheckcards.feature.threecards.ui

import java.util.concurrent.TimeUnit

class ThreeCardsPresenter(val view: ThreeCardsContract.View) : ThreeCardsContract.Presenter {
    override fun start() {

        //manage subcscription (try AAC) FIXME
        view.getClicks()
                .throttleFirst(ANIMATION_DURATION_MS, TimeUnit.MILLISECONDS)
                .subscribe(this::onCardClicked)
    }

    private fun onCardClicked(it: ThreeCardsContract.Card) {
        if (it.centered) {
            view.unCenter(it)
        } else {
            view.center(it)
        }

        it.centered = !it.centered
    }
}