package marxhendrik.de.healthcheckcards.feature.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
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
import javax.inject.Inject

private const val ANIMATION_DURATION = 400L
private const val CENTERED_Z_TRANSLATION = 100f

class ThreeCardsView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, style: Int = 0) :
        FrameLayout(context, attr, style),
        InjectingView,
        ThreeCardsContract.View {

    @Inject
    lateinit var presenter: ThreeCardsContract.Presenter

    private val cardToView: Map<Card, CardView> by lazy {
        mapOf(
                GREEN to vCardGreen,
                ORANGE to vCardOrange,
                RED to vCardRed
        )
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
            getSubComponentBuilder(CardsComponent.Builder::class)
                    .view(this)
                    .build()
                    .inject(this)
        }
    }

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

    override fun showFullScreen(it: Card) = animateFullScreen(cardToView.getValue(it))

    private fun cardClicks(view: CardView) = RxView.clicks(view).map { view.card }

    //move logic out of here FIXME
    private fun animateFullScreen(cardView: CardView) {

        var currentlyCentered: CardView? = null

        cardToView.values
                .map {
                    if (it.centered) {
                        currentlyCentered = it
                    }
                    it
                }
                .filter { it == currentlyCentered || it != cardView }
                .forEach { unCenter(it) }

        if (cardView != currentlyCentered) {
            center(cardView)
        }
    }

    private fun unCenter(it: CardView) {
        animateTranslate(direction = "X", cardView = it, translation = 0.0f)
        animateTranslate(cardView = it, translation = 0.0f)
        it.centered = false
    }

    private fun center(cardView: CardView) {
        cardView.centered = true
        animateTranslate(cardView = cardView, translation = CENTERED_Z_TRANSLATION)
        animateTranslate(direction = "X", cardView = cardView, translation = cardView.getCenterTranslation(), delay = ANIMATION_DURATION)
    }

    //FIXME try out physical animations
    private fun animateTranslate(direction: String = "Z", cardView: View, translation: Float, delay: Long = 0) {
        ObjectAnimator.ofFloat(cardView, "translation" + direction, cardView.translationZ, translation).apply {
            duration = ANIMATION_DURATION
            startDelay = delay
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