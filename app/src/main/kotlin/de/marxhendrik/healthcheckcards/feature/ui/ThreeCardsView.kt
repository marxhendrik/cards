package de.marxhendrik.healthcheckcards.feature.ui

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
import de.marxhendrik.healthcheckcards.feature.dagger.ThreeCardsComponent
import de.marxhendrik.healthcheckcards.feature.ui.ThreeCardsContract.Card
import de.marxhendrik.healthcheckcards.feature.ui.ThreeCardsContract.Card.*
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_card_green.view.*
import kotlinx.android.synthetic.main.view_card_orange.view.*
import kotlinx.android.synthetic.main.view_card_red.view.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

private const val ANIMATION_DURATION = 400L
private const val CENTERED_Z_TRANSLATION = 100f

class ThreeCardsView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, style: Int = 0) :
        FrameLayout(context, attr, style),
        InjectingView,
        ThreeCardsContract.View {

    @Inject
    lateinit var presenter: ThreeCardsContract.Presenter

    private val animating = AtomicBoolean(false)

    private val cardToView: Map<Card, SingleCardView> by lazy {
        mapOf(vCardGreen.card to vCardGreen, vCardOrange.card to vCardOrange, vCardRed.card to vCardRed)
    }

    override val clicks: Observable<Card> by lazy {
        Observable.merge(
                cardClicks(vCardGreen),
                cardClicks(vCardOrange),
                cardClicks(vCardRed)
        )
                .throttleFirst(ANIMATION_DURATION, TimeUnit.MILLISECONDS)
                .filter { !animating.get() }
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
        animateTranslate(direction = "X", cardView = cardView, translation = 0.0f)
        animateTranslate(cardView = cardView, translation = 0.0f, delay = ANIMATION_DURATION)

        cardToView.values
                .filter { it != cardView }
                .forEach {
                    //move all the ones on the right back in FIXME
                }

        cardView.centered = false
    }

    private fun center(cardView: SingleCardView) {
        cardView.centered = true
        cardToView.values
                .filter { it != cardView }
                .forEach {
                    //move all the ones on the right out FIXME
                }
        animateTranslate(cardView = cardView, translation = CENTERED_Z_TRANSLATION)
        animateTranslate(direction = "X", cardView = cardView, translation = cardView.getCenterTranslation(), delay = ANIMATION_DURATION)
    }

    /*FIXME
    1. move animations to a util or something
    2. try out physical animations
    3. make animation cooler (move the other cards from the right out and back in)
     */
    private fun animateTranslate(direction: String = "Z", cardView: View, translation: Float, delay: Long = 0) {
        ObjectAnimator.ofFloat(cardView, "translation" + direction, cardView.translationZ, translation).apply {
            duration = ANIMATION_DURATION
            startDelay = delay
            interpolator = AccelerateDecelerateInterpolator()
            animating.set(true)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    animating.set(false)
                }
            })
            start()
        }
    }
}


