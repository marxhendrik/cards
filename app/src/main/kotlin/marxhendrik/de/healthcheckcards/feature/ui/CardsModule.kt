package marxhendrik.de.healthcheckcards.feature.ui

import dagger.Module
import dagger.Provides

@Module
class CardsModule {

    @Provides
    fun providePresenter(view: CardsContract.View): CardsContract.Presenter {
        return CardsPresenter(view)
    }
}