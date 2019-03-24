package de.marxhendrik.healthcheckcards.dagger

import dagger.Module
import dagger.android.AndroidInjectionModule
import de.marxhendrik.healthcheckcards.feature.threecards.dagger.CardsActivityBuildersModule
import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

@Scope
@Retention(RUNTIME)
annotation class PerApplication


/**
 * Base Module. Add other Application scoped modules into includes parameter, split them by Feature
 */
@Module(includes = [AndroidInjectionModule::class, CardsActivityBuildersModule::class])
class AppModule
