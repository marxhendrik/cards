package de.marxhendrik.healthcheckcards.feature.singlecard.dagger

import dagger.BindsInstance
import dagger.Subcomponent
import de.marxhendrik.healthcheckcards.dagger.SubComponentBuilder
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardView
import javax.inject.Scope

@Per1Card
@Subcomponent(modules = [SingleCardModule::class])
interface SingleCardComponent {

    fun inject(cardsView: SingleCardView)

    @Subcomponent.Builder
    interface Builder : SubComponentBuilder {
        @BindsInstance
        fun view(view: SingleCardView): Builder

        fun build(): SingleCardComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class Per1Card
