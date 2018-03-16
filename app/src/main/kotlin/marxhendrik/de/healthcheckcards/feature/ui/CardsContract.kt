package marxhendrik.de.healthcheckcards.feature.ui

import io.reactivex.Observable

interface CardsContract {
    interface Presenter {
        fun start()
    }

    interface View {
        val clicks: Observable<CardsView.Card>
        fun showFullScreen(it: CardsView.Card)
    }

}