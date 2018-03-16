package marxhendrik.de.healthcheckcards.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import marxhendrik.de.healthcheckcards.dagger.ActivityInjector

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityInjector.inject(this)
        super.onCreate(savedInstanceState)
    }
}