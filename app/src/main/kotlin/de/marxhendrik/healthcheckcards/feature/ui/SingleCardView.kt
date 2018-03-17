package de.marxhendrik.healthcheckcards.feature.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View

class SingleCardView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, style: Int = 0) : View(context, attr, style) {
    lateinit var card: ThreeCardsContract.Card
    var centered: Boolean = false

    fun getCenterTranslation(): Float {
        val centerX = (left + right) / 2
        val screenCenter = resources.displayMetrics.widthPixels / 2

        return screenCenter.toFloat() - centerX
    }
}