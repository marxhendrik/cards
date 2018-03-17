package de.marxhendrik.healthcheckcards.feature.threecards.ui

import io.reactivex.Observable

interface ThreeCardsContract {
    interface Presenter {
        fun start()
    }

    interface View {
        fun getClicks(): Observable<Card>
        fun animateTranslateX(card: Card, translation: Float = 0f, delay: Long = 0, function: () -> Unit = {})
        fun viewsToRightOfDo(card: Card, function: (Card) -> Unit = {})
        fun animateTranslateZ(card: Card, translation: Float = 0f, delay: Long = 0)
    }


    sealed class Card(val toCenter: () -> Float, val toEdge: () -> Float, var centered: Boolean = false) {

        val translationToCentre: Float
            get() = toCenter()

        val translationToRightEdge: Float
            get() = toEdge()

        class Green(toCenter: () -> Float, toEdge: () -> Float) : Card(toCenter, toEdge)
        class Orange(toCenter: () -> Float, toEdge: () -> Float) : Card(toCenter, toEdge)
        class Red(toCenter: () -> Float, toEdge: () -> Float) : Card(toCenter, toEdge)
    }
}