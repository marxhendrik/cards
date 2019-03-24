package de.marxhendrik.healthcheckcards.feature.threecards.ui

import io.reactivex.Observable

const val ANIMATION_DELAY_MS = 300L
const val CENTERED_Z_TRANSLATION = 100f

interface ThreeCardsContract {
    interface Presenter

    interface View {
        fun getClicks(): Observable<Int>
    }
}
