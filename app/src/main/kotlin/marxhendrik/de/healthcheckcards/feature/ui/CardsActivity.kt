package marxhendrik.de.healthcheckcards.feature.ui

import android.os.Bundle
import marxhendrik.de.healthcheckcards.R
import marxhendrik.de.healthcheckcards.base.BaseActivity
import marxhendrik.de.healthcheckcards.dagger.HasSubComponentBuilders
import marxhendrik.de.healthcheckcards.dagger.SubComponentBuilderMap
import javax.inject.Inject

class CardsActivity : BaseActivity(), HasSubComponentBuilders {

    @Inject
    override lateinit var builders: SubComponentBuilderMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
    }
}
