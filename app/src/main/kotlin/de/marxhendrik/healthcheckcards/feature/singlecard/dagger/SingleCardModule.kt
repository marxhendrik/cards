package de.marxhendrik.healthcheckcards.feature.singlecard.dagger

import android.arch.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import de.marxhendrik.healthcheckcards.base.ViewLifecycle
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardAnimationCommand
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardPresenter
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardView

@Module
object SingleCardModule {

    @Provides
    @JvmStatic
    fun providePresenter(
        view: SingleCardView,
        animationCommandRelay: PublishRelay<SingleCardAnimationCommand>,
        viewLifecycle: ViewLifecycle
    ): SingleCardContract.Presenter {
        return SingleCardPresenter(animationCommandRelay, view, viewLifecycle)
    }


    @Provides
    @JvmStatic
    fun provideLifecycle(view: SingleCardView, lifecycleOwner: LifecycleOwner) = ViewLifecycle(view, lifecycleOwner)
}
