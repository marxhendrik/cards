package de.marxhendrik.healthcheckcards.feature.singlecard.dagger

import dagger.Module
import dagger.Provides
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardPresenter
import de.marxhendrik.healthcheckcards.feature.threecards.dagger.ScreenWidthPx

@Module
class SingleCardModule {

    @Provides
    fun providePresenter(@ScreenWidthPx screenWidth: Float, view: SingleCardContract.View): SingleCardContract.Presenter {
        return SingleCardPresenter(screenWidth, view)
    }
}