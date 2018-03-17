package de.marxhendrik.healthcheckcards.feature.singlecard.ui

interface SingleCardContract {
    interface Presenter {
        var centered: Boolean
        fun getCenterTranslation(): Float
        fun isToRightOf(card: View): Boolean
        fun getOutRightTranslation(): Float
    }

    interface View {
        val left: Float
        val right: Float
        var centered: Boolean
        fun getCenterTranslation(): Float
        fun isToRightOf(card: View): Boolean
        fun getOutRightTranslation(): Float
    }
}