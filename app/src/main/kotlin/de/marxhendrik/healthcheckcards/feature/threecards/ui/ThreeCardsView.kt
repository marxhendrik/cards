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

    private val cards: List<SingleCardContract.View> by lazy {
        listOf(vCardGreen, vCardOrange, vCardRed)
    }

    init {
        if (!isInEditMode) {
            getComponentBuilder<ThreeCardsComponent.Builder>()
                .view(this)
                .build()
                .inject(this)
        }
    }

    override fun getClicks(): Observable<Int> = Observable.merge(
        cardClicks(vCardGreen),
        cardClicks(vCardOrange),
        cardClicks(vCardRed)
    ).map { cards.indexOf(it) }

    private fun cardClicks(view: SingleCardView) = RxView.clicks(view).map { view }

}

//FIXME
// long term:
// * fix animations (cards should go out to the side and not pop in on top of the others
