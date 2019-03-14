package de.marxhendrik.healthcheckcards.feature.singlecard.extensions

import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardView
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ANIMATION_DELAY_MS
import de.marxhendrik.healthcheckcards.feature.threecards.ui.CENTERED_Z_TRANSLATION
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


fun SingleCardContract.View.animateToOriginalX(delay: Long = 0, func: () -> Unit = {}) =
    (this as SingleCardView).animateTranslateX(delay = delay, callbackFunc = func)

fun SingleCardContract.View.animateCenter() =
    (this as SingleCardView).animateTranslateX(translation = getCenterTranslation(), delay = ANIMATION_DELAY_MS)

fun SingleCardContract.View.animateToFront() =
    (this as SingleCardView).animateTranslateZ(translation = CENTERED_Z_TRANSLATION, delay = ANIMATION_DELAY_MS)

fun SingleCardContract.View.animateToBack() =
    (this as SingleCardView).animateTranslateZ(delay = ANIMATION_DELAY_MS)

fun SingleCardContract.View.animateOutRight(delay: Long = 0) =
    (this as SingleCardView).animateTranslateX(translation = getOutRightTranslation(), delay = delay)


fun SingleCardView?.animateTranslateZ(translation: Float = 0f, delay: Long = 0, callbackFunc: () -> Unit = {}) =
    animateTranslate(DynamicAnimation.TRANSLATION_Z, translation, delay, callbackFunc)

fun SingleCardView?.animateTranslateX(translation: Float = 0f, delay: Long = 0, callbackFunc: () -> Unit = {}) =
    animateTranslate(DynamicAnimation.TRANSLATION_X, translation, delay, callbackFunc)


private fun SingleCardView?.animateTranslate(
    direction: DynamicAnimation.ViewProperty,
    translation: Float = 0f,
    delay: Long = 0,
    callbackFunc: () -> Unit = {}
) {


    SpringAnimation(this, direction, translation)
        .apply {

            spring.dampingRatio = SpringForce.DAMPING_RATIO_NO_BOUNCY
            spring.stiffness = SpringForce.STIFFNESS_MEDIUM

            addEndListener { _, _, _, _ ->
                callbackFunc()
            }

            Observable.timer(delay, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { start() }
        }
}
