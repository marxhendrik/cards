package marxhendrik.de.healthcheckcards.feature.ui

class CardsPresenter(val view: CardsContract.View) : CardsContract.Presenter {
    override fun start() {

        //manage subcscription (try AAC) FIXME
        view.clicks.subscribe {
            view.showFullScreen(it)
        }
    }
}