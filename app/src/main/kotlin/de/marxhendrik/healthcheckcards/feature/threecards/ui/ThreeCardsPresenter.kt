package de.marxhendrik.healthcheckcards.feature.threecards.ui

import android.arch.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay2.PublishRelay
import de.marxhendrik.healthcheckcards.base.LifecycleAwarePresenter
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardAnimationCommand
import java.util.concurrent.TimeUnit

class ThreeCardsPresenter(
    private val view: ThreeCardsContract.View,
    private val animationCommandRelay: PublishRelay<SingleCardAnimationCommand>,
    lifecycleOwner: LifecycleOwner
) : LifecycleAwarePresenter(lifecycleOwner), ThreeCardsContract.Presenter {


    override fun onStart() {
        addDisposable(
            view.getClicks()
                .throttleFirst(ANIMATION_DELAY_MS, TimeUnit.MILLISECONDS)
                .subscribe(this::onCardClicked)
        )
    }

    private fun onCardClicked(cardIndex: Int) {
        animationCommandRelay.accept(SingleCardAnimationCommand(cardIndex))
    }
}
