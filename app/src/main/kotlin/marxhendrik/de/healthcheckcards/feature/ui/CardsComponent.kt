package marxhendrik.de.healthcheckcards.feature.ui

import dagger.BindsInstance
import dagger.Subcomponent
import marxhendrik.de.healthcheckcards.dagger.SubComponentBuilder

@Subcomponent(modules = [CardsModule::class])
interface CardsComponent {

    fun inject(cardsView: ThreeCardsView)

    @Subcomponent.Builder
    interface Builder : SubComponentBuilder {
        @BindsInstance
        fun view(view: ThreeCardsContract.View): Builder

        fun build(): CardsComponent
    }
}