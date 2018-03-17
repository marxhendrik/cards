package de.marxhendrik.healthcheckcards.feature.dagger

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.marxhendrik.healthcheckcards.dagger.SubComponentBuilderMap
import de.marxhendrik.healthcheckcards.feature.ui.ThreeCardsActivity

@Module(subcomponents = [ThreeCardsComponent::class])
class ThreeCardsActivityModule {
    @Provides
    fun provideSubComponentBuilders(builder: ThreeCardsComponent.Builder): SubComponentBuilderMap {
        val subComponentBuilderMap = SubComponentBuilderMap()
        subComponentBuilderMap[ThreeCardsComponent.Builder::class] = builder
        return subComponentBuilderMap
    }
}

@Module
abstract class CardsActivityBuildersModule {
    @ContributesAndroidInjector(modules = [ThreeCardsActivityModule::class])
    abstract fun bind(): ThreeCardsActivity
}