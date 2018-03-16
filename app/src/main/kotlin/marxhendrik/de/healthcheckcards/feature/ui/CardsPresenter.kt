package marxhendrik.de.healthcheckcards.feature.ui

import marxhendrik.de.healthcheckcards.log

class CardsPresenter(val view: CardsContract.View) : CardsContract.Presenter {
    override fun start() {
        log("presenter start")
    }
}