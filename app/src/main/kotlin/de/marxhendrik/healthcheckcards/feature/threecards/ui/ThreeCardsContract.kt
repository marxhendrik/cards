package de.marxhendrik.healthcheckcards.feature.threecards.ui

import io.reactivex.Observable

const val ANIMATION_DURATION_MS = 300L
const val CENTERED_Z_TRANSLATION = 100f


interface ThreeCardsContract {
    interface Presenter {
        fun start()
    }

    interface View {
        fun getClicks(): Observable<Card>
        fun animateTranslateX(card: Card, translation: Float = 0f, delay: Long = 0, function: () -> Unit = {})
        fun animateTranslateZ(card: Card, translation: Float = 0f, delay: Long = 0)
        fun getCards(): Set<Card>
        fun isToRightOf(card: Card, otherCard: Card): Boolean
    }


}