package de.marxhendrik.healthcheckcards.feature.singlecard.ui

interface SingleCardContract {
    interface Presenter

    interface View {
        fun animateToOriginalX(delay: Long = 0, func: () -> Unit = {})
        fun animateCenter()
        fun animateToFront()
        fun animateToBack()
        fun animateOutRight(delay: Long = 0)
        fun getIndex(): Int
    }
}
