package de.marxhendrik.healthcheckcards.feature.singlecard.ui

import android.support.annotation.DrawableRes

interface SingleCardContract {
    interface Presenter

    interface View {
        fun animateToOriginalX(delay: Long = 0, func: () -> Unit = {})
        fun animateCenter()
        fun animateToFront()
        fun animateToBack()
        fun animateOutRight(delay: Long = 0)
        fun getIndex(): Int
        fun setBackground(@DrawableRes drawable: Int)
    }
}
