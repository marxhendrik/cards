package de.marxhendrik.healthcheckcards.feature.singlecard.dagger

import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardAnimationCommand
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardPresenter

@Module
object SingleCardModule {

    @Provides
    @JvmStatic
    fun providePresenter(
        view: SingleCardContract.View,
        animationCommandRelay: PublishRelay<SingleCardAnimationCommand>
    ): SingleCardContract.Presenter {
        return SingleCardPresenter(animationCommandRelay, view)
    }
}
