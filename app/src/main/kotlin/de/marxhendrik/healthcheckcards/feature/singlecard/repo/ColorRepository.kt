package de.marxhendrik.healthcheckcards.feature.singlecard.repo

import de.marxhendrik.healthcheckcards.feature.singlecard.network.ColorApi

class ColorRepository(private val api: ColorApi) {

    fun getColor(index: Int): ColorApi.Color {
        return api.getColor(index)
    }
}
