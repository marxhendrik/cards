package de.marxhendrik.healthcheckcards.feature.threecards.ui

import io.reactivex.Observable

interface ThreeCardsContract {
    interface Presenter {
        fun start()
    }

    interface View {
        fun getClicks(): Observable<Card>
        fun center(card: Card)
        fun unCenter(card: Card)
    }


    sealed class Card(var centered: Boolean = false) {
        class Green : Card()
        class Orange : Card()
        class Red : Card()
    }
}