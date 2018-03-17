package marxhendrik.de.healthcheckcards.feature.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_card_green.view.*
import kotlinx.android.synthetic.main.view_card_orange.view.*
import kotlinx.android.synthetic.main.view_card_red.view.*
import marxhendrik.de.healthcheckcards.dagger.InjectingView
import marxhendrik.de.healthcheckcards.dagger.getSubComponentBuilder
import marxhendrik.de.healthcheckcards.feature.ui.ThreeCardsContract.Card
import marxhendrik.de.healthcheckcards.feature.ui.ThreeCardsContract.Card.*
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

    private val cardToView: Map<Card, CardView> by lazy {
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
            getSubComponentBuilder(CardsComponent.Builder::class)
                    .view(this)
                    .build()
                    .inject(this)
        }
    }

    private fun cardClicks(view: CardView) = RxView.clicks(view).map { view.card }

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
    private fun animateFullScreen(cardView: CardView) {
        val currentlyCentered: CardView? = cardToView.values.firstOrNull { it.centered }

        if (cardView == currentlyCentered) {
            unCenter(cardView)
        } else {
            center(cardView)
        }
    }

    private fun unCenter(cardView: CardView) {
        animateTranslate(direction = "X", cardView = cardView, translation = 0.0f)
        animateTranslate(cardView = cardView, translation = 0.0f, delay = ANIMATION_DURATION)

        cardToView.values
                .filter { it != cardView }
                .forEach {
                    //move all the ones on the right back in FIXME
                }

        cardView.centered = false
    }

    private fun center(cardView: CardView) {
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


class CardView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, style: Int = 0) : View(context, attr, style) {
    lateinit var card: Card
    var centered: Boolean = false

    fun getCenterTranslation(): Float {
        val centerX = (left + right) / 2
        val screenCenter = resources.displayMetrics.widthPixels / 2

        return screenCenter.toFloat() - centerX
    }
}