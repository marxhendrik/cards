package marxhendrik.de.healthcheckcards.feature.ui

import dagger.BindsInstance
import dagger.Subcomponent
import marxhendrik.de.healthcheckcards.dagger.SubComponentBuilder

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