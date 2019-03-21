package de.marxhendrik.healthcheckcards.feature.threecards.dagger

import android.arch.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardAnimationCommand
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsContract
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsPresenter

@Module
object ThreeCardsModule {

    @Provides
    @JvmStatic
    fun providePresenter(
        view: ThreeCardsContract.View,
        lifecycleOwner: LifecycleOwner,
        animationCommandRelay: PublishRelay<SingleCardAnimationCommand>
    ): ThreeCardsContract.Presenter {
        return ThreeCardsPresenter(view, animationCommandRelay, lifecycleOwner)
    }
}
