package de.marxhendrik.healthcheckcards.feature.threecards.ui

sealed class Card(private val toCenter: () -> Float, private val toEdge: () -> Float, var centered: Boolean = false) {

    val translationToCentre: Float
        get() = toCenter()

    val translationToRightEdge: Float
        get() = toEdge()

    class Green(toCenter: () -> Float, toEdge: () -> Float) : Card(toCenter, toEdge)
    class Orange(toCenter: () -> Float, toEdge: () -> Float) : Card(toCenter, toEdge)
    class Red(toCenter: () -> Float, toEdge: () -> Float) : Card(toCenter, toEdge)
}