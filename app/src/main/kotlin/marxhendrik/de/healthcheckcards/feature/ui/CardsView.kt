package marxhendrik.de.healthcheckcards.feature.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import marxhendrik.de.healthcheckcards.dagger.InjectingView
import marxhendrik.de.healthcheckcards.dagger.getSubComponentBuilder
import javax.inject.Inject

class CardsView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, style: Int = 0) :
        FrameLayout(context, attr, style),
        InjectingView,
        CardsContract.View {


    @Inject
    lateinit var presenter: CardsContract.Presenter


    init {
        getSubComponentBuilder(CardsComponent.Builder::class)
                .view(this)
                .build()
                .inject(this)
        presenter.start()
    }
}