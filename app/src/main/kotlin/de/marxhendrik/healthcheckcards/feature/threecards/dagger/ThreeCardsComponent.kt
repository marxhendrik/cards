package de.marxhendrik.healthcheckcards.feature.threecards.dagger

import dagger.BindsInstance
import dagger.Subcomponent
import de.marxhendrik.healthcheckcards.dagger.SubComponentBuilder
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsContract
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsView

@Subcomponent(modules = [ThreeCardsModule::class])
interface ThreeCardsComponent {

    fun inject(cardsView: ThreeCardsView)

    @Subcomponent.Builder
    interface Builder : SubComponentBuilder {
        @BindsInstance
        fun view(view: ThreeCardsContract.View): Builder

        fun build(): ThreeCardsComponent
    }
}