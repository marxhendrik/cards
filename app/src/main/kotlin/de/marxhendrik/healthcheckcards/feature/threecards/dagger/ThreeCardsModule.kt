package de.marxhendrik.healthcheckcards.feature.threecards.dagger

import android.arch.lifecycle.LifecycleOwner
import dagger.Module
import dagger.Provides
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsContract
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsPresenter

@Module
class ThreeCardsModule {

    @Provides
    fun providePresenter(view: ThreeCardsContract.View, lifecycleOwner: LifecycleOwner): ThreeCardsContract.Presenter {
        return ThreeCardsPresenter(view, lifecycleOwner)
    }
}