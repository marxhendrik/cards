package marxhendrik.de.healthcheckcards.feature.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_cards.view.*
import marxhendrik.de.healthcheckcards.dagger.InjectingView
import marxhendrik.de.healthcheckcards.dagger.getSubComponentBuilder
import javax.inject.Inject

class CardsView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, style: Int = 0) :
        FrameLayout(context, attr, style),
        InjectingView,
        CardsContract.View {

    private val animationDuration = 400L

    @Inject
    lateinit var presenter: CardsContract.Presenter


    enum class Card {
        GREEN, ORANGE, RED
    }

    //FIXME make view CustomView just to have enum setter
    private val cardToView: Map<Card, View> by lazy {
        mapOf(
                Card.GREEN to vCardGreen,
                Card.ORANGE to vCardOrange,
                Card.RED to vCardRed
        )
    }

    private val viewToCard: Map<View, Card> by lazy {
        mapOf(
                vCardGreen to Card.GREEN,
                vCardOrange to Card.ORANGE,
                vCardRed to Card.RED
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
        getSubComponentBuilder(CardsComponent.Builder::class)
                .view(this)
                .build()
                .inject(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.start()
    }

    override fun showFullScreen(it: Card) = animateFullScreen(cardToView.getValue(it))

    private fun cardClicks(view: View) = RxView.clicks(view).map { viewToCard.getValue(view) }

    private fun animateFullScreen(cardView: View) {
        cardToView.values
                .filter { it != cardView }
                .forEach {
                    clearAnimation()
                    animateTranslate(cardView = it, translation = 0.0f)
                }

        animateTranslate(cardView = cardView, translation = 100f)
        moveToCenter(cardView)
    }

    private fun findCenterDistance(cardView: View): Float {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun moveToCenter(cardView: View) {
        animateTranslate(direction = "X", cardView = cardView, translation = findCenterDistance(cardView), delay = animationDuration)
    }

    //FIXME try out physical animations
    private fun animateTranslate(direction: String = "Z", cardView: View, translation: Float, delay: Long = 0) {
        ObjectAnimator.ofFloat(cardView, "translation" + direction, cardView.translationZ, translation).apply {
            duration = animationDuration
            startDelay = delay
            start()
        }
    }
}