package de.marxhendrik.healthcheckcards.feature.threecards.extensions

import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce.DAMPING_RATIO_NO_BOUNCY
import android.support.animation.SpringForce.STIFFNESS_MEDIUM
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun SingleCardView?.animateTranslateZ(translation: Float = 0f, delay: Long = 0, callbackFunc: () -> Unit = {}) =
        animateTranslate(DynamicAnimation.TRANSLATION_Z, translation, delay, callbackFunc)

fun SingleCardView?.animateTranslateX(translation: Float = 0f, delay: Long = 0, callbackFunc: () -> Unit = {}) =
        animateTranslate(DynamicAnimation.TRANSLATION_X, translation, delay, callbackFunc)


private fun SingleCardView?.animateTranslate(
        direction: DynamicAnimation.ViewProperty,
        translation: Float = 0f,
        delay: Long = 0,
        callbackFunc: () -> Unit = {}) {


    SpringAnimation(this, direction, translation)
            .apply {

                spring.dampingRatio = DAMPING_RATIO_NO_BOUNCY
                spring.stiffness = STIFFNESS_MEDIUM

                addEndListener { _, _, _, _ ->
                    callbackFunc()
                }

                Observable.timer(delay, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { start() }
            }
}