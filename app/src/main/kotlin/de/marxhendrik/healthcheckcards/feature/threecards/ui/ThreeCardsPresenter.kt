package de.marxhendrik.healthcheckcards.feature.threecards.ui

import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsContract.Card
import java.util.concurrent.TimeUnit

class ThreeCardsPresenter(val view: ThreeCardsContract.View) : ThreeCardsContract.Presenter {
    override fun start() {

        //manage subcscription (try AAC) FIXME
        view.getClicks()
                .throttleFirst(ANIMATION_DURATION_MS, TimeUnit.MILLISECONDS)
                .subscribe(this::onCardClicked)
    }

    private fun onCardClicked(card: Card) {
        if (card.centered) {
            unCenter(card)
        } else {
            center(card)
        }

        card.centered = !card.centered
    }


    private fun unCenter(card: Card) {
        view.animateTranslateX(card = card) {
            view.viewsToRightOfDo(card = card) {
                view.animateTranslateX(card = it, delay = ANIMATION_DURATION_MS)
            }
        }

        view.animateTranslateZ(card, delay = ANIMATION_DURATION_MS)
    }

    private fun center(card: Card) {
        view.viewsToRightOfDo(card = card) {
            view.animateTranslateX(card = it, translation = it.translationToRightEdge)
        }
        view.animateTranslateZ(card = card, translation = CENTERED_Z_TRANSLATION, delay = ANIMATION_DURATION_MS)
        view.animateTranslateX(card = card, translation = card.translationToCentre, delay = ANIMATION_DURATION_MS)
    }
}