package de.marxhendrik.healthcheckcards

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import de.marxhendrik.healthcheckcards.dagger.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class HealthCheckApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        DaggerAppComponent.builder()
                .app(this)
                .build()
                .inject(this)
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }


    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}