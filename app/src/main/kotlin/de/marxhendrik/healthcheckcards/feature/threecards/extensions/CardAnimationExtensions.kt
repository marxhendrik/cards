package de.marxhendrik.healthcheckcards.feature.threecards.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardView
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ANIMATION_DURATION_MS


fun SingleCardView?.animateTranslate(
        direction: String,
        translation: Float = 0f,
        delay: Long = 0,
        animationDuration: Long = ANIMATION_DURATION_MS,
        callbackFunc: () -> Unit = {}) {

    ObjectAnimator.ofFloat(this, "translation" + direction, translation).apply {
        duration = animationDuration
        startDelay = delay
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                callbackFunc()
            }
        })
        start()
    }
}