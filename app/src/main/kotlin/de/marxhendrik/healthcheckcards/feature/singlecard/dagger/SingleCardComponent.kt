package de.marxhendrik.healthcheckcards.feature.singlecard.dagger

import dagger.BindsInstance
import dagger.Subcomponent
import de.marxhendrik.healthcheckcards.dagger.SubComponentBuilder
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardView

@Subcomponent(modules = [SingleCardModule::class])
interface SingleCardComponent {

    fun inject(cardsView: SingleCardView)

    @Subcomponent.Builder
    interface Builder : SubComponentBuilder {
        @BindsInstance
        fun view(view: SingleCardContract.View): Builder

        fun build(): SingleCardComponent
    }
}