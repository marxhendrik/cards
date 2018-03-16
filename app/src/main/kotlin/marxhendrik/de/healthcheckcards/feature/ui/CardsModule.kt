package marxhendrik.de.healthcheckcards.feature.ui

import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.ContributesAndroidInjector
import marxhendrik.de.healthcheckcards.dagger.SubComponentBuilder
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

@Module
class CardsModule {

    @Provides
    fun providePresenter(view: CardsContract.View): CardsContract.Presenter {
        return CardsPresenter(view)
    }
}

@Subcomponent(modules = [CardsModule::class])
interface CardsComponent {

    fun inject(cardsView: CardsView)

    @Subcomponent.Builder
    interface Builder : SubComponentBuilder {
        @BindsInstance
        fun view(view: CardsContract.View): Builder

        fun build(): CardsComponent
    }
}