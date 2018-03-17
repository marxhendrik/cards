package de.marxhendrik.healthcheckcards.feature.threecards.dagger

import dagger.Module
import dagger.Provides
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsContract
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsPresenter

@Module
class ThreeCardsModule {

    @Provides
    fun providePresenter(view: ThreeCardsContract.View): ThreeCardsContract.Presenter {
        return ThreeCardsPresenter(view)
    }
}