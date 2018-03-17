package de.marxhendrik.healthcheckcards.feature.dagger

import dagger.Module
import dagger.Provides
import de.marxhendrik.healthcheckcards.feature.ui.ThreeCardsContract
import de.marxhendrik.healthcheckcards.feature.ui.ThreeCardsPresenter

@Module
class ThreeCardsModule {

    @Provides
    fun providePresenter(view: ThreeCardsContract.View): ThreeCardsContract.Presenter {
        return ThreeCardsPresenter(view)
    }
}