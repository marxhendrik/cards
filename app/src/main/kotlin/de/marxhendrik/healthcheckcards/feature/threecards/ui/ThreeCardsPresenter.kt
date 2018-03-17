package de.marxhendrik.healthcheckcards.feature.threecards.ui

class ThreeCardsPresenter(val view: ThreeCardsContract.View) : ThreeCardsContract.Presenter {
    override fun start() {

        //manage subcscription (try AAC) FIXME
        view.clicks.subscribe {
            view.showFullScreen(it)
        }
    }
}