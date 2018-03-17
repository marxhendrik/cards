package de.marxhendrik.healthcheckcards.feature.threecards.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import com.jakewharton.rxbinding2.view.RxView
import de.marxhendrik.healthcheckcards.dagger.InjectingView
import de.marxhendrik.healthcheckcards.dagger.getSubComponentBuilder
import de.marxhendrik.healthcheckcards.feature.threecards.dagger.ThreeCardsComponent
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsContract.Card
import de.marxhendrik.healthcheckcards.log
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_card_green.view.*
import kotlinx.android.synthetic.main.view_card_orange.view.*
import kotlinx.android.synthetic.main.view_card_red.view.*
import javax.inject.Inject

const val ANIMATION_DURATION_MS = 300L
const val CENTERED_Z_TRANSLATION = 100f

class ThreeCardsView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, style: Int = 0) :
        FrameLayout(context, attr, style),
        InjectingView,
        ThreeCardsContract.View {

    @Inject
    lateinit var presenter: ThreeCardsContract.Presenter

    private val cardToView: Map<Card, SingleCardView> by lazy {
        mapOf(vCardGreen.card to vCardGreen, vCardOrange.card to vCardOrange, vCardRed.card to vCardRed)
    }

    init {
        if (!isInEditMode) {
            getSubComponentBuilder(ThreeCardsComponent.Builder::class)
                    .view(this)
                    .build()
                    .inject(this)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (isInEditMode) {
            return
        }

        initViews()

        presenter.start()
    }

    private fun initViews() {
        vCardGreen.card = Card.Green({ vCardGreen.getCenterTranslation() }, { vCardGreen.getOutRightTranslation() })
        vCardOrange.card = Card.Orange({ vCardOrange.getCenterTranslation() }, { vCardOrange.getOutRightTranslation() })
        vCardRed.card = Card.Red({ vCardRed.getCenterTranslation() }, { vCardRed.getOutRightTranslation() })
    }

    override fun getClicks(): Observable<Card> = Observable.merge(
            cardClicks(vCardGreen),
            cardClicks(vCardOrange),
            cardClicks(vCardRed)
    )

    private fun cardClicks(view: SingleCardView) = RxView.clicks(view).map { view.card }


    override fun viewsToRightOfDo(card: Card, function: (Card) -> Unit) {
        val cardView = cardToView.getValue(card)
        log("clicked: $card : $cardView")
        cardToView.values
                .filter { it != cardView }
                .filter { it.isToRightOf(cardView) }
                .forEach {
                    log("    -> to right is: $it")
                    function(it.card)
                }
    }

    override fun animateTranslateZ(card: Card, translation: Float, delay: Long) {
        val cardView = cardToView.getValue(card)
        animateTranslate("Z", cardView, translation, delay)
    }

    override fun animateTranslateX(card: Card, translation: Float, delay: Long, function: () -> Unit) {
        val cardView = cardToView.getValue(card)
        animateTranslate("X", cardView, translation, delay, callbackFunc = function)
    }


    /*FIXME
    1. move animations to a util or something
    2. try out physical animations
    3. make animation cooler (move the other cards from the right out and back in)
     */
    private fun animateTranslate(
            direction: String,
            cardView: View,
            translation: Float = 0f,
            delay: Long = 0,
            animationDuration: Long = ANIMATION_DURATION_MS,
            callbackFunc: () -> Unit = {}) {
        ObjectAnimator.ofFloat(cardView, "translation" + direction, translation).apply {
            duration = animationDuration
            startDelay = delay
            interpolator = AccelerateDecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    callbackFunc()
                }
            })
            start()
        }
    }
}


