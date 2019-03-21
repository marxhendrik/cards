package de.marxhendrik.healthcheckcards.feature.singlecard.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import de.marxhendrik.healthcheckcards.R
import de.marxhendrik.healthcheckcards.dagger.InjectingView
import de.marxhendrik.healthcheckcards.dagger.getComponentBuilder
import de.marxhendrik.healthcheckcards.feature.singlecard.dagger.SingleCardComponent
import de.marxhendrik.healthcheckcards.feature.threecards.extensions.animateTranslateX
import de.marxhendrik.healthcheckcards.feature.threecards.extensions.animateTranslateZ
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ANIMATION_DELAY_MS
import de.marxhendrik.healthcheckcards.feature.threecards.ui.CENTERED_Z_TRANSLATION
import javax.inject.Inject


class SingleCardView @JvmOverloads constructor(context: Context, private val attr: AttributeSet? = null, style: Int = 0) :
    View(context, attr, style), SingleCardContract.View, InjectingView {

    @Inject
    lateinit var presenter: SingleCardContract.Presenter

    private var screenWidth: Float = resources.displayMetrics.widthPixels.toFloat()

    init {
        getComponentBuilder<SingleCardComponent.Builder>()
            .view(this)
            .build()
            .inject(this)

    }

    private fun getCenterTranslation(): Float {
        val centerX = (left + right) / 2
        val screenCenter = screenWidth / 2
        return screenCenter - centerX
    }

    private fun getOutRightTranslation() = screenWidth - left


    override fun animateToOriginalX(delay: Long, func: () -> Unit) =
        this.animateTranslateX(delay = delay, callbackFunc = func)

    override fun animateCenter() =
        animateTranslateX(translation = getCenterTranslation(), delay = ANIMATION_DELAY_MS)

    override fun animateToFront() =
        animateTranslateZ(translation = CENTERED_Z_TRANSLATION, delay = ANIMATION_DELAY_MS)

    override fun animateToBack() =
        animateTranslateZ(delay = ANIMATION_DELAY_MS)

    override fun animateOutRight(delay: Long) =
        animateTranslateX(translation = getOutRightTranslation(), delay = delay)

    override fun getIndex(): Int {
        val typedArray = context.theme.obtainStyledAttributes(attr, R.styleable.SingleCardView, 0, 0)
        val value = typedArray.getInteger(R.styleable.SingleCardView_cardIndex, 0)
        typedArray.recycle()
        return value
    }
}
