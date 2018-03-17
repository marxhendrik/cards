package marxhendrik.de.healthcheckcards.feature.ui

class CardsPresenter(val view: ThreeCardsContract.View) : ThreeCardsContract.Presenter {
    override fun start() {

        //manage subcscription (try AAC) FIXME
        view.clicks.subscribe {
            view.showFullScreen(it)
        }
    }
}