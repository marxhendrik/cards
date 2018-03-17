package de.marxhendrik.healthcheckcards.feature.threecards.ui

import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract
import io.reactivex.Observable

const val ANIMATION_DURATION_MS = 300L
const val CENTERED_Z_TRANSLATION = 100f


interface ThreeCardsContract {
    interface Presenter {
        fun start()
    }

    interface View {
        fun getClicks(): Observable<SingleCardContract.View>
        fun animateTranslateX(card: SingleCardContract.View, translation: Float = 0f, delay: Long = 0, function: () -> Unit = {})
        fun animateTranslateZ(card: SingleCardContract.View, translation: Float = 0f, delay: Long = 0)
        val cards: Set<SingleCardContract.View>
        fun isToRightOf(card: SingleCardContract.View, otherCard: SingleCardContract.View): Boolean
    }
}