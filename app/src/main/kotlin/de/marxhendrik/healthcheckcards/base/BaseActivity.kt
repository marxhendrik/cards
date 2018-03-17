package de.marxhendrik.healthcheckcards.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.marxhendrik.healthcheckcards.dagger.ActivityInjector

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityInjector.inject(this)
        super.onCreate(savedInstanceState)
    }
}