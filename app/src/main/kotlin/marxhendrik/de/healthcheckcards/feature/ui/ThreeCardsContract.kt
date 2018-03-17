package marxhendrik.de.healthcheckcards.feature.ui

import io.reactivex.Observable

interface ThreeCardsContract {
    interface Presenter {
        fun start()
    }

    interface View {
        val clicks: Observable<Card>
        fun showFullScreen(card: Card)
    }


    enum class Card {
        GREEN, ORANGE, RED
    }
}