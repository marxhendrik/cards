package de.marxhendrik.healthcheckcards.feature.threecards.ui

import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract.View
import java.util.concurrent.TimeUnit

//Unit Tests FIXME
class ThreeCardsPresenter(val view: ThreeCardsContract.View) : ThreeCardsContract.Presenter {
    override fun start() {

        //manage subcscription (try AAC) FIXME
        view.getClicks()
                .throttleFirst(ANIMATION_DURATION_MS, TimeUnit.MILLISECONDS)
                .subscribe(this::onCardClicked)
    }

    private fun onCardClicked(card: View) {
        if (card.centered) {
            unCenter(card)
        } else {
            center(card)
        }

        card.centered = !card.centered
    }


    private fun unCenter(card: View) {
        view.animateTranslateX(card = card) {
            viewsToTheRightOfCardDo(card = card) {
                view.animateTranslateX(card = it, delay = ANIMATION_DURATION_MS)
            }
        }

        view.animateTranslateZ(card, delay = ANIMATION_DURATION_MS)
    }

    private fun center(card: View) {
        viewsToTheRightOfCardDo(card = card) {
            view.animateTranslateX(card = it, translation = it.getOutRightTranslation())
        }
        view.animateTranslateZ(card = card, translation = CENTERED_Z_TRANSLATION, delay = ANIMATION_DURATION_MS)
        view.animateTranslateX(card = card, translation = card.getCenterTranslation(), delay = ANIMATION_DURATION_MS)
    }

    private fun viewsToTheRightOfCardDo(card: View, func: (View) -> Unit) {
        view.cards
                .filter { card != it }
                .filter { view.isToRightOf(card, it) }
                .forEach {
                    func(it)
                }
    }
}