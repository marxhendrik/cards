package de.marxhendrik.healthcheckcards.feature.threecards.ui

import com.jakewharton.rxrelay2.PublishRelay
import de.marxhendrik.healthcheckcards.base.LifecycleAwarePresenter
import de.marxhendrik.healthcheckcards.base.ViewLifecycle
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardAnimationCommand
import java.util.concurrent.TimeUnit

class ThreeCardsPresenter(
    private val view: ThreeCardsContract.View,
    private val animationCommandRelay: PublishRelay<SingleCardAnimationCommand>,
    lifecycle: ViewLifecycle
) : LifecycleAwarePresenter(), ThreeCardsContract.Presenter {


    init {
        lifecycle.register(this)
    }

    override fun onStart() {
        view.getClicks()
            .throttleFirst(ANIMATION_DELAY_MS, TimeUnit.MILLISECONDS)
            .subscribe(this::onCardClicked)
            .manage()
    }

    private fun onCardClicked(cardIndex: Int) {
        animationCommandRelay.accept(SingleCardAnimationCommand(cardIndex))
    }
}
