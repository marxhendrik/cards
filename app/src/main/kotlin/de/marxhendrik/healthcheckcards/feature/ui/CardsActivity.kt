package de.marxhendrik.healthcheckcards.feature.ui

import android.os.Bundle
import de.marxhendrik.healthcheckcards.R
import de.marxhendrik.healthcheckcards.base.BaseActivity
import de.marxhendrik.healthcheckcards.dagger.HasSubComponentBuilders
import de.marxhendrik.healthcheckcards.dagger.SubComponentBuilderMap
import javax.inject.Inject

class CardsActivity : BaseActivity(), HasSubComponentBuilders {

    @Inject
    override lateinit var builders: SubComponentBuilderMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
    }
}
