package marxhendrik.de.healthcheckcards.dagger

import dagger.Module
import dagger.android.AndroidInjectionModule
import marxhendrik.de.healthcheckcards.feature.ui.CardsActivityBuildersModule
import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

@Scope
@Retention(RUNTIME)
annotation class PerApplication


@PerApplication
@Module(includes = [AndroidInjectionModule::class, CardsActivityBuildersModule::class])
class AppModule