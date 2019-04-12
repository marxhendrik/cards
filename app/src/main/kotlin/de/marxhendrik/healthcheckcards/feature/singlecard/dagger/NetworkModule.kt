package de.marxhendrik.healthcheckcards.feature.singlecard.dagger

import dagger.Module
import dagger.Provides
import de.marxhendrik.healthcheckcards.feature.singlecard.network.ColorApi

@Module
object NetworkModule {
    @Provides
    @JvmStatic
    fun provideColorApi() = ColorApi()
}
