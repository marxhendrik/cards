package marxhendrik.de.healthcheckcards.feature.ui

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import marxhendrik.de.healthcheckcards.dagger.SubComponentBuilderMap

@Module(subcomponents = [CardsComponent::class])
class CardsActivityModule {
    @Provides
    fun provideSubComponentBuilders(builder: CardsComponent.Builder): SubComponentBuilderMap {
        val subComponentBuilderMap = SubComponentBuilderMap()
        subComponentBuilderMap[CardsComponent.Builder::class] = builder
        return subComponentBuilderMap
    }
}

@Module(subcomponents = [CardsComponent::class])
abstract class CardsActivityBuildersModule {
    @ContributesAndroidInjector(modules = [CardsActivityModule::class])
    abstract fun binds(): CardsActivity
}