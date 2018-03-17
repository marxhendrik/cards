package de.marxhendrik.healthcheckcards.feature.threecards.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.jakewharton.rxbinding2.view.RxView
import de.marxhendrik.healthcheckcards.dagger.InjectingView
import de.marxhendrik.healthcheckcards.dagger.getSubComponentBuilder
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardView
import de.marxhendrik.healthcheckcards.feature.threecards.dagger.ThreeCardsComponent
import de.marxhendrik.healthcheckcards.feature.threecards.extensions.animateTranslate
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_card_green.view.*
import kotlinx.android.synthetic.main.view_card_orange.view.*
import kotlinx.android.synthetic.main.view_card_red.view.*
import javax.inject.Inject

class ThreeCardsView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, style: Int = 0) :
        FrameLayout(context, attr, style),
        InjectingView,
        ThreeCardsContract.View {

    @Inject
    lateinit var presenter: ThreeCardsContract.Presenter


    override val cards: Set<SingleCardContract.View> by lazy {
        setOf(vCardGreen, vCardOrange, vCardRed)
    }

    init {
        if (!isInEditMode) {
            getSubComponentBuilder(ThreeCardsComponent.Builder::class)
                    .view(this)
                    .build()
                    .inject(this)
        }
    }

    override fun getClicks(): Observable<SingleCardContract.View> = Observable.merge(
            cardClicks(vCardGreen),
            cardClicks(vCardOrange),
            cardClicks(vCardRed)
    )

    private fun cardClicks(view: SingleCardView) = RxView.clicks(view).map { view }

    override fun animateTranslateZ(card: SingleCardContract.View, translation: Float, animationDuration: Long, delay: Long) {
        (card as SingleCardView).animateTranslate("Z", translation, animationDuration, delay)
    }

    override fun animateTranslateX(card: SingleCardContract.View, translation: Float, animationDuration: Long, delay: Long, function: () -> Unit) {
        (card as SingleCardView).animateTranslate("X", translation, delay, animationDuration, callbackFunc = function)
    }
}


