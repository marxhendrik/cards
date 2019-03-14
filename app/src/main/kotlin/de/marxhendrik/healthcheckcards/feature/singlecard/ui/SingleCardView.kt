package de.marxhendrik.healthcheckcards.feature.singlecard.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import de.marxhendrik.healthcheckcards.dagger.InjectingView
import de.marxhendrik.healthcheckcards.dagger.getComponentBuilder
import de.marxhendrik.healthcheckcards.feature.singlecard.dagger.SingleCardComponent
import javax.inject.Inject

class SingleCardView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, style: Int = 0) :
    View(context, attr, style), SingleCardContract.View, InjectingView {

    override val left: Float
        get() = getLeft().toFloat()

    override val right: Float
        get() = getRight().toFloat()

    override var centered: Boolean
        get() = presenter.centered
        set(value) {
            presenter.centered = value
        }

    @Inject
    lateinit var presenter: SingleCardContract.Presenter

    init {
        getComponentBuilder<SingleCardComponent.Builder>()
            .view(this)
            .build()
            .inject(this)
    }

    override fun getCenterTranslation() = presenter.getCenterTranslation()

    override fun isToRightOf(card: SingleCardContract.View) = presenter.isToRightOf(card)

    override fun getOutRightTranslation() = presenter.getOutRightTranslation()
}
