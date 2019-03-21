package de.marxhendrik.healthcheckcards.feature.singlecard.ui

import com.jakewharton.rxrelay2.Relay
import de.marxhendrik.healthcheckcards.base.LifecycleAwarePresenter
import de.marxhendrik.healthcheckcards.base.ViewLifecycle

class SingleCardPresenter(
    private val animationCommandRelay: Relay<SingleCardAnimationCommand>,
    private val view: SingleCardContract.View,
    viewLifecycle: ViewLifecycle
) : SingleCardContract.Presenter, LifecycleAwarePresenter() {

    private var centered: Boolean = false
    private val cardIndex: Int = view.getIndex()

    init {
        viewLifecycle.register(this)
    }

    override fun onStart() {
        animationCommandRelay
            .subscribe {
                animate(it.index)
            }.manage()
    }

    private fun animate(index: Int) {
        if (cardIndex == index) {
            toggleCentered()
        } else {
            unCenter()
        }
    }

    private fun toggleCentered() {
        if (centered) {
            unCenter()
        } else {
            center()
        }

        centered = !centered
    }

    private fun unCenter() {
        view.animateToOriginalX()
        view.animateToBack()
    }

    private fun center() {
        view.animateToFront()
        view.animateCenter()
    }

}

data class SingleCardAnimationCommand(val index: Int)

