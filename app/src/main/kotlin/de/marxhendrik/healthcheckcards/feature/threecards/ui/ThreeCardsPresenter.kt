package de.marxhendrik.healthcheckcards.feature.threecards.ui

import android.arch.lifecycle.LifecycleOwner
import de.marxhendrik.healthcheckcards.base.LifecycleAwarePresenter
import de.marxhendrik.healthcheckcards.feature.singlecard.extensions.animateCenter
import de.marxhendrik.healthcheckcards.feature.singlecard.extensions.animateOutRight
import de.marxhendrik.healthcheckcards.feature.singlecard.extensions.animateToBack
import de.marxhendrik.healthcheckcards.feature.singlecard.extensions.animateToFront
import de.marxhendrik.healthcheckcards.feature.singlecard.extensions.animateToOriginalX
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract
import java.util.concurrent.TimeUnit

class ThreeCardsPresenter(
    private val view: ThreeCardsContract.View,
    lifecycleOwner: LifecycleOwner
) : LifecycleAwarePresenter(lifecycleOwner), ThreeCardsContract.Presenter {


    override fun onStart() {
        addDisposable(
            view.getClicks()
                .throttleFirst(ANIMATION_DELAY_MS, TimeUnit.MILLISECONDS)
                .subscribe(this::onCardClicked)
        )
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
                it.animateToOriginalX(delay = ANIMATION_DELAY_MS)
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
}
