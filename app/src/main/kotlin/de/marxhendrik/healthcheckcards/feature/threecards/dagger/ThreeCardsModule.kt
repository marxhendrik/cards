package de.marxhendrik.healthcheckcards.feature.threecards.dagger

import android.arch.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import de.marxhendrik.healthcheckcards.base.ViewLifecycle
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardAnimationCommand
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsContract
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsPresenter
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsView

@Module
object ThreeCardsModule {

    @Provides
    @JvmStatic
    fun providePresenter(
        view: ThreeCardsView,
        lifecycle: ViewLifecycle,
        animationCommandRelay: PublishRelay<SingleCardAnimationCommand>
    ): ThreeCardsContract.Presenter {
        return ThreeCardsPresenter(view, animationCommandRelay, lifecycle)
    }


    @Provides
    @JvmStatic
    fun provideLifecycle(view: ThreeCardsView, lifecycleOwner: LifecycleOwner) = ViewLifecycle(view, lifecycleOwner)
}
