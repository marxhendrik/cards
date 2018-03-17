package de.marxhendrik.healthcheckcards.feature.threecards.ui

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
import de.marxhendrik.healthcheckcards.feature.threecards.ui.ThreeCardsContract.Card.*
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_card_green.view.*
import kotlinx.android.synthetic.main.view_card_orange.view.*
import kotlinx.android.synthetic.main.view_card_red.view.*
import javax.inject.Inject

const val ANIMATION_DURATION_MS = 400L
private const val CENTERED_Z_TRANSLATION = 100f

class ThreeCardsView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, style: Int = 0) :
        FrameLayout(context, attr, style),
        InjectingView,
        ThreeCardsContract.View {

    @Inject
    lateinit var presenter: ThreeCardsContract.Presenter

    private val cardToView: Map<Card, SingleCardView> by lazy {
        mapOf(vCardGreen.card to vCardGreen, vCardOrange.card to vCardOrange, vCardRed.card to vCardRed)
    }

    override val clicks: Observable<Card> by lazy {
        Observable.merge(
                cardClicks(vCardGreen),
                cardClicks(vCardOrange),
                cardClicks(vCardRed)
        )
    }

    init {
        if (!isInEditMode) {
            getSubComponentBuilder(ThreeCardsComponent.Builder::class)
                    .view(this)
                    .build()
                    .inject(this)
        }
    }

    private fun cardClicks(view: SingleCardView) = RxView.clicks(view).map { view.card }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (isInEditMode) return

        initViews()
        presenter.start()
    }

    private fun initViews() {
        vCardGreen.card = GREEN
        vCardOrange.card = ORANGE
        vCardRed.card = RED
    }

    override fun showFullScreen(card: Card) {
        animateFullScreen(cardToView.getValue(card))
    }

    //move logic out of here into presenter FIXME
    private fun animateFullScreen(cardView: SingleCardView) {
        val currentlyCentered: SingleCardView? = cardToView.values.firstOrNull { it.centered }

        if (cardView == currentlyCentered) {
            unCenter(cardView)
        } else {
            center(cardView)
        }
    }

    private fun unCenter(cardView: SingleCardView) {
        animateTranslateX(cardView = cardView, translation = 0.0f)
        animateTranslateZ(cardView = cardView, translation = 0.0f, delay = ANIMATION_DURATION_MS)

        viewsToRightOfDo(cardView) {
            animateTranslateX(it, 0f, delay = 2 * ANIMATION_DURATION_MS, animationDuration = ANIMATION_DURATION_MS)
        }

        cardView.centered = false
    }

    private fun center(cardView: SingleCardView) {
        cardView.centered = true
        viewsToRightOfDo(cardView) {
            animateTranslateX(it, it.getOutRightTranslation(), animationDuration = ANIMATION_DURATION_MS)
        }
        animateTranslateZ(cardView = cardView, translation = CENTERED_Z_TRANSLATION, delay = ANIMATION_DURATION_MS)
        animateTranslateX(cardView = cardView, translation = cardView.getCenterTranslation(), delay = ANIMATION_DURATION_MS)
    }

    private inline fun viewsToRightOfDo(cardView: SingleCardView, func: (SingleCardView) -> Unit) {
        cardToView.values
                .filter { it != cardView }
                .filter { it.isToRightOf(cardView) }
                .forEach {
                    func(it)
                }
    }

    private fun animateTranslateX(cardView: View, translation: Float, delay: Long = 0, animationDuration: Long = ANIMATION_DURATION_MS) {
        animateTranslate("X", cardView, translation, delay, animationDuration)
    }

    private fun animateTranslateZ(cardView: View, translation: Float, delay: Long = 0, animationDuration: Long = ANIMATION_DURATION_MS) {
        animateTranslate("Z", cardView, translation, delay, animationDuration)
    }

    /*FIXME
    1. move animations to a util or something
    2. try out physical animations
    3. make animation cooler (move the other cards from the right out and back in)
     */
    private fun animateTranslate(direction: String, cardView: View, translation: Float, delay: Long = 0, animationDuration: Long = ANIMATION_DURATION_MS) {
        ObjectAnimator.ofFloat(cardView, "translation" + direction, translation).apply {
            duration = animationDuration
            startDelay = delay
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }
}


