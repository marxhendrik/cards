package marxhendrik.de.healthcheckcards.dagger

import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection

interface Injectable

object ActivityInjector {
    fun inject(activity: AppCompatActivity) {
        if (activity is Injectable) {
            AndroidInjection.inject(activity)
        }
    }
}