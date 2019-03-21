package de.marxhendrik.healthcheckcards.feature.threecards.dagger

import android.arch.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.marxhendrik.healthcheckcards.dagger.SubComponentBuilderMap
import de.marxhendrik.healthcheckcards.feature.singlecard.dagger.SingleCardComponent
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardAnimationCommand
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardView
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsActivity
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsView
import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

@Module(subcomponents = [ThreeCardsComponent::class, SingleCardComponent::class])
object ThreeCardsActivityModule {
    @Provides
    @PerActivity
    @JvmStatic
    fun provideSubComponentBuilders(threeCardsBuilder: ThreeCardsComponent.Builder, singleCardsBuilder: SingleCardComponent.Builder): SubComponentBuilderMap {
        val subComponentBuilderMap = SubComponentBuilderMap()
        subComponentBuilderMap[ThreeCardsView::class.java] = threeCardsBuilder
        subComponentBuilderMap[SingleCardView::class.java] = singleCardsBuilder
        return subComponentBuilderMap
    }

    @Provides
    @PerActivity
    @JvmStatic
    fun provideLifeCycleOwner(activity: ThreeCardsActivity): LifecycleOwner = activity

    @Provides
    @PerActivity
    @JvmStatic
    fun provideAnimationCommandRelay(): PublishRelay<SingleCardAnimationCommand> = PublishRelay.create()
}

@Module
abstract class CardsActivityBuildersModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [ThreeCardsActivityModule::class])
    abstract fun bind(): ThreeCardsActivity
}

@Scope
@Retention(RUNTIME)
annotation class PerActivity

