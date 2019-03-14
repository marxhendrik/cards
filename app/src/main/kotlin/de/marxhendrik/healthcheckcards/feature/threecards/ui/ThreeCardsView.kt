package de.marxhendrik.healthcheckcards.feature.threecards.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.jakewharton.rxbinding2.view.RxView
import de.marxhendrik.healthcheckcards.R
import de.marxhendrik.healthcheckcards.dagger.InjectingView
import de.marxhendrik.healthcheckcards.dagger.getComponentBuilder
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardView
import de.marxhendrik.healthcheckcards.feature.threecards.dagger.ThreeCardsComponent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_card_green.view.vCardGreen
import kotlinx.android.synthetic.main.view_card_orange.view.vCardOrange
import kotlinx.android.synthetic.main.view_card_red.view.vCardRed
import javax.inject.Inject

class ThreeCardsView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, style: Int = 0) :
    FrameLayout(context, attr, style),
    InjectingView,
    ThreeCardsContract.View {

    @Inject
    lateinit var presenter: ThreeCardsContract.Presenter

    override fun onFinishInflate() {
        super.onFinishInflate()
        View.inflate(context, R.layout.view_cards_content, this)
    }

    override val cards: Set<SingleCardContract.View> by lazy {
        setOf(vCardGreen, vCardOrange, vCardRed)
    }

    init {
        if (!isInEditMode) {
            getComponentBuilder<ThreeCardsComponent.Builder>()
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

}

//FIXME Single card animations need to be done here and in presenter, card view should not know other cards, presenter should not know singlecard interface, invent some other
// excuse for the presenter of singleclard (maybe just set the color?)
// check layout inflation stuff
