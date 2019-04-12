package de.marxhendrik.healthcheckcards.feature.singlecard.network

import android.support.annotation.IntRange

/**
 * Fake Api
 */
class ColorApi {

    private val colorMap = mapOf(0 to Color.GREEN, 1 to Color.YELLOW, 2 to Color.RED)

    fun getColor(@IntRange(from = 0, to = 2) index: Int) = colorMap[index] ?: throw IllegalArgumentException("only index 1 to 3 accepted but got $index")

    enum class Color {
        GREEN,
        RED,
        YELLOW
    }
}
