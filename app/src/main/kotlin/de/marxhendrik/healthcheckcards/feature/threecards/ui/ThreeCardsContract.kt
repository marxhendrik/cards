package de.marxhendrik.healthcheckcards.feature.threecards.ui

import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract
import io.reactivex.Observable

const val ANIMATION_DELAY_MS = 300L
const val CENTERED_Z_TRANSLATION = 100f


interface ThreeCardsContract {
    interface Presenter

    interface View {
        fun getClicks(): Observable<SingleCardContract.View>
        val cards: Set<SingleCardContract.View>
    }
}
