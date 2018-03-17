package de.marxhendrik.healthcheckcards.feature.ui

import dagger.Module
import dagger.Provides

@Module
class CardsModule {

    @Provides
    fun providePresenter(view: ThreeCardsContract.View): ThreeCardsContract.Presenter {
        return CardsPresenter(view)
    }
}