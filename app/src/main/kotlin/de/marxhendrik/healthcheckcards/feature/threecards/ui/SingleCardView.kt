package de.marxhendrik.healthcheckcards.feature.threecards.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View

class SingleCardView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, style: Int = 0) : View(context, attr, style) {

    lateinit var card: ThreeCardsContract.Card

    fun getCenterTranslation(): Float {
        val centerX = (left + right) / 2
        val screenCenter = resources.displayMetrics.widthPixels / 2
        return screenCenter.toFloat() - centerX
    }

    fun isToRightOf(cardView: SingleCardView) = getCenterTranslation() - cardView.getCenterTranslation() <= 0

    fun getOutRightTranslation() = resources.displayMetrics.widthPixels - left.toFloat()
}