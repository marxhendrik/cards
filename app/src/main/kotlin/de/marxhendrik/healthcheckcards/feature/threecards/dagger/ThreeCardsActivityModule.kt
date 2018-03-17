package de.marxhendrik.healthcheckcards.feature.threecards.dagger

import android.arch.lifecycle.LifecycleOwner
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.marxhendrik.healthcheckcards.dagger.SubComponentBuilderMap
import de.marxhendrik.healthcheckcards.feature.singlecard.dagger.SingleCardComponent
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsActivity
import javax.inject.Qualifier

@Module(subcomponents = [ThreeCardsComponent::class, SingleCardComponent::class])
class ThreeCardsActivityModule {
    @Provides
    fun provideSubComponentBuilders(threeCardsBuilder: ThreeCardsComponent.Builder, singleCardsBuilder: SingleCardComponent.Builder): SubComponentBuilderMap {
        val subComponentBuilderMap = SubComponentBuilderMap()
        subComponentBuilderMap[ThreeCardsComponent.Builder::class] = threeCardsBuilder
        subComponentBuilderMap[SingleCardComponent.Builder::class] = singleCardsBuilder
        return subComponentBuilderMap
    }

    @Provides
    @ScreenWidthPx
    fun provideScreenWidth(activity: ThreeCardsActivity): Float {
        return activity.resources.displayMetrics.widthPixels.toFloat()
    }

    @Provides
    fun provideLifeCycleOwner(activity: ThreeCardsActivity): LifecycleOwner = activity
}

@Module
abstract class CardsActivityBuildersModule {
    @ContributesAndroidInjector(modules = [ThreeCardsActivityModule::class])
    abstract fun bind(): ThreeCardsActivity
}

@Qualifier
annotation class ScreenWidthPx