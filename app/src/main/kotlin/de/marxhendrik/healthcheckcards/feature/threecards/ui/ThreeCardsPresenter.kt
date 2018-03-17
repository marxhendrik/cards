package de.marxhendrik.healthcheckcards.feature.threecards.ui

import android.arch.lifecycle.LifecycleOwner
import de.marxhendrik.healthcheckcards.base.LifecycleAwarePresenter
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract
import java.util.concurrent.TimeUnit

//Unit Tests FIXME
class ThreeCardsPresenter(
        val view: ThreeCardsContract.View,
        lifecycleOwner: LifecycleOwner) : LifecycleAwarePresenter(lifecycleOwner), ThreeCardsContract.Presenter {


    override fun onStart() {
        addDisposable(view.getClicks()
                .throttleFirst(ANIMATION_DURATION_MS, TimeUnit.MILLISECONDS)
                .subscribe(this::onCardClicked))
    }

    private fun onCardClicked(card: SingleCardContract.View) {
        if (card.centered) {
            unCenter(card)
        } else {
            center(card)
        }

        card.centered = !card.centered
    }


    private fun unCenter(card: SingleCardContract.View) {
        card.animateToOriginalX {
            card.forEachViewOnRightSide {
                it.animateToOriginalX(delay = ANIMATION_DURATION_MS)
            }
        }
        card.animateToBack()
    }

    private fun center(card: SingleCardContract.View) {
        card.forEachViewOnRightSide {
            it.animateOutRight()
        }
        card.animateToFront()
        card.animateCenter()
    }

    private fun SingleCardContract.View.forEachViewOnRightSide(func: (SingleCardContract.View) -> Unit) {
        view.cards
                .filter { this != it }
                .filter { it.isToRightOf(this) }
                .forEach {
                    func(it)
                }
    }

    private fun SingleCardContract.View.animateToOriginalX(delay: Long = 0, func: () -> Unit = {}) =
            view.animateTranslateX(card = this, animationDuration = ANIMATION_DURATION_MS, delay = delay, function = func)

    private fun SingleCardContract.View.animateCenter() =
            view.animateTranslateX(card = this, translation = this.getCenterTranslation(), animationDuration = ANIMATION_DURATION_MS, delay = ANIMATION_DURATION_MS)

    private fun SingleCardContract.View.animateToFront() =
            view.animateTranslateZ(card = this, translation = CENTERED_Z_TRANSLATION, animationDuration = ANIMATION_DURATION_MS, delay = ANIMATION_DURATION_MS)

    private fun SingleCardContract.View.animateToBack() =
            view.animateTranslateZ(card = this, animationDuration = ANIMATION_DURATION_MS, delay = ANIMATION_DURATION_MS)

    private fun SingleCardContract.View.animateOutRight(delay: Long = 0) =
            view.animateTranslateX(card = this, animationDuration = ANIMATION_DURATION_MS, delay = delay, translation = this.getOutRightTranslation())
}