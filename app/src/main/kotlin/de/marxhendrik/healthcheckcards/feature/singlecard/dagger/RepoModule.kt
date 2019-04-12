package de.marxhendrik.healthcheckcards.feature.singlecard.dagger

import dagger.Module
import dagger.Provides
import de.marxhendrik.healthcheckcards.feature.singlecard.network.ColorApi
import de.marxhendrik.healthcheckcards.feature.singlecard.repo.ColorRepository

@Module
object RepoModule {
    @Provides
    @JvmStatic
    fun provideColorRepo(api: ColorApi) = ColorRepository(api)
}
